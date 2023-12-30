package advent_of_code.years.year2023.day03;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.Lists;

public class Day03 extends RootDay {
    public Day03(Year year, int day) {
        super(year, day, "560670", "91622824");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * Sum of numbers adjacent to a symbol
     */
    @Override
    public String run1(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");
        
        int sum = 0;
        
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                String s = grid.get(x, y);
                if (!s.equals(".") && !s.matches("[0-9]")) {
                    ArrayList<Integer> numbers = getAndRemoveNeighbourNumbers(grid, x, y);
                    sum += Lists.sum(numbers);
                }
            }
        }
        
        return sum + "";
    }

    /**
     * Sum of gear ratios. A gear ratio is a * multiplied with exactly two adjacent numbers
     */
    @Override
    public String run2(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");
        
        return getGearRatios(grid) + "";
    }

    /**
     * @param grid
     * @return gear ratios for grid
     */
    private int getGearRatios(Grid<String> grid) {
        int gearRatios = 0;
        
        for (int x=0; x<grid.getWidth(); x++) {
            for (int y=0; y<grid.getHeight(); y++) {
                String s = grid.safeGet(x, y);
                if (s != null && s.equals("*")) {
                    gearRatios += gearGearRatio(grid, x, y);
                }
            }
        }
        
        return gearRatios;
    }

    /**
     * @param grid
     * @param x
     * @param y
     * @return gear ratio for x,y
     */
    private int gearGearRatio(Grid<String> grid, int x, int y) {
        ArrayList<Integer> numbersAdjacent = getAndRemoveNeighbourNumbers(grid, x, y);
        
        if (numbersAdjacent.size() == 2) {
            int i = numbersAdjacent.get(0) * numbersAdjacent.get(1);
            return i;
        }
        
        return 0;
    }

    /**
     * A list of numbers adjacent to the given index. The numbers will be removed.
     * 
     * @param grid
     * @param posX
     * @param posY
     * @return A list of numbers adjacent to the given index
     */
    private ArrayList<Integer> getAndRemoveNeighbourNumbers(Grid<String> grid, int posX, int posY) {
        ArrayList<int[]> neighbours = grid.getNeighbours(posX, posY, true);
        ArrayList<Integer> numbersAdjacent = new ArrayList<Integer>();
        
        for (int[] neighbour : neighbours) {
            int x = neighbour[0];
            int y = neighbour[1];
            String s = grid.get(x, y);
            
            if (s.matches("[0-9]")) {
                numbersAdjacent.add(traceAndRemoveNumber(x, y, grid));
            }
        }
        
        return numbersAdjacent;
    }

    /**
     * Given any index of a number in the grid, the number will be traced removed and returned
     * @param posX
     * @param posY
     * @param grid
     * @return number
     */
    private int traceAndRemoveNumber(int posX, int posY, Grid<String> grid) {
        //find index of start of number
        while(true) {
            posX--;
            String s = grid.safeGet(posX, posY);
            if (s == null || !s.matches("[0-9]")) {
                posX++;
                break;
            }
        }
        
        //trace and create number
        String nr = "";
        while (true) {
            String s = grid.safeGet(posX, posY);
            
            if (s != null && s.matches("[0-9]")) {
                nr += s;
                grid.set(posX, posY, ".");
            } else {
                return Integer.parseInt(nr);
            }
            posX++;
        }
    }
}
