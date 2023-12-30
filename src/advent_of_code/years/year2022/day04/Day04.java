package advent_of_code.years.year2022.day04;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day04 extends RootDay {
    public Day04(Year year, int day) {
        super(year, day, "550", "931");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        int overlap = 0;
        for (String pair : input.split("\n")) {
            overlap += calPairOverlapFully(pair);
        }
        
        return overlap + "";
    }
    
    @Override
    public String run2(String input) {
        int overlap = 0;
        
        for (String pair : input.split("\n")) {
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
}
