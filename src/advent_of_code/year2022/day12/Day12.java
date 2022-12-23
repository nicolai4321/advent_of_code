package advent_of_code.year2022.day12;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day12 extends RootDay {
    public Day12() {
        super(2022, 12, "490", "488");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        Grid<Integer> heightMap = getHeightMap(input);
        Grid<String> directionMap = getDirectionMap(heightMap);
        return calculateSteps(directionMap) + "";
    }
    
    @Override
    public String run2(String input) {
        Grid<Integer> heightMap = getHeightMap(input);
        Grid<String> directionMap = getDirectionMap2(heightMap);
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
                if (s.contains("S")) {
                    return traverse(dirMap, x, y, 0);
                }
            }
        }
        
        throw new RuntimeException("Error, could not find start position");
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
        String options = directionMap.get(x, y);
        Integer otherSteps = null;

        //logic for visiting cell
        if (options.contains("v")) {
            otherSteps = Integer.parseInt(options.replaceAll(".*v", ""));
            if (otherSteps <= steps) {
                return Integer.MAX_VALUE;
            } else {
                directionMap.set(x, y, options.replaceAll("[0-9]+", "") + steps);
            }
        } else {
            directionMap.set(x, y, options + "v" + steps);
        }
        
        //logic for visiting next cell
        if (options.contains("E")) {
            return steps;
        } else {
            int l = Integer.MAX_VALUE;
            int r = Integer.MAX_VALUE;
            int u = Integer.MAX_VALUE;
            int d = Integer.MAX_VALUE;
            
            if (options.contains("l")) {
                l = traverse(directionMap, x - 1, y, steps + 1);
            }
            
            if (options.contains("r")) {
                r = traverse(directionMap, x + 1, y, steps + 1);
            }
            
            if (options.contains("d")) {
                d = traverse(directionMap, x, y + 1, steps + 1);
            }
            
            if (options.contains("u")) {
                u = traverse(directionMap, x, y - 1, steps + 1);
            }
            
            return Math.min(Math.min(l,  r), Math.min(u, d));
        }
    }

    private Grid<String> getDirectionMap2(Grid<Integer> heightMap) {
        Grid<String> directionMap = new Grid<String>(heightMap.getWidth(), heightMap.getHeight(), false);
        
        for (int x=0; x<directionMap.getWidth(); x++) {
            for (int y=0; y<directionMap.getHeight(); y++) {
                int height = heightMap.get(x, y);
                String direction = "";
                
                if (height == 0 || height == 1) {
                    direction += "E";
                } else if (height == 27) {
                    direction += "S";
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
     * S: start
     * E: end
     * 
     * u: up
     * d: down
     * l: left
     * r: right
     * 
     * @param heightMap
     * @return
     */
    private Grid<String> getDirectionMap(Grid<Integer> heightMap) {
        Grid<String> dirMap = new Grid<String>(heightMap.getWidth(), heightMap.getHeight(), false);
        dirMap.set("u");
        
        for (int x=0; x<dirMap.getWidth(); x++) {
            for (int y=0; y<dirMap.getHeight(); y++) {
                int height = heightMap.get(x, y);
                String dir = "";
                
                if (height == 0) {
                    dir += "S";
                } else if (height == 27) {
                    dir += "E";
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
        Grid<String> grid = new Grid<String>(input.split("\n"), true);
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
        if (c == 'S') {
            return 0;
        }

        if (c == 'E') {
            return 27;
        }
        
        return c - 96;
    }
    
    private static String[] example() {
        return Read.getStrings(2022, 12, "example01.txt"); 
    }
    
    private static String[] input() {
        return Read.getStrings(2022, 12, "input01.txt"); 
    }
}
