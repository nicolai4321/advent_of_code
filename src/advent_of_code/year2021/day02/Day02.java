package advent_of_code.year2021.day02;

import advent_of_code.Day;
import advent_of_code.Utils;

public class Day02 implements Day {

	@Override
	public String run1() {
		int depth = 0;
		int horizontalPos = 0;
		
		for (String line : getInput()) {
			int value = Integer.parseInt(line.replaceAll("[A-Za-z]+ ", ""));
			
			if (line.matches("forward.*")) {
				horizontalPos += value;
			}
			
			if (line.matches("up.*")) {
				depth -= value;		
			}
			
			if (line.matches("down.*")) {
				depth += value;
			}
		}
		return (depth * horizontalPos) + "";
	}

	@Override
	public String run2() {
		int aim = 0;
		int depth = 0;
		int horizontalPos = 0;
		
		for (String line : getInput()) {
			int value = Integer.parseInt(line.replaceAll("[A-Za-z]+ ", ""));
			
			if (line.matches("forward.*")) {
				horizontalPos += value;
				depth += aim * value;
			}
			
			if (line.matches("up.*")) {
				aim -= value;		
			}
			
			if (line.matches("down.*")) {
				aim += value;
			}
		}
		return (depth * horizontalPos) + "";
	}

	private String[] getExample() {
		String path = "year2021/day02/example01.txt";
		return Utils.readFileAsStrings(path);
	}
	
	private String[] getInput() {
		String path = "year2021/day02/input01.txt";
		return Utils.readFileAsStrings(path);
	}
}
