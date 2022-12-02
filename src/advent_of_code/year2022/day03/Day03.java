package advent_of_code.year2022.day03;

import advent_of_code.utils.Day;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;

public class Day03 extends Day {
	@Override
	public String run1() {
		Log.show(input());
		return null;
	}

	@Override
	public String run2() {
		return null;
	}
	
	@Override
	protected boolean test() {
		if (run1() == null) {
			Log.show("run1() returns null");
			return false;
		}
		
		return true;
	}

	private static String example() {
		return Read.getString(2022, 3, "example01.txt"); 
	}
	
	private static String input() {
		return Read.getString(2022, 3, "input01.txt"); 
	}
}
