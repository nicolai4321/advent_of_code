package advent_of_code;

import advent_of_code.utils.Log;
import advent_of_code.utils.RootDay;
import advent_of_code.year2022.day01.Day01;
import advent_of_code.year2022.day02.Day02;
import advent_of_code.year2022.day03.Day03;
import advent_of_code.year2022.day04.Day04;
import advent_of_code.year2022.day05.Day05;
import advent_of_code.year2022.day06.Day06;
import advent_of_code.year2022.day07.Day07;
import advent_of_code.year2022.day08.Day08;
import advent_of_code.year2022.day09.Day09;
import advent_of_code.year2022.day10.Day10;
import advent_of_code.year2022.day11.Day11;
import advent_of_code.year2022.day12.Day12;
import advent_of_code.year2022.day13.Day13;
import advent_of_code.year2022.day14.Day14;
import advent_of_code.year2022.day15.Day15;
import advent_of_code.year2022.day16.Day16;
import advent_of_code.year2022.day17.Day17;
import advent_of_code.year2022.day18.Day18;
import advent_of_code.year2022.day19.Day19;
import advent_of_code.year2022.day20.Day20;
import advent_of_code.year2022.day21.Day21;

public class Main {
	public static void main(String[] args) {
		RootDay day = new Day20();
		//day.run();
		runAllDays(true);
	}
	
	private static void runAllDays(boolean onlyOptimized) {
		Log.show("Run all days");
		Log.disable();
		new Day01().run();
		new Day02().run();
		new Day03().run();
		new Day04().run();
		new Day05().run();
		new Day06().run();
		new Day07().run();
		new Day08().run();
		new Day09().run();
		new Day10().run();
		new Day11().run();
		if (!onlyOptimized) {
			new Day12().run(); //TODO optimize
		}
		new Day13().run();
		new Day14().run();
		if (!onlyOptimized) {
			new Day15().run(); //TODO optimize
			new Day16().run(); //TODO optimize
		}
		new Day17().run();
		if (!onlyOptimized) {
			new Day18().run(); //TODO optimize
		}
		new Day19().run();
		new Day20().run();
		new Day21().run();
		Log.enable();
		Log.show("Done");
	}
}
