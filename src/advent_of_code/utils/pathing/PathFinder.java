package advent_of_code.utils.pathing;

import java.util.ArrayList;
import java.util.HashSet;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Log;
import advent_of_code.utils.math.MathOp;

public class PathFinder {
    public static final String WALL = "#";
    
    private static int POS_X_INDEX = 0;
    private static int POS_Y_INDEX = 1;
    private static int COST_INDEX = 2;
    private static int MIN_COST_INDEX = 3;
    private static int LOCKED_INDEX = 4;
    
    /**
     * A* algorithm
     * @param grid
     * @param start
     * @param end
     * @return shortest path length
     */
    public static Integer minPath(Grid<String> grid, char start, char end) {
        int[] startPos = getPos(grid, start);
        int[] endPos = getPos(grid, end);

        ArrayList<int[]> options = new ArrayList<int[]>();
        addOption(grid, options, 0, startPos[0], startPos[1], endPos[0], endPos[1]);
        return minPathAstar(grid, options, endPos [0], endPos[1], false);
    }
    
    /**
     * A* algorithm will also paint and overwrite grid with path
     * @param grid
     * @param start
     * @param end
     * @returns shortest path length
     */
    public static Integer trackPath(Grid<String> grid, char start, char end) {
        int[] startPos = getPos(grid, start);
        int[] endPos = getPos(grid, end);

        ArrayList<int[]> options = new ArrayList<int[]>();
        addOption(grid, options, 0, startPos[0], startPos[1], endPos[0], endPos[1]);
        Integer length = minPathAstar(grid, options, endPos [0], endPos[1], false);
        
        if (length == null) {
            Log.warn("No path exists");
            return length;
        }

        backTrack(grid, options, endPos[0], endPos[1]);
        return length;
    }
    
    /**
     * 
     * @param directionGrid
     * @param start
     * @param end
     */
    public static Integer minPathDirection(Grid<String> directionGrid, char start, char end) {
        int[] startPos = getPosContaining(directionGrid, start);
        int[] endPos = getPosContaining(directionGrid, end);
        
        ArrayList<int[]> options = new ArrayList<int[]>();
        addOption(directionGrid, options, 0, startPos[0], startPos[1], endPos[0], endPos[1]);
        return minPathAstar(directionGrid, options, endPos [0], endPos[1], true);
    }

    
    /**
     * @param grid
     * @param start
     * @param end
     * @return shortest path length
     */
    public static Integer minSimple(Grid<String> grid, char start, char end) {
        ArrayList<int[]> tracker = new ArrayList<int[]>();
        int[] startPos = getPos(grid, start);
        int[] endPos = getPos(grid, end);
        ArrayList<int[]> finalTracker = minPathTracker(grid, tracker, startPos[0], startPos[1], endPos [0], endPos[1]);
        return finalTracker.size();
    }
    
    /**
     * Finds shortest path and mark it on the grid
     * @param grid
     * @param start
     * @param end
     */
    public static void simpleTrack(Grid<String> grid, char start, char end) {
        ArrayList<int[]> tracker = new ArrayList<int[]>();
        int[] startPos = getPos(grid, start);
        int[] endPos = getPos(grid, end);
        ArrayList<int[]> finalTracker = minPathTracker(grid, tracker, startPos[0], startPos[1], endPos [0], endPos[1]);
        
        for (int[] t : finalTracker) {
            grid.set(t[0], t[1], "¤");
        }
    }
    
    public static void simpleTrack(Grid<String> grid, int[] startPos, int[] endPos) {
        ArrayList<int[]> tracker = new ArrayList<int[]>();
        ArrayList<int[]> finalTracker = minPathTracker(grid, tracker, startPos[0], startPos[1], endPos [0], endPos[1]);
        
        for (int[] t : finalTracker) {
            grid.set(t[0], t[1], "¤");
        }
    }
    
