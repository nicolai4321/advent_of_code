package advent_of_code.year2022.day01;

import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day01 extends RootDay {
	public Day01() {
		super(true, true, "74198", true, true, "209914");
	}

	@Override
	public String run1() {
		ArrayList<Integer> calories = getCalories(input().split("\n"));
		return Collections.max(calories) + "";
	}

	@Override
	public String run2() {
		ArrayList<Integer> calories = getCalories(input().split("\n"));
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
	
	private static String example() {
		return Read.getString(2022, 1, "example01.txt"); 
	}
	
	private static String input() {
		return Read.getString(2022, 1, "input01.txt"); 
	}
}
