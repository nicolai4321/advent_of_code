package advent_of_code.years.year1000;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.years.year1000.day01.Day01;

public class Year1000 extends Year {
    @Override
    public int getYear() {
        return 1000;
    }

    @Override
    public HashMap<Integer, RootDay> getDays() {
        HashMap<Integer, RootDay> days = new HashMap<Integer, RootDay>();
        put(days, new Day01(this, 1));
        return days;
    }
}
