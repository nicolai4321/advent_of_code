package advent_of_code.years.year2018;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.years.year2018.day01.Day01;

public class Year2018 extends Year {
    @Override
    public int getYear() {
        return 2018;
    }

    @Override
    public HashMap<Integer, RootDay> getDays() {
        HashMap<Integer, RootDay> days = new HashMap<Integer, RootDay>();
        put(days, new Day01(this, 1));
        return days;
    }
}
