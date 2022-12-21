package advent_of_code.year2022.day04;

import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day04 extends RootDay {
    public Day04() {
        super(true, true, "550", true, true, "931");
    }

    @Override
    public String run1() {
        String[] input = input();
        
        int overlap = 0;
        for (String pair : input) {
            overlap += calPairOverlapFully(pair);
        }
        
        return overlap + "";
    }
    
    @Override
    public String run2() {
        String[] input = input();
        int overlap = 0;
        
        for (String pair : input) {
            overlap += calPairOverlap(pair);
        }
        return overlap + "";
    }
    
    private int calPairOverlapFully(String pair) {
        String elf0 = pair.split(",")[0];
        String elf1 = pair.split(",")[1];
        
        int[] range0 = getRange(elf0);
        int[] range1 = getRange(elf1);
        
        if (range0[0] <= range1[0] && range1[1] <= range0[1]) {
            return 1;
        }
        
        if (range1[0] <= range0[0] && range0[1] <= range1[1]) {
            return 1;
        }
        
        return 0;
    }
    
    private int calPairOverlap(String pair) {
        String elf0 = pair.split(",")[0];
        String elf1 = pair.split(",")[1];
        int[] range0 = getRange(elf0);
        int[] range1 = getRange(elf1);
        
        if (range1[0] <= range0[1] && range0[1] <= range1[1]) {
            return 1;
        }
        
        if (range0[0] <= range1[1] && range1[1] <= range0[1]) {
            return 1;
        }
        
        return 0;
    }
    
    /**
     * @param nrs
     * @return range
     */
    private int[] getRange(String rangeString) {
        String[] split = rangeString.split("-");
        return new int[] { Integer.parseInt(split[0]), Integer.parseInt(split[1]) };
    }
    
    private static String[] example() {
        return Read.getStrings(2022, 4, "example01.txt"); 
    }
    
    private static String[] input() {
        return Read.getStrings(2022, 4, "input01.txt"); 
    }
}
