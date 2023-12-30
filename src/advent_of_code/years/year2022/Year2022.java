package advent_of_code.years.year2022;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.years.year2022.day01.Day01;
import advent_of_code.years.year2022.day02.Day02;
import advent_of_code.years.year2022.day03.Day03;
import advent_of_code.years.year2022.day04.Day04;
import advent_of_code.years.year2022.day05.Day05;
import advent_of_code.years.year2022.day06.Day06;
import advent_of_code.years.year2022.day07.Day07;
import advent_of_code.years.year2022.day08.Day08;
import advent_of_code.years.year2022.day09.Day09;
import advent_of_code.years.year2022.day10.Day10;
import advent_of_code.years.year2022.day11.Day11;
import advent_of_code.years.year2022.day12.Day12;
import advent_of_code.years.year2022.day13.Day13;
import advent_of_code.years.year2022.day14.Day14;
import advent_of_code.years.year2022.day15.Day15;
import advent_of_code.years.year2022.day16.Day16;
import advent_of_code.years.year2022.day17.Day17;
import advent_of_code.years.year2022.day18.Day18;
import advent_of_code.years.year2022.day19.Day19;
import advent_of_code.years.year2022.day20.Day20;
import advent_of_code.years.year2022.day21.Day21;
import advent_of_code.years.year2022.day22.Day22;
import advent_of_code.years.year2022.day23.Day23;
import advent_of_code.years.year2022.day24.Day24;
import advent_of_code.years.year2022.day25.Day25;

public class Year2022 extends Year {
    @Override
    public int getYear() {
        return 2022;
    }
    
    @Override
    public HashMap<Integer, RootDay> getDays() {
        HashMap<Integer, RootDay> days = new HashMap<Integer, RootDay>();
        put(days, new Day01(this, 1));
        put(days, new Day02(this, 2));
        put(days, new Day03(this, 3));
        put(days, new Day04(this, 4));
        put(days, new Day05(this, 5));
        put(days, new Day06(this, 6));
        put(days, new Day07(this, 7));
        put(days, new Day08(this, 8));
        put(days, new Day09(this, 9));
        put(days, new Day10(this, 10));
        put(days, new Day11(this, 11));
        put(days, new Day12(this, 12));
        put(days, new Day13(this, 13));
        put(days, new Day14(this, 14));
        put(days, new Day15(this, 15));
        put(days, new Day16(this, 16));
        put(days, new Day17(this, 17));
        put(days, new Day18(this, 18));
        put(days, new Day19(this, 19));
        put(days, new Day20(this, 20));
        put(days, new Day21(this, 21));
        put(days, new Day22(this, 22));
        put(days, new Day23(this, 23));
        put(days, new Day24(this, 24));
        put(days, new Day25(this, 25));
        return days;
    }
}
