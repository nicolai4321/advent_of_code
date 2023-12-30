package advent_of_code.years.year2023.day14;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.Log;
import advent_of_code.utils.math.big.BigInt;

public class Day14 extends RootDay {
    public Day14(Year year, int day) {
        super(year, day, "108792", null);
        setInput1("input01.txt");
        setInput2("input01.txt");
        this.disableRun1();
    }
    
    private HashMap<String, Grid<String>> cache = new HashMap<String, Grid<String>>();
    
    @Override
    public String run1(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");
        
        tiltGridUp(grid);
        grid.print();
        
        BigInt preassure = getPreassure(grid);
        
        return preassure + "";
    }

    @Override
    public String run2(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");

        boolean patternDone = false;
        String patternStart = null;
        ArrayList<String> patterns = new ArrayList<String>();
        ArrayList<String> tmpPatterns = new ArrayList<String>();
        
        int cycles = 1000000000;
        for (int i=0; i<cycles; i++) {
            tiltCycle(grid);
            String currentPattern = grid.toString();
            
            if (patternStart == null) {
                if (tmpPatterns.contains(currentPattern)) {
                    Log.show("start: " + i);
                    patternStart = currentPattern;
                    patterns.add(currentPattern);
                    continue;
                } else {
                    tmpPatterns.add(currentPattern);
                }
            }
            
            if (!patternDone && patternStart != null) {
                if (patternStart.equals(currentPattern)) {
                    patternDone = true;
                    int patternLength = patterns.size();
                    Log.show("done: " + i + ", length: " + patternLength);
                    
                    int nrJumps = ((cycles - i) / patternLength) - 1;
                    //major skip
                    i = i + (nrJumps * patternLength);
                    Log.show("jumping to: " + i);
                } else {
                    patterns.add(currentPattern);
                }
            }
        }
        
        return getPreassure(grid) + "";
    }
    
    private void tiltCycle(Grid<String> grid) {
        Grid<String> sto = cache.get(grid.toString());
        if (sto != null) {
            grid = sto;
        }
        
        tiltGridUp(grid);
        tiltGridLeft(grid);
        tiltGridDown(grid);
        tiltGridRight(grid);
        
        cache.put(grid.toString(), grid);
    }
    
    private void tiltGridUp(Grid<String> grid) {
        for (int y=0; y<grid.getHeight(); y++) {
            for (int x=0; x<grid.getWidth(); x++) {
                moveRockUpDown(grid, x, y, -1);
            }
        }
    }
    
    private void tiltGridDown(Grid<String> grid) {
        for (int y=grid.getHeight()-1; 0<=y; y--) {
            for (int x=0; x<grid.getWidth(); x++) {
                moveRockUpDown(grid, x, y, 1);
            }
        }
    }

    private void tiltGridRight(Grid<String> grid) {
        for (int y=0; y<grid.getHeight(); y++) {
            for (int x=grid.getWidth()-1; 0<=x; x--) {
                moveRockRightLeft(grid, x, y, 1);
            }
        }
    }
    
    private void tiltGridLeft(Grid<String> grid) {
        for (int y=0; y<grid.getHeight(); y++) {
            for (int x=0; x<grid.getWidth(); x++) {
                moveRockRightLeft(grid, x, y, -1);
            }
        }
    }
    
    private void moveRockRightLeft(Grid<String> grid, int x, int y, int direction) {
        String value = grid.get(x, y);
        if (!value.equals("O")) {
            return;
        }
        
        if (x == 0 && direction == -1) {
            return;
        }
        
        if (x == grid.getWidth()-1 && direction == 1) {
            return;
        }
        
        String nextValue = grid.get(x + direction, y);
        if (!nextValue.equals(".")) {
            return;
        }
        
        grid.set(x, y, ".");
        grid.set(x + direction, y, "O");
        
        moveRockRightLeft(grid, x + direction, y, direction);
    }

    private void moveRockUpDown(Grid<String> grid, int x, int y, int direction) {
        String value = grid.get(x, y);
        if (!value.equals("O")) {
            return;
        }
        
        if (y == 0 && direction == -1) {
            return;
        }
        
        if (y == grid.getHeight()-1 && direction == 1) {
            return;
        }
        
        String nextValue = grid.get(x, y + direction);
        if (!nextValue.equals(".")) {
            return;
        }
        
        grid.set(x, y, ".");
        grid.set(x, y + direction, "O");
        
        moveRockUpDown(grid, x, y + direction, direction);
    }
    
    private BigInt getPreassure(Grid<String> grid) {
        BigInt preassure = new BigInt(0);
        
        for (int y=0; y<grid.getHeight(); y++) {
            for (int x=0; x<grid.getWidth(); x++) {
                String value = grid.get(x, y);
                
                if (value.equals("O")) {
                    int p = (grid.getHeight() - y);
                    //Log.show(x + "," + y + ": " + p);
                    
                    preassure = preassure.add(p);
                }
            }
        }
        return preassure;
    }
}