    /**
     * @param grid
     * @param x
     * @param y
     * @return the amount of legal cells starting from x,y
     */
    public static int legalCells(Grid<String> grid, int x, int y) {
        HashSet<int[]> visited = new HashSet<int[]>();
        exploreLegalCells(grid, x, y, visited);
        return visited.size();
    }
    
    /**
     * Backtracks the path for AStar
     * @param grid
     * @param options
     * @param endX
     * @param endY
     */
    private static void backTrack(Grid<String> grid, ArrayList<int[]> options, int endX, int endY) {
        int[] currentOption = null;

        for (int[] option : options) {
            if (option[POS_X_INDEX] == endX && option[POS_Y_INDEX] == endY) {
                currentOption = option;
                break;
            }
        }
         
        while(1 < currentOption[COST_INDEX]) {
            int currentX = currentOption[POS_X_INDEX];
            int currentY = currentOption[POS_Y_INDEX];
            
            for (int[] option : options) {
                int x = option[POS_X_INDEX];
                int y = option[POS_Y_INDEX];
                
                if (option[COST_INDEX] == currentOption[COST_INDEX] - 1 && MathOp.manhattenDistance(x, y, currentX, currentY) == 1) {
                    currentOption = option;
                    grid.set(x, y, "¤");
                    break;
                }
            }
        }
    }
    
    /**
     * @param grid
     * @param options
     * @param endX
     * @param endY
     * @param direction
     * @return shortest distance using Astar
     */
    private static Integer minPathAstar(Grid<String> grid, ArrayList<int[]> options, int endX, int endY, boolean direction) {
        if (options.isEmpty()) {
            return null;
        }
        
        int[] bestOption = null;

        for (int[] option : options) {
            if (option[LOCKED_INDEX] == 1) {
                continue;
            }
            
            if (option[POS_X_INDEX] == endX && option[POS_Y_INDEX] == endY) {
                return option[COST_INDEX];
            }
            
            if (bestOption == null || option[MIN_COST_INDEX] < bestOption[MIN_COST_INDEX]) {
                bestOption = option;
            }
        }
        
        if (bestOption == null) {
            return null;
        }

        //add options
        int newCost = bestOption[COST_INDEX] + 1;
        int bestOptionX = bestOption[POS_X_INDEX];
        int bestOptionY = bestOption[POS_Y_INDEX];
        
        if (direction) {
            String directions = grid.get(bestOptionX, bestOptionY);
            
            if (directions.contains("l")) { addOption(grid, options, newCost, bestOptionX - 1, bestOptionY    , endX, endY); }
            if (directions.contains("r")) { addOption(grid, options, newCost, bestOptionX + 1, bestOptionY    , endX, endY); }
            if (grid.getVerticalGoesUp()) {
                if (directions.contains("d")) { addOption(grid, options, newCost, bestOptionX, bestOptionY - 1, endX, endY); }
                if (directions.contains("u")) { addOption(grid, options, newCost, bestOptionX, bestOptionY + 1, endX, endY); }
            } else {
                if (directions.contains("d")) { addOption(grid, options, newCost, bestOptionX, bestOptionY + 1, endX, endY); }
                if (directions.contains("u")) { addOption(grid, options, newCost, bestOptionX, bestOptionY - 1, endX, endY); }
            }
        } else {
            addOption(grid, options, newCost, bestOptionX - 1, bestOptionY    , endX, endY);
            addOption(grid, options, newCost, bestOptionX + 1, bestOptionY    , endX, endY);
            addOption(grid, options, newCost, bestOptionX    , bestOptionY - 1, endX, endY);
            addOption(grid, options, newCost, bestOptionX    , bestOptionY + 1, endX, endY);
        }
        bestOption[LOCKED_INDEX] = 1;
        
        return minPathAstar(grid, options, endX, endY, direction);
    }
    
