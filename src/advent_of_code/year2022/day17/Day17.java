package advent_of_code.year2022.day17;

import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day17 extends RootDay {
    public Day17() {
        super(true, true, "3133", true, true, "1547953216393");
    }

    @Override
    public String run1() {
        String input = input();
        Board board = new Board(input.toCharArray());
        board.start(2022, 1);
        
        return board.getHighestPoint();
    }

    @Override
    public String run2() {
        String input = input();
        Board board = new Board(input.toCharArray());
        board.start(100000000, 10000);
        
        return board.getHighestPoint();
    }
    
    
    private static String example() {
        return (Read.getString(2022, 17, "example01.txt")); 
    }
    
    private static String input() {
        return (Read.getString(2022, 17, "input01.txt")); 
    }
}
