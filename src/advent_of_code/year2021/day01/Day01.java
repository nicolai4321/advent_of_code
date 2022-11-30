package advent_of_code.year2021.day01;

import advent_of_code.Day;
import advent_of_code.Utils;

public class Day01 implements Day {
	@Override
	public String run1() {
		Integer lastMeasurement = null;
		int increased = 0;
		for (int measurement : input()) {
			if (lastMeasurement != null && lastMeasurement < measurement) {
				increased++;
			}
			lastMeasurement = measurement;
		}
		
		return increased + "";
	}

	@Override
	public String run2() {
		Integer measurement1 = null;
		Integer measurement2 = null;
		Integer sum1 = null;
		int increased = 0;
		for (int measurement0 : input()) {
			if (measurement1 != null && measurement2 != null) {
				int sum0 = measurement0 + measurement1 + measurement2;
				if (sum1 != null && sum1 < sum0) {
					increased++;
				}
				sum1 = sum0;
			}
			
			measurement2 = measurement1;
			measurement1 = measurement0;
		}
		
		return increased + "";
	}
	
	private static int[] example() {
		String path = "year2021/day01/example01.txt";
		return Utils.readFileAsInts(path); 
	}
	
	private static int[] input() {
		String path = "year2021/day01/input01.txt";
		return Utils.readFileAsInts(path); 
	}
}