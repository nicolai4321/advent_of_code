package advent_of_code.years.year2023.day13;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.math.big.BigInt;

public class Day13 extends RootDay {
    public Day13(Year year, int day) {
        super(year, day, "32723", "34536");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        ArrayList<Grid<String>> grids = getGrids(input);

        BigInt sum = new BigInt(0);
        for (Grid<String> grid : grids) {
            sum = sum.add(new BigInt(getMirrorValue(grid, true)));
        }
        
        return sum + "";
    }


    @Override
    public String run2(String input) {
        ArrayList<Grid<String>> grids = getGrids(input);

        BigInt sum = new BigInt(0);
        for (Grid<String> grid : grids) {
            sum = sum.add(new BigInt(getMirrorValue(grid, false)));
        }
        
        return sum + "";
    }
    
    private int getMirrorValue(Grid<String> grid, boolean mirrorClean) {
        Integer horizontalMirror = findHorizontalMirror(grid, mirrorClean);
        if (horizontalMirror != null) {
            return horizontalMirror * 100;
        }
        
        Integer verticalMirror = findVerticalMirror(grid, mirrorClean);
        if (verticalMirror != null) {
            return verticalMirror;
        }
        
        throw new RuntimeException("Could not find reflection");
    }
    
    private Integer findHorizontalMirror(Grid<String> grid, boolean mirrorClean) {
        for (int y=0; y<grid.getHeight(); y++) {
            if (isHorizontalMirror(y, y+1, grid, mirrorClean)) {
                return y+1;
            }
        }
        
        return null;
    }
    
    private Integer findVerticalMirror(Grid<String> grid, boolean mirrorClean) {
        for (int x=0; x<grid.getWidth(); x++) {
            if (isVerticalMirror(x, x+1, grid, mirrorClean)) {
                return x+1;
            }
        }
        
        return null;
    }

    private boolean isVerticalMirror(int x0, int x1, Grid<String> grid, boolean mirrorClean) {
        if (x0 == grid.getWidth()-1) {
            return false;
        }
        
        if (x0 < 0) {
            return mirrorClean;
        }
        
        if (grid.getWidth() <= x1) {
            return mirrorClean;
        }
        
        String col0 = "";
        String col1 = "";
        
        for (int y=0; y<grid.getHeight(); y++) {
            col0 += grid.get(x0, y);
            col1 += grid.get(x1, y);
        }
        
        if (col0.equals(col1)) {
            return isVerticalMirror(x0 - 1, x1 + 1, grid, mirrorClean);
        } else if (!mirrorClean && nrOfDifferences(col0, col1) == 1) {
            return isVerticalMirror(x0 - 1, x1 + 1, grid, true);
        } else {
            return false;
        }
    }
    
    private boolean isHorizontalMirror(int y0, int y1, Grid<String> grid, boolean mirrorClean) {
        if (y0 == grid.getHeight()-1) {
            return false;
        }
        
        if (y0 < 0) {
            return mirrorClean;
        }
        
        if (grid.getHeight() <= y1) {
            return mirrorClean;
        }
        
        String row0 = "";
        String row1 = "";
        
        for (int x=0; x<grid.getWidth(); x++) {
            row0 += grid.get(x, y0);
            row1 += grid.get(x, y1);
        }
        
        if (row0.equals(row1)) {
            return isHorizontalMirror(y0 - 1, y1 + 1, grid, mirrorClean);
        } else if (!mirrorClean && nrOfDifferences(row0, row1) == 1) {
            return isHorizontalMirror(y0 - 1, y1 + 1, grid, true);
        } else {
            return false;
        }
    }
    
    private int nrOfDifferences(String s0, String s1) {
        int differences = 0;
        
        for (int i=0; i<s0.length(); i++) {
            char c0 = s0.charAt(i);
            char c1 = s1.charAt(i);
            
            if (c0 != c1) {
                differences++;
            }
        }
        
        return differences;
    }

    private ArrayList<Grid<String>> getGrids(String input) {
        ArrayList<Grid<String>> grids = new ArrayList<Grid<String>>();
        for (String s : input.split("\n\n")) {
            Grid<String> grid = new Grid<String>(s, false);
            grid.setDivider("");
            grids.add(grid);
        }
        return grids;
    }
}
