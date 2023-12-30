package advent_of_code.years.year2021;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.years.year2021.day04.Day04;
import advent_of_code.years.year2021.day05.Day05;
import advent_of_code.years.year2021.day06.Day06;
import advent_of_code.years.year2021.day07.Day07;
import advent_of_code.years.year2021.day08.Day08;
import advent_of_code.years.year2021.day09.Day09;
import advent_of_code.years.year2021.day10.Day10;
import advent_of_code.years.year2021.day11.Day11;
import advent_of_code.years.year2021.day12.Day12;

public class Year2021 extends Year {
    @Override
    public int getYear() {
        return 2021;
    }

    @Override
    public HashMap<Integer, RootDay> getDays() {
        HashMap<Integer, RootDay> days = new HashMap<Integer, RootDay>();
        put(days, new Day04(this, 4));
        put(days, new Day05(this, 5));
        put(days, new Day06(this, 6));
        put(days, new Day07(this, 7));
        put(days, new Day08(this, 8));
        put(days, new Day09(this, 9));
        put(days, new Day10(this, 10));
        put(days, new Day11(this, 11));
        put(days, new Day12(this, 12));
        return days;
    }
}
