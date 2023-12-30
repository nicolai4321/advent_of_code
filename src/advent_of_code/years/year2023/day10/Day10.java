package advent_of_code.years.year2023.day10;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;

public class Day10 extends RootDay {
    public Day10(Year year, int day) {
        super(year, day, "6812", "527");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * What is the farthest away you can get from the start coordinate if you move along the pipes
     */
    @Override
    public String run1(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");
        
        int[] startCoord = findCoord(grid, "S");
        int steps = nrOfSteps(grid, startCoord);
        
        return steps + "";
    }

    /**
     * What is the area that is surrounded by the pipe?
     */
    @Override
    public String run2(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");

        int[] startCoord = findCoord(grid, "S");


        cleanGrid(grid, input, startCoord);
        grid.set(startCoord[0], startCoord[1], getStartValue(grid, startCoord));
        
        fillGrid(grid, 0, 0, new ArrayList<int[]>());
        grid.print();
        
        return count(grid) + "";
    }

    /**
     * Cleans grid such that all cells that are free have the value .
     * @param grid
     * @param input
     * @param startCoord
     */
    private void cleanGrid(Grid<String> grid, String input, int[] startCoord) {
        Grid<String> gridSteps = new Grid<String>(input, false);
        gridSteps.setDivider("");

        nrOfSteps(gridSteps, startCoord);
        
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                String stepValue = gridSteps.get(x, y);
                if (!stepValue.equals("#")) {
                    grid.set(x, y, ".");
                }
            }
        }
    }
    
    /**
     * @param grid
     * @return count amount of cells with value .
     */
    private int count(Grid<String> grid) {
        int count = 0;
        
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                String value = grid.get(x, y);
                if (value.equals(".")) {
                    count++;
                }
            }
        }
        
        return count;
    }

    /**
     * Fills cells with the value # if they are outside the loop
     * @param grid
     * @param x
     * @param y
     * @param visited
     */
    private void fillGrid(Grid<String> grid, int x, int y, ArrayList<int[]> visited) {
        //stop if the coordinate is outside the grid
        if (!(0 <= x && x <= grid.getWidth() && 0 <= y && y <= grid.getHeight())) {
            return;
        }
        
        //stop if the coordinate has been visited before
        for (int[] ints : visited) {
            if (ints[0] == x && ints[1] == y) {
                return;
            }
        }
        visited.add(new int[] {x, y});

        String ul = grid.safeGet(x-1, y-1);
        String ur = grid.safeGet(x, y-1);
        String dl = grid.safeGet(x-1, y);
        String dr = grid.safeGet(x, y);
        
        //mark as outside the loop
        if (ul != null && ul.equals(".")) {
            grid.set(x-1, y-1, "#");
        }

        //mark as outside the loop
        if (ur != null && ur.equals(".")) {
            grid.set(x, y-1, "#");
        }

        //mark as outside the loop
        if (dl != null && dl.equals(".")) {
            grid.set(x-1, y, "#");
        }

        //mark as outside the loop
        if (dr != null && dr.equals(".")) {
            grid.set(x, y, "#");
        }
        
        //go up if possible
        if (!((ul != null && (ul.equals("L") || ul.equals("F") || ul.equals("-"))) && 
              (ur != null && (ur.equals("J") || ur.equals("7") || ur.equals("-"))))) {
            fillGrid(grid, x, y-1, visited);
        }
        
        //go down if possible
        if (!(dl != null && ((dl.equals("L") || dl.equals("F") || dl.equals("-"))) && 
             (dr != null && (dr.equals("J") || dr.equals("7") || dr.equals("-"))))) {
              fillGrid(grid, x, y+1, visited);
        }
        
        //go right if possible
        if (!((ur != null && (ur.equals("7") || ur.equals("F") || ur.equals("|"))) && 
              (dr != null && (dr.equals("L") || dr.equals("J") || dr.equals("|"))))) {
            fillGrid(grid, x+1, y, visited);
        }
        
        //go left if possible
        if (!((ul != null && (ul.equals("7") || ul.equals("F") || ul.equals("|"))) && 
              (dl != null && (dl.equals("L") || dl.equals("J") || dl.equals("|"))))) {
            fillGrid(grid, x-1, y, visited);
        }
    }

    /**
     * @param grid
     * @param searchValue
     * @return coordinate of searchValue
     */
    private int[] findCoord(Grid<String> grid, String searchValue) {
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                String value = grid.get(x, y);
                if (value.equals(searchValue)) {
                    return new int[] { x, y };
                }
            }
        }
        throw new RuntimeException("Could not find coordinate for '" + searchValue + "'");
    }
    
    /**
     * Will find the number of steps to get farthest away from start
     * and mark the loop with the value #.
     * 
     * @param grid
     * @param start0
     * @param start1
     * @param steps
     * @return nr of steps to get farthest away
     */
    private int step(Grid<String> grid, int[] start0, int[] start1, int steps) {
        //ends in the exact same position from both ends
        if (start0[0] == start1[0] && start0[1] == start1[1] && steps != 0) {
            grid.set(start0[0], start0[1], "#");
            return steps + 1;
        }
        
        //the two ends has crossed each other
        if (grid.get(start0[0], start0[1]).equals("#") && grid.get(start1[0], start1[1]).equals("#")) {
            return steps;
        }
        
        int[] next0 = getNext(grid, start0);
        int[] next1 = getNext(grid, start1);
        grid.set(start0[0], start0[1], "#");
        grid.set(start1[0], start1[1], "#");
        
        return step(grid, next0, next1, steps + 1);
    }
    
    /**
     * @param grid
     * @param start
     * @return next possible coordinate
     */
    private int[] getNext(Grid<String> grid, int[] start) {
        String value = grid.get(start[0], start[1]);

        switch(value) {
            case "#":
                return null;
            case "F":
                return validPath(start[0] + 1, start[1], grid, start[0], start[1] + 1);
            case "-":
                return validPath(start[0] - 1, start[1], grid, start[0] + 1, start[1]);
            case "|":
                return validPath(start[0], start[1] + 1, grid, start[0], start[1] - 1);
            case "7":
                return validPath(start[0] - 1, start[1], grid, start[0], start[1] + 1);
            case "L":
                return validPath(start[0], start[1] - 1, grid, start[0] + 1, start[1]);
            case "J":
                return validPath(start[0], start[1] - 1, grid, start[0] - 1, start[1]);
        }
        throw new RuntimeException("Unknown value: " + value);
    }
    
    /**
     * @param grid
     * @param start
     * @return the value of the start coordinates
     */
    private String getStartValue(Grid<String> grid, int[] start) {
        int x = start[0];
        int y = start[1];

        boolean leftConnected = false;
        if (0 <= x-1) {
            String value = grid.get(x-1, y);
            leftConnected = value.equals("-") || value.equals("L") || value.equals("F");
        }
        
        boolean rightConnected = false;
        if (x+1 < grid.getWidth()) {
            String value = grid.get(x+1, y);
            rightConnected = value.equals("-") || value.equals("7") || value.equals("J");
        }
        
        boolean upConnected = false;
        if (0 <= y-1) {
            String value = grid.get(x, y-1);
            upConnected = value.equals("|") || value.equals("7") || value.equals("F");
        }

        boolean downConnected = false;
        if (y+1 < grid.getHeight()) {
            String value = grid.get(x, y+1);
            downConnected = value.equals("|") || value.equals("L") || value.equals("J");
        }
        
        if (leftConnected && rightConnected) {
            return "-";
        }
        
        if (upConnected && downConnected) {
            return "|";
        }
        
        if (downConnected && rightConnected) {
            return "F";
        }

        if (downConnected && leftConnected) {
            return "7";
        }
        
        if (upConnected && leftConnected) {
            return "J";
        }
        
        if (upConnected && rightConnected) {
            return "L";
        }
        
        throw new RuntimeException("Invalid start value");
    }

    /**
     * Moving along a pipe there is only one valid path if you
     * discard the direction you are coming from.
     * 
     * @param x
     * @param y
     * @param grid
     * @param xx
     * @param yy
     * @return valid path
     */
    private int[] validPath(int x0, int y0, Grid<String> grid, int x1, int y1) {
        String value0 = grid.get(x0, y0);
        String value1 = grid.get(x1, y1);
        
        if (!value0.equals("#") && !value1.equals("#")) {
            throw new RuntimeException("Too many valid");
        }
        
        if (value0.equals("#") && !value1.equals("#")) {
            return new int[] { x1, y1 };
        }
        
        if (!value0.equals("#") && value1.equals("#")) {
            return new int[] { x0, y0 };
        }

        return null;
    }

    /**
     * Will find the value of the start and find the number of steps to get farthest 
     * away from start and mark the loop with the value #.
     * 
     * @param grid
     * @param start
     * @return nr of steps to get farthest away from start
     */
    private int nrOfSteps(Grid<String> grid, int[] start) {
        int x = start[0];
        int y = start[1];
        grid.set(x, y, "#");
        final String startValue = getStartValue(grid, start);
        
        switch (startValue) {
            case "-":
                return step(grid, new int[] { x-1, y }, new int[] { x+1, y }, 0);
            case "|":
                return step(grid, new int[] { x, y-1 }, new int[] { x, y+1 }, 0);
            case "F":
                return step(grid, new int[] { x, y+1 }, new int[] { x+1, y }, 0);
            case "7":
                return step(grid, new int[] { x, y+1 }, new int[] { x-1, y }, 0);
            case "L":
                return step(grid, new int[] { x, y-1 }, new int[] { x+1, y }, 0);
            case "J":
                return step(grid, new int[] { x, y-1 }, new int[] { x-1, y }, 0);
            default:
                throw new RuntimeException("Error");
        }
    }
}
