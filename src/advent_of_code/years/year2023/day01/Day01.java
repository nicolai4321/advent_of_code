package advent_of_code.years.year2023.day01;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Log;

public class Day01 extends RootDay {
    public Day01(Year year, int day) {
        super(year, day, "55123", "55260");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * What is the sum of all lines if the number for every line contains of the first and last digit?
     */
    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        
        int sum = 0;
        for (String line : lines) {
            line = line.replaceAll("[a-z]", "");
            if (line.length() == 0) {
                continue;
            }
            String nr = line.charAt(0) + "" + line.charAt(Math.max(0, line.length() - 1));
            sum += Integer.parseInt(nr);
        }
        
        return sum + "";
    }

    /**
     * What is the sum of all lines if the number for every line contains of the first and last digit? Including written numbers
     */
    @Override
    public String run2(String input) {
        String[] lines = input.split("\n");
        
        int sum = 0;
        for (String line : lines) {
            line = line.replaceAll("one",   "one1one")
                       .replaceAll("two",   "two2two")
                       .replaceAll("three", "three3three")
                       .replaceAll("four",  "four4four")
                       .replaceAll("five",  "five5five")
                       .replaceAll("six",   "six6six")
                       .replaceAll("seven", "seven7seven")
                       .replaceAll("eight", "eight8eight")
                       .replaceAll("nine",  "nine9nine")
                       .replaceAll("[a-z]", "");

            String nr = line.charAt(0) + "" + line.charAt(line.length() - 1);
            sum += Integer.parseInt(nr);
        }
        
        return sum + "";
    }
}
