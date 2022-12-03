package advent_of_code.year2022.day04;

import advent_of_code.utils.Day;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;

public class Day04 extends Day {
	@Override
	public String run1() {
		String input = example();
		Log.show(input);
		return null;
	}
	
	@Override
	public String run2() {
		String input = example();
		return null;
	}

	/**
	 * Test
	 */
	@Override
	protected boolean test() {
		if (run1() == null) {
			Log.show("run1() is false");
			return false;
		}
		
		return true;
	}
	
	private static String example() {
		return Read.getString(2022, 4, "example01.txt"); 
	}
	
	private static String input() {
		return Read.getString(2022, 4, "input01.txt"); 
	}
}
