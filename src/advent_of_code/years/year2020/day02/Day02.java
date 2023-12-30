package advent_of_code.years.year2020.day02;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day02 extends RootDay {
    public Day02(Year year, int day) {
        super(year, day, "560", "303");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        int valid = 0;
        for (String s : input.split("\n")) {
            int min = Integer.parseInt(s.split("-")[0]);
            int max = Integer.parseInt((s.split("-")[1]).split(" ")[0]);
            String ch = s.split(" ")[1].replaceAll(":", "");
            String pass = s.split(" ")[2];
            
            int i = 0;
            for (char c : pass.toCharArray()) {
                if (ch.equals(c + "")) {
                    i++;
                }
            }
            
            if (min <= i && i <= max) {
                valid++;
            }
        }
        
        return valid + "";
    }

    @Override
    public String run2(String input) {
        int valid = 0;
        for (String s : input.split("\n")) {
            int index0 = Integer.parseInt(s.split("-")[0]);
            int index1 = Integer.parseInt((s.split("-")[1]).split(" ")[0]);
            String ch = s.split(" ")[1].replaceAll(":", "");
            String pass = s.split(" ")[2];
            
            char[] chars = pass.toCharArray();
            boolean v = ch.equals(chars[index0 - 1] + "") ^ ch.equals(chars[index1 - 1] + "");
            if (v) {
                valid++;
            }
        }
        
        return valid + "";
    }
}
