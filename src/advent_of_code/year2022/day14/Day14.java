package advent_of_code.year2022.day14;

import java.util.ArrayList;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day14 extends RootDay {
    public Day14() {
        super(true, true, "795", true, true, "30214");
    }

    @Override
    public String run1() {
        String[] input = input();
        ArrayList<ArrayList<int[]>> structures = transformInput(input);
        int[] boundaries = findBoundaries(structures);
        Grid<String> grid = generateGrid(structures, boundaries);
        int nrSand = simulateSand(grid, boundaries[3], 500, 0, false);
        return nrSand + "";
    }

    @Override
    public String run2() {
        String[] input = input();
        ArrayList<ArrayList<int[]>> structures = transformInput(input);
        insertHorizontalLine(structures, 175);
        int[] boundaries = findBoundaries(structures);
        Grid<String> grid = generateGrid(structures, boundaries);
        int nrSand = simulateSand(grid, boundaries[3], 500, 0, true) + 1;

        return nrSand + "";
    }
    
    /**
     * Insert horizontal line at the bottom
     * @param structures
     * @param length
     */
    private void insertHorizontalLine(ArrayList<ArrayList<int[]>> structures, int length) {
        int maxY = 0;
        int minX = Integer.MAX_VALUE;
        int maxX = -9999;
        for (ArrayList<int[]> lines : structures) {
            for (int[] coords : lines) {
                int x = coords[0];
                int y = coords[1];
                maxY = Math.max(maxY, y);
                maxX = Math.max(maxX, x);
                minX = Math.min(minX, x);
            }
        }
        
        int y = maxY + 2;
        ArrayList<int[]> line = new ArrayList<int[]>();
        line.add(new int[] {minX - length, y});
        line.add(new int[] {maxX + length, y});
        
        structures.add(line);
    }

    /**
     * @param grid
     * @param maxY
     * @param startX
     * @param startY
     * @param stopAtStart
     * @return number of sand that is placed
     */
    private int simulateSand(Grid<String> grid, int maxY, int startX, int startY, boolean stopAtStart) {
        int nrSand = 0;
        boolean falling = true;
        while (falling) {
            nrSand++;
            falling = placeSand(grid, startX, startY, maxY, stopAtStart);
        }
        return nrSand - 1;
    }

    /**
     * @param grid
     * @param x
     * @param y
     * @param maxY
     * @param stopAtStart
     * @return true if the sand can be placed
     */
    private boolean placeSand(Grid<String> grid, int x, int y, int maxY, boolean stopAtStart) {
        if (maxY <= y) {
            return false;
        }
        
        String valueBelow = grid.get(x, y + 1);
        if (valueBelow.equals(".")) {
            return placeSand(grid, x, y + 1, maxY, stopAtStart);
        }
        
        String valueLeft = grid.get(x - 1, y + 1);
        if (valueLeft.equals(".")) {
            return placeSand(grid, x - 1, y + 1, maxY, stopAtStart);
        }
        
        String valueRight = grid.get(x + 1, y + 1);
        if (valueRight.equals(".")) {
            return placeSand(grid, x + 1, y + 1, maxY, stopAtStart);
        }
        
        String valueHere = grid.get(x, y);
        if (stopAtStart && valueHere.equals("+")) {
            return false;
        }
        
        grid.set(x, y, "o");
        return true;
    }

    /**
     * @param list
     * @param boundaries
     * @return grid for the structures
     */
    private Grid<String> generateGrid(ArrayList<ArrayList<int[]>> structures, int[] boundaries) {
        int x0 = boundaries[0];
        int x1 = boundaries[1];
        int y0 = boundaries[2];
        int y1 = boundaries[3];
        
        int width = x1 - x0 + 1;
        int height = y1 - y0 + 1;
        
        Grid<String> grid = new Grid<String>(width, height, false);
        grid.shift(-x0, -y0);
        grid.set(".");
        grid.setDivider("");
        grid.set(500, 0, "+");
        
        for (ArrayList<int[]> lines : structures) {
            for (int i=0; (i+1)<lines.size(); i++) {
                int[] coords0 = lines.get(i);
                int[] coords1 = lines.get(i + 1);
                
                if (coords0[0] == coords1[0]) {
                    //same x
                    int x = coords0[0];
                    int minY = Math.min(coords0[1], coords1[1]);
                    int maxY = Math.max(coords0[1], coords1[1]);
                    
                    for (int y=minY; y<=maxY; y++) {
                        grid.set(x, y, "#");
                    }
                } else {
                    //same y
                    int minX = Math.min(coords0[0], coords1[0]);
                    int maxX = Math.max(coords0[0], coords1[0]);
                    int y = coords0[1];
                    
                    for (int x=minX; x<=maxX; x++) {
                        grid.set(x, y, "#");
                    }
                }
            }
        }
        
        return grid;
    }

    /**
     * @param list
     * @return boundaries
     */
    private int[] findBoundaries(ArrayList<ArrayList<int[]>> list) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        
        for (ArrayList<int[]> line : list) {
            for (int[] coords : line) {
                int x = coords[0];
                int y = coords[1];
                minX = Math.min(x, minX);
                maxX = Math.max(x, maxX);
                minY = Math.min(y, minY);
                maxY = Math.max(y, maxY);
            }
        }
        
        //ensure start coordinate
        minX = Math.min(500, minX);
        maxX = Math.max(500, maxX);
        minY = Math.min(0, minY);
        maxY = Math.max(0, maxY);

        return new int[] { minX, maxX, minY, maxY };
    }

    /**
     * @param input
     * @return list of structures
     */
    private ArrayList<ArrayList<int[]>> transformInput(String[] input) {
        ArrayList<ArrayList<int[]>> structures = new ArrayList<ArrayList<int[]>>();
        for (String line : input) {
            ArrayList<int[]> lineSegments = new ArrayList<int[]>();
            for (String coords : line.split(" -> ")) {
                String[] coord = coords.split(",");
                int x = Integer.parseInt(coord[0]);
                int y = Integer.parseInt(coord[1]);
                int[] pair = new int[] { x, y };
                lineSegments.add(pair);
            }
            structures.add(lineSegments);
        }
        return structures;
    }

    private static String[] example() {
        return (Read.getStrings(2022, 14, "example01.txt")); 
    }
    
    private static String[] input() {
        return (Read.getStrings(2022, 14, "input01.txt")); 
    }
}
