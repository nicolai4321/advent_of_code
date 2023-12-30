package advent_of_code.years.year2023.day17;

import java.util.HashSet;
import java.util.PriorityQueue;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.math.MathOp;

public class Day17 extends RootDay {
    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int UP = 2;
    public final static int DOWN = 3;
    public final static int NULL = 4;
    
    public Day17(Year year, int day) {
        super(year, day, "1099", "1266");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        Grid<String> gridS = new Grid<String>(input, false);
        Grid<Integer> grid = Grid.toIntGrid(gridS);
        grid.setDivider("");

        int[] startCoord = {0, 0};
        int[] endCoord = {grid.getWidth()-1, grid.getHeight()-1};
        
        return minPath(grid, startCoord, endCoord, false) + "";
    }

    @Override
    public String run2(String input) {
        Grid<String> gridS = new Grid<String>(input, false);
        Grid<Integer> grid = Grid.toIntGrid(gridS);
        grid.setDivider("");

        int[] startCoord = {0, 0};
        int[] endCoord = {grid.getWidth()-1, grid.getHeight()-1};
        
        return minPath(grid, startCoord, endCoord, true) + "";
    }
    
    /**
     * Finds minimum path
     * @param grid
     * @param startCoord
     * @param endCoord
     * @param ultra
     * @return cost of minimum path
     */
    public static Integer minPath(Grid<Integer> grid, int[] startCoord, int[] endCoord, boolean ultra) {
        HashSet<Node> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(0, 0, 0, NULL, 0, 0));
        
        return minPath(grid, queue, visited, endCoord, ultra);
    }

    /**
     * Finds minimum path
     * @param grid
     * @param queue
     * @param visited
     * @param endCoord
     * @param ultra
     * @return cost of minimum path
     */
    public static int minPath(Grid<Integer> grid, PriorityQueue<Node> queue, HashSet<Node> visited, int[] endCoord, boolean ultra) {
        Node node = queue.poll();

        if (visited.contains(node)) {
            return minPath(grid, queue, visited, endCoord, ultra);
        }
        visited.add(node);
        
        int x = node.x;
        int y = node.y;

        if (x == endCoord[0] && y == endCoord[1] && (!ultra || 4 <= node.facingCount)) {
            return node.cost;
        }
        
        addOption(grid, queue, node, endCoord, x+1, y, RIGHT, ultra);
        addOption(grid, queue, node, endCoord, x-1, y, LEFT, ultra);
        addOption(grid, queue, node, endCoord, x, y+1, DOWN, ultra);
        addOption(grid, queue, node, endCoord, x, y-1, UP, ultra);
        
        return minPath(grid, queue, visited, endCoord, ultra);
    }
    
    /**
     * Add option if legal
     * @param grid
     * @param queue
     * @param node
     * @param endCoord
     * @param x
     * @param y
     * @param facing
     * @param ultra
     */
    private static void addOption(Grid<Integer> grid, PriorityQueue<Node> queue, Node node, int[] endCoord, int x, int y, int facing, boolean ultra) {
        if (node.facing == facing) {
            int facingCount = node.facingCount;
            if ((!ultra && 3 <= facingCount) || (ultra && 10 <= node.facingCount && node.facing != NULL)) {
                return;
            }
            
            Integer cost = grid.safeGet(x, y);
            if (cost == null) {
                return;
            }
            
            queue.add(new Node(x, y, node.cost + cost, facing, node.facingCount + 1, node.cost + cost + MathOp.manhattenDistance(x, y, endCoord[0], endCoord[1])));
        } else {
            if (ultra && node.facingCount < 4 && node.facing != NULL) {
                return;
            }
            
            if (facing == LEFT && node.facing == RIGHT ||
                facing == RIGHT && node.facing == LEFT ||
                facing == UP && node.facing == DOWN ||
                facing == DOWN && node.facing == UP) {
                return;
            }
            
            Integer cost = grid.safeGet(x, y);
            if (cost == null) {
                return;
            }
            
            queue.add(new Node(x, y, node.cost + cost, facing, 1, node.cost + cost + MathOp.manhattenDistance(x, y, endCoord[0], endCoord[1])));
        }
    }
}
