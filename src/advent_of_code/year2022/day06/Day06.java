package advent_of_code.year2022.day06;

import advent_of_code.utils.Day;
import advent_of_code.utils.Lists;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;

public class Day06 extends Day {
	@Override
	public String run1() {
		String input = example();
		Log.show(input);
		return null;
	}
	
	@Override
	public String run2() {
		return null;
	}
	
	@Override
	protected boolean test() {
		return true;
	}
	
	private static String example() {
		return Read.getString(2022, 6, "example01.txt"); 
	}
	
	private static String input() {
		return Read.getString(2022, 6, "input01.txt"); 
	}
}
