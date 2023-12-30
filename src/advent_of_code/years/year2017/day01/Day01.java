package advent_of_code.years.year2017.day01;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.CharUtils;

public class Day01 extends RootDay {
    public Day01(Year year, int day) {
        super(year, day, "1097", "1188");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        char[] chars = input.toCharArray();
        return sumMatches(chars, 1) + "";
    }

    @Override
    public String run2(String input) {
        char[] chars = input.toCharArray();
        return sumMatches(chars, chars.length/2) + "";
    }
    
    private int sumMatches(char[] chars, int next) {
        int sum = 0;
        
        for (int i=0; i<chars.length; i++) {
            char c = chars[i];
            char matchC = chars[(i+next) % chars.length];
            
            if (c == matchC) {
                sum += CharUtils.toInt(c);
            }
        }
        
        return sum;
    }
}
