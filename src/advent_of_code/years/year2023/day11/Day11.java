package advent_of_code.years.year2023.day11;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.math.MathOp;
import advent_of_code.utils.math.big.BigInt;

public class Day11 extends RootDay {
    public Day11(Year year, int day) {
        super(year, day, "9536038", "447744640566");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        return pairLength(grid, 2) + "";
    }


    @Override
    public String run2(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        return pairLength(grid, 1000000) + "";
    }
    
    /**
     * @param grid
     * @param increase
     * @return sum of distances between pairs
     */
    private BigInt pairLength(Grid<String> grid, int emptySpaceIncrease) {
        ArrayList<int[][]> pairs = getPairs(grid);

        BigInt sum = BigInt.get(0);
        ArrayList<Integer> emptyRows = getEmptyRows(grid);
        ArrayList<Integer> emptyColumns = getEmptyColumns(grid);
        
        BigInt emptySpaceDistance = BigInt.get(emptySpaceIncrease);
        for (int i=0; i<pairs.size(); i++) {
            int[][] pair = pairs.get(i);
            int[] coord0 = pair[0];
            int[] coord1 = pair[1];
            BigInt distance = BigInt.get(MathOp.manhattenDistance(coord0[0], coord0[1], coord1[0], coord1[1]));
            
            int minX = Math.min(coord0[0], coord1[0]);
            int maxX = Math.max(coord0[0], coord1[0]);
            int minY = Math.min(coord0[1], coord1[1]);
            int maxY = Math.max(coord0[1], coord1[1]);
            
            for(int x : emptyColumns) {
                if (minX <= x && x <= maxX) {
                    distance = distance.add(emptySpaceDistance).sub(1);
                }
            }
            
            for (int y : emptyRows) {
                if (minY <= y && y <= maxY) {
                    distance = distance.add(emptySpaceDistance).sub(1);
                }
            }
            
            sum = sum.add(distance);
        }
        
        return sum;
    }
    
    /**
     * @param grid
     * @return pairs of coordinates
     */
    private ArrayList<int[][]> getPairs(Grid<String> grid) {
        ArrayList<int[]> coords = new ArrayList<int[]>();
        
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                if (grid.get(x, y).equals("#")) {
                    coords.add(new int[] {x,y});
                }
            }
        }
        
        ArrayList<int[][]> pairs = new ArrayList<int[][]>();

        for (int i=0; i<coords.size(); i++) {
            for (int j=i+1; j<coords.size(); j++) {
                pairs.add(new int[][] { coords.get(i), coords.get(j) });
            }
        }
        
        return pairs;
    }
    
    /**
     * @param grid
     * @return an array of indexes for empty columns
     */
    private ArrayList<Integer> getEmptyColumns(Grid<String> grid) {
        ArrayList<Integer> emptyRows = new ArrayList<Integer>();
        
        for (int x=0; x<grid.getWidth(); x++) {
            boolean empty = true;
            for (int y=0; y<grid.getHeight(); y++) {
                if (grid.get(x, y).equals("#")) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                emptyRows.add(x);
            }
        }
        
        return emptyRows;
    }

    /**
     * @param grid
     * @return an array of indexes for empty rows
     */
    private ArrayList<Integer> getEmptyRows(Grid<String> grid) {
        ArrayList<Integer> emptyRows = new ArrayList<Integer>();
        
        for (int y=0; y<grid.getHeight(); y++) {
            boolean empty = true;
            for (int x=0; x<grid.getWidth(); x++) {
                if (grid.get(x, y).equals("#")) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                emptyRows.add(y);
            }
        }
        
        return emptyRows;
    }
}
