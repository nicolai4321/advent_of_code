package advent_of_code.years.year2020.day03;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;

public class Day03 extends RootDay {
    public Day03(Year year, int day) {
        super(year, day, "254", "1666768320");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] strings = input.split("\n");
        
        Grid<String> grid = new Grid<String>(strings, false);
        grid.setDivider("");
        int trees = findTrees(grid, 3, 1);
        
        return trees + "";
    }

    @Override
    public String run2(String input) {
        String[] strings = input.split("\n");
        
        Grid<String> grid = new Grid<String>(strings, false);
        grid.setDivider("");
        
        int trees0 = findTrees(grid, 1, 1);
        int trees1 = findTrees(grid, 3, 1);
        int trees2 = findTrees(grid, 5, 1);
        int trees3 = findTrees(grid, 7, 1);
        int trees4 = findTrees(grid, 1, 2);
        int treesTotal = trees0 * trees1 * trees2 * trees3 * trees4;
        
        return treesTotal + "";
    }
    
    private int findTrees(Grid<String> grid, int xSlope, int ySlope) {
        int w = grid.getWidth();
        int h = grid.getHeight();
        
        int x = 0;
        int y = 0;
        int trees = 0;
        
        while (true) {
            x = (x + xSlope) % w;
            y = y + ySlope;
            
            if (h <= y) {
                break;
            }
            
            String s = grid.get(x, y);
            if (s.equals("#")) {
                trees++;
            }
        }
        
        return trees;
    }
}
