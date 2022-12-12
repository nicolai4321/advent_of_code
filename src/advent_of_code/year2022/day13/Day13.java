package advent_of_code.year2022.day13;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day13 extends RootDay {
	public Day13() {
		super(true, true, null, true, true, null);
	}

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
	
	private static String example() {
		return Read.getString(2022, 13, "example01.txt"); 
	}
	
	private static String input() {
		return Read.getString(2022, 13, "input01.txt"); 
	}
}
