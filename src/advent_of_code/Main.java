package advent_of_code;

import advent_of_code.year2021.day02.Day02;
import advent_of_code.year2021.day03.Day03;
import advent_of_code.year2021.day04.Day04;

public class Main {
	public static void main(String[] args) {
		System.out.println("Running\n");
		Day day = new Day04();
		
		String result1 = day.run1();
		System.out.println("Result part I:\n'" + result1 + "'\n");

		String result2 = day.run2();
		System.out.println("Result part II:\n'" + result2 + "'\n");
		System.out.println("Done");
	}
}
