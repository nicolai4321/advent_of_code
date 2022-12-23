package advent_of_code.year2022.day23;

import java.util.ArrayList;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day23 extends RootDay {
    public Day23() {
        super(true, true, "4070", true, true, "881");
    }

    /**
     * How many empty ground tiles does the smallest rectangle with all elves contain?
     */
    @Override
    public String run1() {
        String[] input = input();
        ArrayList<Elf> elves = mapInput(input);
        simulateRounds(elves, 10, false);        
        return nrEmptyGroundTilesInSmallestRectangle(elves) + "";
    }
    
    /**
     * What is the number of the first round where no elf moves?
     */
    @Override
    public String run2() {
        String[] input = input();
        ArrayList<Elf> elves = mapInput(input);
        return simulateRounds(elves, Integer.MAX_VALUE, false).toString();
    }
    
    /**
     * @param elves
     * @param rounds
     * @param print
     * @return the first round where no elf moves
     */
    private Integer simulateRounds(ArrayList<Elf> elves, int rounds, boolean print) {
        if (print) {
            print(elves);            
        }
        for (int round=0; round<rounds; round++) {
            boolean someElfMoved = simulateRound(elves, round % 4);
            if (print) {
                print(elves);                
            }
            
            if (!someElfMoved) {
                return round + 1;
            }
        }
        return null;
    }

    /**
     * @param elves
     * @param prefferedDireciton
     * @return true if some elf has moved
     */
    private boolean simulateRound(ArrayList<Elf> elves, int prefferedDireciton) {
        //first half
        for (Elf elf : elves) {
            elf.planMove(elves, prefferedDireciton);
        }

        //second half
        boolean someElfMoved = false;
        for (Elf elf : elves) {
            if (elf.move(elves)) {
                someElfMoved = true;
            }
        }
        return someElfMoved;
    }

    /**
     * Print a grid of elves
     * @param elves
     */
    private void print(ArrayList<Elf> elves) {
        int[] minMax = getMinMax(elves);
        int[] widthHeight = getWidthHeight(elves);
        Grid grid = new Grid(widthHeight[0], widthHeight[1], false);
        grid.set(".");
        grid.setDivider("");
        grid.shift(-minMax[0], -minMax[1]);
        
        for (Elf elf : elves) {
            grid.set(elf.getX(), elf.getY(), "#");
        }
        grid.print();
    }

    /**
     * @param input
     * @return a list of elves
     */
    private ArrayList<Elf> mapInput(String[] input) {
        ArrayList<Elf> elves = new ArrayList<Elf>();
        for (int r=0; r<input.length; r++) {
            String row = input[r];
            char[] chars = row.toCharArray();
            for (int c=0; c<row.length(); c++) {
                if (chars[c] == '#') {
                    elves.add(new Elf(c, r));                    
                }
            }
        }
        return elves;
    }
    
    /**
     * @param elves
     * @return { minX, minY, maxX, maxY }
     */
    private int[] getMinMax(ArrayList<Elf> elves) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        
        for (Elf elf : elves) {
            minX = Math.min(minX, elf.getX());
            minY = Math.min(minY, elf.getY());
            maxX = Math.max(maxX, elf.getX());
            maxY = Math.max(maxY, elf.getY());
        }
        
        return new int[] { minX, minY, maxX, maxY };
    }
    
    /**
     * @param elves
     * @return { width, height }
     */
    private int[] getWidthHeight(ArrayList<Elf> elves) {
        int[] minMax = getMinMax(elves);
        int width = minMax[2] - minMax[0] + 1;
        int height = minMax[3] - minMax[1] + 1;    
        return new int[] { width, height };
    }
    
    /**
     * @param elves
     * @return nr of void cells in the smallest possible reactangle that contains all the elves
     */
    private int nrEmptyGroundTilesInSmallestRectangle(ArrayList<Elf> elves) {
        int[] widthHeight = getWidthHeight(elves);
        int area = widthHeight[0] * widthHeight[1];
        return area - elves.size();
    }
    
    private static String[] example() {
        return Read.getStrings(2022, 23, "example01.txt"); 
    }
 
    private static String[] input() {
        return Read.getStrings(2022, 23, "input01.txt"); 
    }
}
