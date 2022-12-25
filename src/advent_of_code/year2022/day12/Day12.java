package advent_of_code.year2022.day12;

import advent_of_code.utils.Grid;
import advent_of_code.utils.RootDay;

public class Day12 extends RootDay {
    private static String START = "S";
    private static String END = "E";
    private static String VISITED = "v";
    
    public Day12() {
        super(2022, 12, "490", "488");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * What is the fewest steps required to move from your current position to the location that should get the best signal?
     */
    @Override
    public String run1(String input) {
        Grid<Integer> heightMap = getHeightMap(input);
        Grid<String> directionMap = getDirectionMapFromBottom(heightMap);
        return calculateSteps(directionMap) + "";
    }
    
    /**
     * What is the fewest steps required to move starting from any square with elevation a to the location that should get the best signal?
     */
    @Override
    public String run2(String input) {
        Grid<Integer> heightMap = getHeightMap(input);
        Grid<String> directionMap = getDirectionMapFromTop(heightMap);
        return calculateSteps(directionMap) + "";
    }
    
    /**
     * @param dirMap
     * @return amount of steps
     */
    private int calculateSteps(Grid<String> dirMap) {
        for (int x=0; x<dirMap.getWidth(); x++) {
            for (int y=0; y<dirMap.getHeight(); y++) {
                String s = dirMap.get(x, y);
                if (s.contains(START)) {
                    return traverse(dirMap, x, y, 0);
                }
            }
        }
        
        throw new RuntimeException("Could not find start position");
    }

    /**
     * Method to traverse the grid
     * @param dirMap
     * @param x
     * @param y
     * @param steps
     * @return amount of steps from (x,y) to a finish
     */
    private int traverse(Grid<String> directionMap, int x, int y, int steps) {
        String directions = directionMap.get(x, y);

        //logic for visiting cell
        if (directions.contains(VISITED)) {
            Integer otherSteps = Integer.parseInt(directions.replaceAll(".*" + VISITED, ""));
            if (otherSteps <= steps) {
                return Integer.MAX_VALUE;
            } else {
                directionMap.set(x, y, directions.replaceAll("[0-9]+", "") + steps);
            }
        } else {
            directionMap.set(x, y, directions + VISITED + steps);
        }
        
        //logic for visiting next cell
        if (directions.contains(END)) {
            return steps;
        } else {
            int l = Integer.MAX_VALUE;
            int r = Integer.MAX_VALUE;
            int u = Integer.MAX_VALUE;
            int d = Integer.MAX_VALUE;
            
            if (directions.contains("l")) {
                l = traverse(directionMap, x - 1, y, steps + 1);
            }
            
            if (directions.contains("r")) {
                r = traverse(directionMap, x + 1, y, steps + 1);
            }
            
            if (directions.contains("d")) {
                d = traverse(directionMap, x, y + 1, steps + 1);
            }
            
            if (directions.contains("u")) {
                u = traverse(directionMap, x, y - 1, steps + 1);
            }
            
            return Math.min(Math.min(l,  r), Math.min(u, d));
        }
    }

    /**
     * 
     * @param heightMap
     * @return direction map from the top
     */
    private Grid<String> getDirectionMapFromTop(Grid<Integer> heightMap) {
        Grid<String> directionMap = new Grid<String>(heightMap.getWidth(), heightMap.getHeight(), false);
        
        for (int x=0; x<directionMap.getWidth(); x++) {
            for (int y=0; y<directionMap.getHeight(); y++) {
                int height = heightMap.get(x, y);
                String direction = "";
                
                if (height == 0 || height == 1) {
                    direction += END;
                } else if (height == 27) {
                    direction += START;
                }
                
                //left
                if (0 < x) {
                    int heightL = heightMap.get(x - 1, y);
                    if (height - 1 <= heightL) {
                        direction += "l";
                    }
                }
                
                //right
                if (x < directionMap.getWidth() - 1) {
                    int heightR = heightMap.get(x + 1, y);
                    if (height - 1 <= heightR) {
                        direction += "r";
                    }
                }
                
                //up
                if (0 < y) {
                    int heightU = heightMap.get(x, y - 1);
                    if (height - 1 <= heightU) {
                        direction += "u";
                    }
                }
                
                //down
                if (y < directionMap.getHeight() - 1) {
                    int heightD = heightMap.get(x, y + 1);
                    if (height - 1 <= heightD) {
                        direction += "d";
                    }
                }
                
                directionMap.set(x, y, direction);        
            }
        }
        
        return directionMap;
    }
    
    /**
     * @param heightMap
     * @return direction map from the bottom
     */
    private Grid<String> getDirectionMapFromBottom(Grid<Integer> heightMap) {
        Grid<String> dirMap = new Grid<String>(heightMap.getWidth(), heightMap.getHeight(), false);
        dirMap.set("u");
        
        for (int x=0; x<dirMap.getWidth(); x++) {
            for (int y=0; y<dirMap.getHeight(); y++) {
                int height = heightMap.get(x, y);
                String dir = "";
                
                if (height == 0) {
                    dir += START;
                } else if (height == 27) {
                    dir += END;
                }
                
                //left
                if (0 < x) {
                    int heightL = heightMap.get(x - 1, y);
                    if (heightL <= height + 1) {
                        dir += "l";
                    }
                }
                
                //right
                if (x < dirMap.getWidth() - 1) {
                    int heightR = heightMap.get(x + 1, y);
                    if (heightR <= height + 1) {
                        dir += "r";
                    }
                }
                
                //up
                if (0 < y) {
                    int heightU = heightMap.get(x, y - 1);
                    if (heightU <= height + 1) {
                        dir += "u";
                    }
                }
                
                //down
                if (y < dirMap.getHeight() - 1) {
                    int heightD = heightMap.get(x, y + 1);
                    if (heightD <= height + 1) {
                        dir += "d";
                    }
                }
                
                dirMap.set(x, y, dir);        
            }
        }
        
        return dirMap;
    }
    
    /**
     * @param input
     * @return heightMap
     */
    private Grid<Integer> getHeightMap(String input) {
        Grid<String> grid = new Grid<String>(input, true);
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                char c = grid.get(x, y).charAt(0);
                grid.set(x, y, charToInt(c) + "");
            }
        }
        
        return Grid.toIntGrid(grid);
    }
    
    /**
     * @param c
     * @return corresponding int
     */
    private int charToInt(char c) {
        if (c == START.charAt(0)) {
            return 0;
        }

        if (c == END.charAt(0)) {
            return 27;
        }
        
        return c - 96;
    }
}
