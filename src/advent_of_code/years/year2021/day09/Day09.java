package advent_of_code.years.year2021.day09;


import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.pathing.PathFinder;

public class Day09 extends RootDay {
	public Day09(Year year, int day) {
		super(year, day, "512", "1600104");
        setInput1("input01.txt");
        setInput2("input01.txt");
	}

	@Override
	public String run1(String input) {
	    Grid<String> gridS = new Grid<String>(input, false);
	    Grid<Integer> grid = Grid.toIntGrid(gridS);
	    
		return getRiskLevel(grid) + "";
	}

	@Override
	public String run2(String input) {
	    Grid<String> gridS = new Grid<String>(input, false);
        Grid<Integer> grid = Grid.toIntGrid(gridS);
        ArrayList<int[]> lowPoints = getLowPoints(grid);
        ArrayList<Integer> pools = getPools(grid, lowPoints);
        Collections.sort(pools, Collections.reverseOrder());
        
        return (pools.get(0) * pools.get(1) * pools.get(2)) + "";
	}

	private ArrayList<Integer> getPools(Grid<Integer> grid, ArrayList<int[]> lowPoints) {
	    ArrayList<Integer> pools = new ArrayList<Integer>();
	    
        Grid<String> gridString = Grid.toStringGrid(grid);
        gridString.replace("9", PathFinder.WALL);

	    for (int[] lowPoint : lowPoints) {
	        int x = lowPoint[0];
	        int y = lowPoint[1];
	        int poolSize = PathFinder.legalCells(gridString, x, y);
	        pools.add(poolSize);
	    }
	    return pools;
    }

	private int getRiskLevel(Grid<Integer> grid) {
	    int riskLevel = 0;
	    
	    for (int x=0; x<grid.getWidth(); x++) {
	        for (int y=0; y<grid.getHeight(); y++) {
	            if (isLowPoint(grid, x, y)) {
	                riskLevel += (1 + grid.get(x, y));
	            }
	        }
	    }
	    
	    return riskLevel;
	}
	
    private ArrayList<int[]> getLowPoints(Grid<Integer> grid) {
	    ArrayList<int[]> lowPoints = new ArrayList<int[]>();
	    
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                if (isLowPoint(grid, x, y)) {
                    lowPoints.add(new int[] { x, y });
                }
            }
        }
        
        return lowPoints;
	}
	
	private boolean isLowPoint(Grid<Integer> grid, int x, int y) {
	    int value = grid.get(x, y);
	    
        Integer l = grid.safeGet(x - 1, y);
        Integer r = grid.safeGet(x + 1, y);
        Integer u = grid.safeGet(x, y - 1);
        Integer d = grid.safeGet(x, y + 1);
	    
	    return (l == null || value < l) && (r == null || value < r) && (u == null || value < u) && (d == null || value < d);
	}
}
