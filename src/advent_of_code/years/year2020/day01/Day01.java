package advent_of_code.years.year2020.day01;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;

public class Day01 extends RootDay {
    public Day01(Year year, int day) {
        super(year, day, "41979", "193416912");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        int[] ints = Lists.stringsToInts(input.split("\n"));
        
        for (int i=0; i<ints.length; i++) {
            for (int j=0; j<ints.length; j++) {
                if (i != j) {
                    if (ints[i] + ints[j] == 2020) {
                        return (ints[i] * ints[j]) + "";
                    }
                }
            }
        }
        
        return null;
    }

    @Override
    public String run2(String input) {
        int[] ints = Lists.stringsToInts(input.split("\n"));
        
        for (int i=0; i<ints.length; i++) {
            for (int j=0; j<ints.length; j++) {
                for (int k=0; k<ints.length; k++) {
                    if (i != j) {
                        if (ints[i] + ints[j] + ints[k] == 2020) {
                            return (ints[i] * ints[j] * ints[k]) + "";
                        }
                    }
                }
            }
        }
        
        return null;
    }
}
