package advent_of_code;

import advent_of_code.utils.Day;
import advent_of_code.utils.Log;
import advent_of_code.year2022.day01.Day01;

public class Main {
	public static void main(String[] args) {
		Log.show("#################");
		Day day = new Day01();
		
		String result1 = day.run1();
		Log.show("Result part I:\n'" + result1 + "'\n");
		Log.show("#################");
		
		String result2 = day.run2();
		Log.show("Result part II:\n'" + result2 + "'\n");
		Log.show("#################");
	}
}
