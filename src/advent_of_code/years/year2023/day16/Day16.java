package advent_of_code.years.year2023.day16;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.enums.Direction;

public class Day16 extends RootDay {
    public Day16(Year year, int day) {
        super(year, day, "8146", "8358");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * How many tiles are being energized from (0,0)?
     */
    @Override
    public String run1(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        start(grid);
        
        return energy(grid) + "";
    }

    /**
     * How many tiles are being energized at max if you can start from any position at the edge?
     */
    @Override
    public String run2(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        
        int bestEnergy = 0;
        for (int x=0; x<grid.getWidth(); x++) {
            bestEnergy = Math.max(bestEnergy, traverseE(grid, x, 0, Direction.DOWN));
            bestEnergy = Math.max(bestEnergy, traverseE(grid, x, grid.getHeight()-1, Direction.UP));
        }
        
        for (int y=0; y<grid.getHeight(); y++) {
            bestEnergy = Math.max(bestEnergy, traverseE(grid, 0, y, Direction.RIGHT));
            bestEnergy = Math.max(bestEnergy, traverseE(grid, grid.getWidth()-1, y, Direction.LEFT));
        }
        
        return bestEnergy + "";
    }
    
    private int traverseE(Grid<String> grid, int x, int y, Direction direction) {
        Grid<String> gridCopy = copyGrid(grid);
        traverse(gridCopy, x, y, direction);
        return energy(gridCopy);
    }

    private Grid<String> copyGrid(Grid<String> grid) {
        Grid<String> copy = new Grid<String>(grid.getWidth(), grid.getHeight(), false);
        
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                copy.set(x, y, grid.get(x, y));
            }
        }
        
        return copy;
    }

    private int energy(Grid<String> grid) {
        int energy = 0;
        
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                String value = grid.get(x, y);
                if (value.contains("#")) {
                    energy++;
                }
            }
        }
        
        return energy;
    }

    private void start(Grid<String> grid) {
        int x = 0;
        int y = 0;
        traverse(grid, x, y, Direction.RIGHT);
    }

    private void traverse(Grid<String> grid, int x, int y, Direction direction) {
        String value = grid.safeGet(x, y);
        
        if (value == null) {
            return;
        }
        
        if (value.contains("#" + direction)) {
            return;
        }
        
        if (!value.contains("#")) {
            grid.set(x, y, value + "#" + direction);
        }
        
        if (value.contains(".")) {
            switch(direction) {
                case LEFT:
                    traverse(grid, x-1, y, direction);
                    return;
                case RIGHT:
                    traverse(grid, x+1, y, direction);
                    return;
                case DOWN:
                    traverse(grid, x, y+1, direction);
                    return;
                case UP:
                    traverse(grid, x, y-1, direction);
                    return;
            }
        }
        
        if (value.contains("/")) {
            switch(direction) {
                case LEFT:
                    traverse(grid, x, y+1, Direction.DOWN);
                    return;
                case RIGHT:
                    traverse(grid, x, y-1, Direction.UP);
                    return;
                case DOWN:
                    traverse(grid, x-1, y, Direction.LEFT);
                    return;
                case UP:
                    traverse(grid, x+1, y, Direction.RIGHT);
                    return;
            }
        }
        
        if (value.contains("\\")) {
            switch(direction) {
                case LEFT:
                    traverse(grid, x, y-1, Direction.UP);
                    return;
                case RIGHT:
                    traverse(grid, x, y+1, Direction.DOWN);
                    return;
                case DOWN:
                    traverse(grid, x+1, y, Direction.RIGHT);
                    return;
                case UP:
                    traverse(grid, x-1, y, Direction.LEFT);
                    return;
            }
        }
        
        if (value.contains("-")) {
            switch(direction) {
                case LEFT:
                    traverse(grid, x-1, y, direction);
                    return;
                case RIGHT:
                    traverse(grid, x+1, y, direction);
                    return;
                case DOWN:
                case UP:
                    traverse(grid, x-1, y, Direction.LEFT);
                    traverse(grid, x+1, y, Direction.RIGHT);
                    return;
            }
        }
        
        if (value.contains("|")) {
            switch(direction) {
                case LEFT:
                case RIGHT:
                    traverse(grid, x, y-1, Direction.UP);
                    traverse(grid, x, y+1, Direction.DOWN);
                    return;
                case DOWN:
                    traverse(grid, x, y+1, direction);
                    return;
                case UP:
                    traverse(grid, x, y-1, direction);
                    return;
            }
        }
    }
}
