package advent_of_code.years.year2018.day01;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;

public class Day01 extends RootDay {
    public Day01(Year year, int day) {
        super(year, day, "500", "709");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] str = input.split("\n");
        int[] ints = Lists.stringsToInts(str);
        
        int sum = 0;
        for (int i : ints) {
            sum += i;
        }
        
        return sum + "";
    }

    @Override
    public String run2(String input) {
        HashMap<Integer, Integer> hM = new HashMap<>();
        
        String[] str = input.split("\n");
        int[] ints = Lists.stringsToInts(str);
        
        int sum = 0;
        hM.put(sum, 1);
        for (int j=0; j<ints.length; j++) {
            sum += ints[j];
            
            if (hM.get(sum) == null) {
                hM.put(sum, 1);
            } else {
                return sum + "";
            }
            
            if (j == ints.length - 1) {
                j = -1;
            }
        }
        
        throw new RuntimeException("No result");
    }
}
