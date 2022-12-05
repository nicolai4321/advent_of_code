package advent_of_code.year2022.day01;

import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.utils.Day;
import advent_of_code.utils.Read;

public class Day01 extends Day {
	@Override
	public String run1() {
		ArrayList<Integer> calories = getCalories(input().split("\n"));
		return getMax(calories) + "";
	}

	@Override
	public String run2() {
		ArrayList<Integer> calories = getCalories(input().split("\n"));
		return getTop3(calories) + "";
	}

	private static int getMax(ArrayList<Integer> calories) {
		return Collections.max(calories);
	}
	
	private static int getTop3(ArrayList<Integer> calories) {
		Collections.sort(calories, Collections.reverseOrder());
		return calories.get(0) + calories.get(1) + calories.get(2);
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
	
	@Override
	protected boolean test() {
		return run1().equals("74198") && run2().equals("209914");
	}
	
	private static String example() {
		return Read.getString(2022, 1, "example01.txt"); 
	}
	
	private static String input() {
		return Read.getString(2022, 1, "input01.txt"); 
	}
}
