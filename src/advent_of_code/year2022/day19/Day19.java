package advent_of_code.year2022.day19;

import java.util.ArrayList;

import advent_of_code.utils.Lists;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day19 extends RootDay {
	public Day19() {
		super(true, true, null, false, true, null);
	}

	@Override
	public String run1() {
		String[] input = example();
		Log.show(input);
		
		return null;
	}

	@Override
	public String run2() {
		String[] input = input();
		
		return null;
	}

	private static String[] example() {
		return (Read.getStrings(2022, 19, "example01.txt")); 
	}
	
	private static String[] input() {
		return (Read.getStrings(2022, 19, "input01.txt")); 
	}
}
