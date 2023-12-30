package advent_of_code.years.year1000.day01;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.Log;
import advent_of_code.utils.pathing.PathFinder;

public class Day01 extends RootDay {
    public Day01(Year year, int day) {
        super(year, day, null, null);
        setInput1("input03.txt");
        setInput2("input01.txt");
        this.disableRun2();
    }

    @Override
    public String run1(String input) {
        Grid<String> grid = new Grid<String>(input, true);
        grid.setDivider("");

        //Integer l1 = PathFinder.minSimple(grid, 'S', 'E');
        //Log.show(l1);
        
        Integer l2 = PathFinder.trackPath(grid, 'S', 'E');
        Log.show("track length: " + l2);
        grid.print();
        
        //PathFinder.trackPath(grid, 'S', 'E');
        //grid.print();
        
        return "";
    }

    @Override
    public String run2(String input) {
        return null;
    }
}
