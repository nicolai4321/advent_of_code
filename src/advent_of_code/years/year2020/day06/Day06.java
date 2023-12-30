package advent_of_code.years.year2020.day06;

import java.util.HashMap;
import java.util.HashSet;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day06 extends RootDay {
    public Day06(Year year, int day) {
        super(year, day, "6680", "3117");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        String[] groups = input.split("\n\n");
        
        return countGroups(groups, true) + "";
    }
    
    @Override
    public String run2(String input) {
        String[] groups = input.split("\n\n");
        
        return countGroups(groups, false) + "";
    }
    
    private int countGroups(String[] groups, boolean any) {
        int total = 0;
        
        for (String group : groups) {
            String[] persons = group.split("\n");
            if (any) {
                total += countAny(persons);
            } else {
                total += countAll(persons);
            }
        }
        return total;
    }

    private int countAll(String[] persons) {
        HashMap<Character, Integer> yesAnswers = new HashMap<Character, Integer>();

        for (String person : persons) {
            for (char c : person.toCharArray()) {
                Integer currentValue = yesAnswers.get(c);
                currentValue = (currentValue == null) ? 0 : currentValue;
                yesAnswers.put(c, currentValue + 1);
            }
        }
        
        int total = 0;
        for (char c : yesAnswers.keySet()) {
            if (yesAnswers.get(c) == persons.length) {
                total += 1;
            }
        }
        
        return total;
    }

    private int countAny(String[] persons) {
        HashSet<Character> yesAnswers = new HashSet<Character>();
        
        for (String person : persons) {
            for (char c : person.toCharArray()) {
                yesAnswers.add(c);
            }
        }
        
        return yesAnswers.size();
    }
}
