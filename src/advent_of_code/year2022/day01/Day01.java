package advent_of_code.year2022.day01;

import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.utils.RootDay;

public class Day01 extends RootDay {
    public Day01() {
        super(2022, 1, "74198", "209914");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        ArrayList<Integer> calories = getCalories(input.split("\n"));
        return Collections.max(calories) + "";
    }

    @Override
    public String run2(String input) {
        ArrayList<Integer> calories = getCalories(input.split("\n"));
        Collections.sort(calories, Collections.reverseOrder());
        int sumTop3 = calories.get(0) + calories.get(1) + calories.get(2);
        return sumTop3 + "";
    }
    
    /**
     * @param input
     * @return calories
     */
    private static ArrayList<Integer> getCalories(String[] input) {
        ArrayList<Integer> calories = new ArrayList<Integer>();
        int cal = 0;
        for (String line : input) {
            if (line.isBlank()) {
                calories.add(cal);
                cal = 0;
            } else {
                cal += Integer.parseInt(line);                
            }
        }
        calories.add(cal);
        return calories;
    }
}
