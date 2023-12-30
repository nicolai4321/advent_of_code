package advent_of_code.years.year2023.day21;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;

public class Day21 extends RootDay {
    public Day21(Year year, int day) {
        super(year, day, "3671", "609708004316870");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * How many valid positions after 64 steps?
     */
    @Override
    public String run1(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");
        
        int[] start = find(grid, "S");
        
        return moveGardener(grid, start[0], start[1], 64) + "";
    }

    /**
     * How many valid positions after 26501365 steps if the grid repeats infinitely?
     */
    @Override
    public String run2(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");
        
        return InfiniteGrid.moveGardener(grid, 26501365).toString();
    }
    
    /**
     * @param grid
     * @param startX
     * @param startY
     * @param steps
     * @return amount of possible positions starting at (startX, startY) in the given amount of steps
     */
    public static int moveGardener(Grid<String> grid, int startX, int startY, int steps) {
        ArrayList<int[]> positions = new ArrayList<>();
        positions.add(new int[] { startX, startY });

        return moveGardener(grid, positions, new ArrayList<>(), steps).size();
    }
    
    /**
     * @param grid
     * @param positions
     * @param rounds
     * @return list of possible positions given the different positions and amount of steps
     */
    public static ArrayList<int[]> moveGardener(Grid<String> grid, ArrayList<int[]> currentPositions, ArrayList<int[]> offsetPositions, int steps) {
        if (currentPositions.isEmpty()) {
            return currentPositions;
        }
        
        if (steps <= 0) {
            return currentPositions;
        }
        
        for (int[] position : currentPositions) {
            int x = position[0];
            int y = position[1];
            
            addMoveIfLegal(grid, offsetPositions, x+1, y);
            addMoveIfLegal(grid, offsetPositions, x-1, y);
            addMoveIfLegal(grid, offsetPositions, x, y+1);
            addMoveIfLegal(grid, offsetPositions, x, y-1);
        }
        
        return moveGardener(grid, offsetPositions, currentPositions, steps - 1);
    }
    
    /**
     * Add new coordinate to the list of positions if legal
     * @param grid
     * @param newPositions
     * @param x
     * @param y
     */
    private static void addMoveIfLegal(Grid<String> grid, ArrayList<int[]> newPositions, int x, int y) {
        String value = grid.safeGet(x, y);
        
        if (value == null) {
            return;
        }
        
        if (!(value.equals(".") || value.equals("S"))) {
            return;
        }
        
        for (int[] newPos : newPositions) {
            if (x == newPos[0] && y == newPos[1]) {
                return;
            }
        }
        
        newPositions.add(new int[] { x, y });
    }

    /**
     * @param grid
     * @param s
     * @return coordinate that has value of string s
     */
    public static int[] find(Grid<String> grid, String s) {
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                String value = grid.get(x, y);
                if (s.equals(value)) {
                    return new int[] { x, y };
                }
            }
        }
        throw new RuntimeException("Could not find " + s);
    }
}
