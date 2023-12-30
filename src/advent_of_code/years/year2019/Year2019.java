package advent_of_code.years.year2019;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.years.year2019.day01.Day01;

public class Year2019 extends Year {
    @Override
    public int getYear() {
        return 2019;
    }

    @Override
    public HashMap<Integer, RootDay> getDays() {
        HashMap<Integer, RootDay> days = new HashMap<Integer, RootDay>();
        put(days, new Day01(this, 1));
        return days;
    }
}
