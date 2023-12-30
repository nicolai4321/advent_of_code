package advent_of_code.years.year2021.day02;

import advent_of_code.time.Day;
import advent_of_code.utils.Read;

public class Day02 extends Day {

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
		return Read.getStrings(2021, 2, "example01.txt");
	}
	
	private String[] getInput() {
		return Read.getStrings(2021, 2, "input01.txt");
	}
}
