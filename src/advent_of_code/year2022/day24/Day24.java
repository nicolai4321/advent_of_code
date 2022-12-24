package advent_of_code.year2022.day24;

import java.util.ArrayList;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Grid;
import advent_of_code.utils.MathOp;
import advent_of_code.utils.LightGrid;
import advent_of_code.utils.RootDay;

public class Day24 extends RootDay {
    public Day24() {
        super(2022, 24, "260", "747");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        Grid<String> grid = generateGrid(input);
        ArrayList<Blizzard> blizzards = generateBlizzards(input);
        ArrayList<LightGrid<String>> blizzardStates = generateGridStates(grid, blizzards);
        
        int[] startPos = getStartPosition(grid);
        int[] endPos = getEndPosition(grid);
        
        return reachGoal(startPos[0], startPos[1], endPos[0], endPos[1], blizzardStates, 0, new ArrayList<int[]>(), new ArrayList<int[]>()) + "";
    }
    
    @Override
    public String run2(String input) {
        Grid<String> grid = generateGrid(input);
        ArrayList<Blizzard> blizzards = generateBlizzards(input);
        ArrayList<LightGrid<String>> blizzardStates = generateGridStates(grid, blizzards);
        
        int[] startPos = getStartPosition(grid);
        int[] endPos = getEndPosition(grid);
        
        int minutes0 = reachGoal(startPos[0], startPos[1], endPos[0], endPos[1], blizzardStates, 0, new ArrayList<int[]>(), new ArrayList<int[]>());
        int minutes1 = reachGoal(endPos[0], endPos[1], startPos[0], startPos[1], blizzardStates, minutes0, new ArrayList<int[]>(), new ArrayList<int[]>());
        int minutes2 = reachGoal(startPos[0], startPos[1], endPos[0], endPos[1], blizzardStates, minutes1, new ArrayList<int[]>(), new ArrayList<int[]>());
        return (minutes2) + "";
    }

    /**
     * @param x
     * @param y
     * @param goalX
     * @param goalY
     * @param grids
     * @param minutes
     * @param visited
     * @param options
     * @return minutes to reach goal
     */
    private static Integer reachGoal(int x, int y, int goalX, int goalY, ArrayList<LightGrid<String>> blizzardStates, int minutes, ArrayList<int[]> visited, ArrayList<int[]> options) {
        if (x == goalX && y == goalY) {
            return minutes;
        }
        
        addIfFree(x, y+1, goalX, goalY, minutes+1, blizzardStates, visited, options);
        addIfFree(x, y-1, goalX, goalY, minutes+1, blizzardStates, visited, options);
        addIfFree(x+1, y, goalX, goalY, minutes+1, blizzardStates, visited, options);
        addIfFree(x-1, y, goalX, goalY, minutes+1, blizzardStates, visited, options);
        addIfFree(x, y, goalX, goalY, minutes+1, blizzardStates, visited, options);
        int[] bestOption = options.get(0);
        options.remove(0);

        return reachGoal(bestOption[0], bestOption[1], goalX, goalY, blizzardStates, bestOption[2], visited, options);
    }

    /**
     * Add this scenario as an option if (x,y) is a valid and free cell
     * @param x
     * @param y
     * @param minutes
     * @param grids
     * @param visited
     * @param options
     */
    private static void addIfFree(int x, int y, int goalX, int goalY, int minutes, ArrayList<LightGrid<String>> blizzardStates, ArrayList<int[]> visited, ArrayList<int[]> options) {
        int blizzardState = minutes % blizzardStates.size();
        
        //if already visited
        for (int i=0; i<visited.size(); i++) {
            int[] v = visited.get(i);
            if (v[0] == blizzardState && v[1] == x && v[2] == y) {
                if (minutes < v[3]) {
                    visited.remove(i);
                    break;
                } else {
                    return;                    
                }
            }
        }
        visited.add(new int[] { blizzardState, x, y, minutes });
        
        LightGrid<String> grid = blizzardStates.get(blizzardState);
        
        //check outside
        if (x < 0 || grid.getWidth() <= x || y < 0 || grid.getHeight() <= y) {
            return;
        }
        
        //check for walls
        if (grid.get(x, y).equals("#")) {
            return;
        }
        
        //add option sorted by the best score
        int score = MathOp.getManhattenDistance(goalX, goalY, x, y) + minutes;
        for (int i=0; i<options.size(); i++) {
            if (score < options.get(i)[3]) {
                options.add(i, new int[] {x, y, minutes, score});
                return;
            }
        }
        options.add( new int[] {x, y, minutes, score} );
    }
    
