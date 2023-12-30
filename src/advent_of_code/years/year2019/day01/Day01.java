package advent_of_code.years.year2019.day01;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;

public class Day01 extends RootDay {
    public Day01(Year year, int day) {
        super(year, day, "3231195", "4843929");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] lst = input.split("\n");
        int[] ints = Lists.stringsToInts(lst);
                
        int sum = 0;
        for (int i : ints) {
            sum += (i/3 - 2);
        }
        
        return sum + "";
    }

    @Override
    public String run2(String input) {
        String[] lst = input.split("\n");
        int[] ints = Lists.stringsToInts(lst);
                
        int sum = 0;
        for (int i : ints) {
            sum += calc(i);
        }
        
        return sum + "";
    }
    
    private int calc(int i) {
        if (i <= 0) {
            return 0;
        }

        int value = i/3 - 2;
        
        if (value <= 0) {
            return 0;
        }
        
        return value + calc(value);
    }
}
