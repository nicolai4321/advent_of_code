package advent_of_code.time;

import java.util.HashMap;

import advent_of_code.years.year2022.day01.Day01;
import advent_of_code.years.year2022.day02.Day02;

public abstract class Year {
    public abstract int getYear();
    public abstract HashMap<Integer, RootDay> getDays();
    
    protected void put(HashMap<Integer, RootDay> days, RootDay day) {
        days.put(day.getDay(), day);
    }
}
