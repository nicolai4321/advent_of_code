package advent_of_code.years.year2021.day11;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;

public class Day11 extends RootDay {
    private static int HAS_FLASHED = -1;
    
	public Day11(Year year, int day) {
		super(year, day, "1688", "403");
        setInput1("input01.txt");
        setInput2("input01.txt");
        this.disableRun1();
	}

	/**
     * How many flashes after 100 rounds?
     */
	@Override
	public String run1(String input) {
	    Grid<String> rawGrid = new Grid<String>(input, false);
	    Grid<Integer> grid = Grid.toIntGrid(rawGrid);
	    
	    int flashes = 0;
	    for (int i=0; i<100; i++) {
	        flashes += simulateRound(grid);
	    }
	    
	    return flashes + "";
	}

	/**
	 * Which round contains all flashes (100)?
	 */
	@Override
	public String run2(String input) {
        Grid<String> rawGrid = new Grid<String>(input, false);
        Grid<Integer> grid = Grid.toIntGrid(rawGrid);
        
        int round = 0;
        while (true) {
            round++;
            int flashes = simulateRound(grid);
            if (flashes == 100) {
                return round + "";
            }
        }
	}
	
	private int simulateRound(Grid<Integer> grid) {
        increateAll(grid);
        int flashes = flashAll(0, grid);
	    resetAllFlashes(grid);
        
	    return flashes;
	}
	
	private void resetAllFlashes(Grid<Integer> grid) {
	    for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                int value = grid.get(x, y);
                
                if (value == HAS_FLASHED) {
                    grid.set(x, y, 0);
                }
            }
        }
    }

    private int flashAll(int flashes, Grid<Integer> grid) {
       for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                int value = grid.get(x, y);
                
                if (value != HAS_FLASHED && 9 < value) {
                    grid.set(x, y, HAS_FLASHED);
                    spreadFlash(grid, x - 1, y - 1);
                    spreadFlash(grid, x    , y - 1);
                    spreadFlash(grid, x + 1, y - 1);

                    spreadFlash(grid, x - 1, y    );
                    spreadFlash(grid, x    , y    );
                    spreadFlash(grid, x + 1, y    );
                    
                    spreadFlash(grid, x - 1, y + 1);
                    spreadFlash(grid, x    , y + 1);
                    spreadFlash(grid, x + 1, y + 1);

                    return flashAll(flashes + 1, grid);
                }
            }
        }
	    
	    return flashes;
	}
	
	private void spreadFlash(Grid<Integer> grid, int x, int y) {
        if (x < 0 || y < 0 || grid.getWidth() <= x || grid.getHeight() <= y) {
            return;
        }
        
        int value = grid.get(x, y);
        
        if (value == HAS_FLASHED) {
            return;
        }
        
        grid.set(x, y, value + 1);
    }

    private void increateAll(Grid<Integer> grid) {
	    for (int x=0; x<grid.getWidth(); x++) {
	        for (int y=0; y<grid.getHeight(); y++) {
	            int value = grid.get(x, y);
	            grid.set(x, y, value + 1);
	        }
	    }
	}
}