    /**
     * Adds option if that is available to discover
     * @param grid
     * @param options
     * @param cost
     * @param x
     * @param y
     * @param endX
     * @param endY
     */
    private static void addOption(Grid<String> grid, ArrayList<int[]> options, int cost, int x, int y, int endX, int endY) {
        if (WALL.equals(grid.get(x, y))) {
            return;
        }
        
        if (x < 0 || y < 0 || grid.getWidth() <= x || grid.getHeight() <= y) {
            return;
        }
        
        for (int[] option : options) {
            if (option[0] == x && option[1] == y) {
                if (cost <= option[COST_INDEX]) {
                    options.remove(option);
                    break;
                }
                return;
            }
        }
        
        options.add(new int[] { x, y, cost, cost + MathOp.manhattenDistance(x, y, endX, endY), 0 });
    }
    
    /**
     * @param grid
     * @param tracker
     * @param x
     * @param y
     * @param endX
     * @param endY
     * @return minimum distance with tracker
     */
    private static ArrayList<int[]> minPathTracker(Grid<String> grid, ArrayList<int[]> tracker, int x, int y, int endX, int endY) {
        if (x == endX && y == endY) {
            return tracker;
        }
        
        if (x < 0 || y < 0 || grid.getWidth() <= x || grid.getHeight() <= y) {
            return null;
        }

        if (grid.get(x, y).equals(WALL)) {
            return null;
        }
        
        for (int[] t : tracker) {
            if (t[0] == x && t[1] == y) {
                return null;
            }
        }
        
        tracker.add(new int[] {x, y});
        
        ArrayList<int[]> r = minPathTracker(grid, (ArrayList<int[]>) tracker.clone(), x + 1, y + 0, endX, endY);
        ArrayList<int[]> l = minPathTracker(grid, (ArrayList<int[]>) tracker.clone(), x - 1, y + 0, endX, endY);
        ArrayList<int[]> u = minPathTracker(grid, (ArrayList<int[]>) tracker.clone(), x + 0, y + 1, endX, endY);
        ArrayList<int[]> d = minPathTracker(grid, (ArrayList<int[]>) tracker.clone(), x + 0, y - 1, endX, endY);
        
        return min(min(r, l), min(u, d));
    }
    
    /**
     * @param a
     * @param b
     * @return tracker with minimum distance where null is the same as the max value
     */
    private static ArrayList<int[]> min(ArrayList<int[]> a, ArrayList<int[]> b) {
        if (a == null && b == null) {
            return null;
        }
        
        if (a == null) {
            return b;
        }
        
        if (b == null) {
            return a;
        }
        
        if (a.size() < b.size()) {
            return a;
        }
        
        return b;
    }
    
    /**
     * @param grid
     * @param c
     * @return position of the first found char c
     */
    private static int[] getPosContaining(Grid<String> grid, char c) {
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                if (grid.get(x, y).contains(c + "")) {
                    return new int[] {x, y};
                }
            }
        }
        throw new RuntimeException("Could not find a position for the grid that contains char '" + c + "'");
    }
    
    /**
     * @param grid
     * @param c
     * @return position of the first found char c
     */
    private static int[] getPos(Grid<String> grid, char c) {
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                if ((c + "").equals(grid.get(x, y))) {
                    return new int[] {x, y};
                }
            }
        }
        throw new RuntimeException("Could not find a position for the grid that contains char '" + c + "'");
    }
    
    /**
     * Adds legal cells to the visited list
     * @param grid
     * @param x
     * @param y
     * @param visited
     */
    private static void exploreLegalCells(Grid<String> grid, int x, int y, HashSet<int[]> visited) {
        if (x < 0 || grid.getWidth() <= x || y < 0 || grid.getHeight() <= y) {
            return;
        }
        
        String value = grid.get(x, y);
        if (value.equals(WALL)) {
            return;
        }
        
        for (int[] v : visited) {
            if (v[0] == x && v[1] == y) {
                return;
            }
        }
        
        visited.add(new int[] { x, y});
        
        exploreLegalCells(grid, x + 1, y, visited);
        exploreLegalCells(grid, x - 1, y, visited);
        exploreLegalCells(grid, x, y + 1, visited);
        exploreLegalCells(grid, x, y - 1, visited);
    }
}
