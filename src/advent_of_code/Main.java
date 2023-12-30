package advent_of_code;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Log;
import advent_of_code.years.year1000.Year1000;
import advent_of_code.years.year2017.Year2017;
import advent_of_code.years.year2018.Year2018;
import advent_of_code.years.year2019.Year2019;
import advent_of_code.years.year2020.Year2020;
import advent_of_code.years.year2021.Year2021;
import advent_of_code.years.year2022.Year2022;
import advent_of_code.years.year2023.Year2023;

public class Main {
    public static HashMap<Integer, Year> years; 
    
    public static void main(String[] args) {
        years = new HashMap<Integer, Year>();
        years.put(2017, new Year2017());
        years.put(2018, new Year2018());
        years.put(2019, new Year2019());
        years.put(2020, new Year2020());
        years.put(2021, new Year2021());
        years.put(2022, new Year2022());
        years.put(2023, new Year2023());

        if (args.length == 0) {
            Log.show("Running every year");
            
            for (Integer yearNr : years.keySet()) {
                Year year = years.get(yearNr);
                runAllDays(year.getYear(), year.getDays());
            }
        } else if (args.length == 1 && isInt(args[0])) {
            int yearNr = Integer.parseInt(args[0]);
            Year year = years.get(yearNr);
            
            if (yearNr == 1000) {
                runDay(new Year1000().getDays().get(1), false);
                return;
            }

            if (year == null) {
                Log.warn("Year " + yearNr + " is not implemented.");
                return;
            }
            
            runAllDays(year.getYear(), year.getDays());
        } else if (args.length == 2 && isInt(args[0]) && isInt(args[1])) {
            int yearNr = Integer.parseInt(args[0]);
            Year year = years.get(yearNr);
            
            if (year == null) {
                Log.warn("Year " + yearNr + " is not implemented.");
                return;
            }
            
            int dayNr = Integer.parseInt(args[1]);
            
            if (dayNr < 1 || 25 < dayNr) {
                Log.warn("Please provide a day between 1 and 25");
            } else {
                RootDay day = year.getDays().get(dayNr);
                
                if (day == null) {
                    throw new RuntimeException("Could not find day " + dayNr + " year " + yearNr);
                }
                
                runDay(day, false);
            }
        } else {
            Log.warn("Invalid argument. Expected 0, 1 or 2 numbers");
        }
    }
    
    /**
     * @param s
     * @return true if s is an int
     */
    private static boolean isInt(String s) {
        return s.matches("[0-9]+");
    }
    
    /**
     * Runs all days for a given year
     * @param year
     * @param days
     */
    private static void runAllDays(int year, HashMap<Integer, RootDay> days) {
        for (int dayNr : days.keySet()) {
            RootDay day = days.get(dayNr);
            runDay(day, true);
        }
        
        Log.show("Done");
    }
    
    /**
     * Runs the task for the specified day. The log can be disabled for the day
     * @param day
     * @param disableDayLog
     */
    private static void runDay(RootDay day, boolean disableDayLog) {
        if (disableDayLog) {
            Log.disable();
        }
        long timeStart= System.nanoTime();
        day.run();
        long timeEnd = System.nanoTime();
        if (disableDayLog) {
            Log.enable();
            Log.show("  Year " + day.getYear() + " Day " + day.getDay() + ": " + ((timeEnd - timeStart)/1000000) + " ms");
        }
    }
}
