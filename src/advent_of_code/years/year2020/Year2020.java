package advent_of_code.years.year2020;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.years.year2020.day01.Day01;
import advent_of_code.years.year2020.day02.Day02;
import advent_of_code.years.year2020.day03.Day03;
import advent_of_code.years.year2020.day04.Day04;
import advent_of_code.years.year2020.day05.Day05;
import advent_of_code.years.year2020.day06.Day06;
import advent_of_code.years.year2020.day07.Day07;
import advent_of_code.years.year2020.day08.Day08;

public class Year2020 extends Year {
    @Override
    public int getYear() {
        return 2020;
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
        return days;
    }
}
