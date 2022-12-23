package advent_of_code.year2022.day17;

import advent_of_code.utils.RootDay;

public class Day17 extends RootDay {
    public Day17() {
        super(2022, 17, "3133", "1547953216393");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        Board board = new Board(input.toCharArray());
        board.start(2022, 1);
        
        return board.getHighestPoint();
    }

    @Override
    public String run2(String input) {
        Board board = new Board(input.toCharArray());
        board.start(100000000, 10000);
        
        return board.getHighestPoint();
    }
}