   /**
    * @param blizzards
    * @param grid
    * @param times
    * @return grid of blizzards
    */
    private static LightGrid<String> move(ArrayList<Blizzard> blizzards, Grid<String> grid, int times) {
        LightGrid<String> blizzardsGrid = new LightGrid<String>(grid.getWidth(), grid.getHeight());
        blizzardsGrid.setDefaultValue(".");
        for (int i=0; i<blizzards.size(); i++) {
            Blizzard blizzard = blizzards.get(i);
            int posX = blizzard.x;
            int posY = blizzard.y;
            Direction direction = blizzard.direction;
            
            if (direction == Direction.UP) {
                int relY = posY - 1;
                posY = Math.floorMod(relY - times, grid.getHeight() - 2) + 1;
            } else if (direction == Direction.DOWN) {
                int relY = posY - 1;
                posY = Math.floorMod(relY + times, grid.getHeight() - 2) + 1;
            } else if (direction == Direction.LEFT) {
                int relX = posX - 1;
                posX = Math.floorMod(relX - times, grid.getWidth() - 2) + 1;
            } else if (direction == Direction.RIGHT) {
                int relX = posX - 1;
                posX = Math.floorMod(relX + times, grid.getWidth() - 2) + 1;
            }
            
            blizzardsGrid.set(posX, posY, "#");
        }
        return blizzardsGrid;
    }
    
    /**
     * @param grid
     * @return start position
     */
    private static int[] getStartPosition(Grid<String> grid) {
        int y=0;
        for (int x=0; x<grid.getWidth(); x++) {
            String value = grid.get(x, y);
            if (value.equals(".")) {
                return new int[] { x, y };
            }
        }
        return null;
    }
    
    /**
     * @param grid
     * @return end position
     */
    private static int[] getEndPosition(Grid<String> grid) {
        int y=grid.getHeight()-1;
        for (int x=0; x<grid.getWidth(); x++) {
            String value = grid.get(x, y);
            if (value.equals(".")) {
                return new int[] { x, y };
            }
        }
        return null;
    }
    
    /**
     * @param grid
     * @param blizzards
     * @return grids of all the blizzard positions where each grid represents a minute
     */
    private static ArrayList<LightGrid<String>> generateGridStates(Grid<String> grid, ArrayList<Blizzard> blizzards) {
        ArrayList<LightGrid<String>> gridStates = new ArrayList<LightGrid<String>>();
        int width = grid.getWidth();
        int height = grid.getHeight();
        int totalStates = MathOp.lcm(width, height);
        
        for (int minute=0; minute<totalStates; minute++) {
            LightGrid<String> blizzardGrid = move(blizzards, grid, minute);
            for (int x=0; x<grid.getWidth(); x++) {
                for (int y=0; y<grid.getHeight(); y++) {
                    String value = grid.get(x, y);
                    if (value.equals("#")) {
                        blizzardGrid.set(x, y, "#");
                    }
                }
            }
            gridStates.add(blizzardGrid);
        }
        
        return gridStates;
    }
    
    /**
     * @param input
     * @return a list of the initial positions and direction for the blizzards
     */
    private static ArrayList<Blizzard> generateBlizzards(String input) {
        ArrayList<Blizzard> blizzards = new ArrayList<Blizzard>();
        String[] lines = input.split("\n");
        for (int r=0; r<lines.length; r++) {
            char[] chars = lines[r].toCharArray();
            for (int c=0; c<chars.length; c++) {
                char direction = chars[c];
                if (direction == '<' || direction == '>' || direction == '^' || direction == 'v') {
                    Blizzard blizzard = new Blizzard();
                    blizzard.x = c;
                    blizzard.y = r;

                    if (direction == '>') {
                        blizzard.direction = Direction.RIGHT;
                    } else if (direction == '<') {
                        blizzard.direction = Direction.LEFT;
                    } else if (direction == '^') {
                        blizzard.direction = Direction.UP;
                    } else if (direction == 'v') {
                        blizzard.direction = Direction.DOWN;
                    }
                    blizzards.add(blizzard);
                }
            }
        }
        return blizzards;
    }
    
    /**
     * @param input
     * @return grid without blizzards
     */
    private static Grid<String> generateGrid(String input) {
        String[] lines = input.split("\n");
        int height = lines.length;
        int width = lines[0].length();
        
        Grid<String> grid = new Grid<String>(width, height, false);
        grid.setDivider("");
        
        for (int y=0; y<height; y++) {
            String line = lines[y];
            char[] chars = line.toCharArray();
            for (int x=0; x<width; x++) {
                char c = chars[x];
                if (c == '#') {
                    grid.set(x, y, "#");
                } else {
                    grid.set(x, y, ".");
                }
            }
        }
        
        return grid;
    }
}
