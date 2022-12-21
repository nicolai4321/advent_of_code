package advent_of_code.year2022.day09;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day09 extends RootDay {
    public Day09() {
        super(true, true, "5710", true, true, "2259");
    }

    @Override
    public String run1() {
        String[] input = input();
        String[][] commands = getCommands(input);
        Grid<Boolean> visitingGrid = simulateGrid(commands, 2, 500, null);
        return countUniqueVisits(visitingGrid) + "";
    }

    @Override
    public String run2() {
        String[] input = input();
        String[][] commands = getCommands(input);
        Grid<Boolean> visitingGrid = simulateGrid(commands, 10, 500, null);        
        return countUniqueVisits(visitingGrid) + "";
    }
    
    /**
     * Simulate the commands on the grid
     * @param commands
     * @param nrKnots
     * @param gridSize
     * @param maxCommands
     * @return grid that is visited by the tail
     */
    private Grid<Boolean> simulateGrid(String[][] commands, int nrKnots, int gridSize, Integer maxCommands) {
        int startX = gridSize/2;
        int startY = gridSize/2;

        Pair[] knots = new Pair[nrKnots];
        for (int i=0; i<nrKnots; i++) {
            knots[i] = new Pair(startX, startY);
        }

        Grid<Boolean> visitingGrid = new Grid<Boolean>(gridSize, gridSize, true);
        visitingGrid.set(false);
        visitingGrid.setBoolInterpretation("#", ".");
        visitingGrid.setDivider("");
        visitingGrid.set(startX, startY, true);
        
        for (int i=0; i<commands.length && (maxCommands==null || i<maxCommands); i++) {
            String[] command = commands[i];
            executeCommand(command, knots, visitingGrid);
            if (maxCommands != null) {
                print(knots, visitingGrid, startX, startY);
            }
        }
        
        if (maxCommands != null) {
            visitingGrid.print();
        }
        
        return visitingGrid;
    }
    
    /**
     * Execute command on the grid
     * @param command
     * @param ropes
     * @param visitingGrid
     */
    private void executeCommand(String[] command, Pair[] knots, Grid<Boolean> visitingGrid) {
        String direction = command[0];
        int nrMoves = Integer.parseInt(command[1]);
        
        for (int move=0; move<nrMoves; move++) {
            for (int i=0; i<knots.length; i++) {
                if (i == 0) {
                    Pair head = knots[i];
                    moveHead(head, direction);
                } else {
                    Pair head = knots[i - 1];
                    Pair tail = knots[i];
                    moveTail(head, tail);
                }
                
                if (i == knots.length - 1) {
                    Pair tail = knots[i];
                    visitingGrid.set(tail.getX(), tail.getY(), true);
                }
            }
        }
    }
    
    /**
     * @param visitingGrid
     * @return sum of all the cells that have been visited
     */
    private int countUniqueVisits(Grid<Boolean> visitingGrid) {
        int sum = 0;
        for (int x=0; x<visitingGrid.getWidth(); x++) {
            for (int y=0; y<visitingGrid.getHeight(); y++) {
                Boolean b = visitingGrid.get(x, y);
                if (b != null && b) {
                    sum++;
                }
            }
        }
        return sum;
    }
    
    /**
     * Move head
     * @param command
     * @param head
     */
    private void moveHead(Pair head, String direction) {
        if (direction.equals("R")) {
            head.addX(1);
        } else if (direction.equals("L")) {
            head.addX(-1);
        } else if (direction.equals("U")) {
            head.addY(1);
        } else if (direction.equals("D")) {
            head.addY(-1);
        }
    }
    
    /**
     * Move tail towards head
     * @param command
     * @param ropeH
     * @param ropeT
     */
    private void moveTail(Pair head, Pair tail) {        
        int distanceX = Math.max(head.getX(), tail.getX()) - Math.min(head.getX(), tail.getX());
        int distanceY = Math.max(head.getY(), tail.getY()) - Math.min(head.getY(), tail.getY());
        boolean touching = distanceX <= 1 && distanceY <= 1;
        boolean sameX = head.getX() == tail.getX();
        boolean sameY = head.getY() == tail.getY();
        boolean diagonal = !sameX && !sameY && !touching;
        
        if (diagonal) {
            if (tail.getX() < head.getX()) {
                tail.addX(1);
            } else {
                tail.addX(-1);                
            }
            
            if (tail.getY() < head.getY()) {
                tail.addY(1);
            } else {
                tail.addY(-1);                
            }
        } else if (1 < distanceX) {
            if (tail.getX() < head.getX()) {
                tail.addX(1);
            } else {
                tail.addX(-1);                
            }
        } else if (1 < distanceY) {
            if (tail.getY() < head.getY()) {
                tail.addY(1);
            } else {
                tail.addY(-1);                
            }
        }
    }

    /**
     * @param input
     * @return pairs of commands and amounts
     */
    public String[][] getCommands(String[] inputs) {
        String[][] commands = new String[inputs.length][2];
        for (int i=0; i<inputs.length; i++) {
            String input = inputs[i];
            String[] command = input.split(" ");
            commands[i][0] = command[0];
            commands[i][1] = command[1];
        }
        return commands;
    }
    
    /**
     * For debugging, can print the current position of all knots
     * @param ropes
     * @param grid
     * @param start
     */
    private void print(Pair[] knots, Grid visitingGrid, Integer startX, Integer startY) {
        Grid<String> grid = new Grid<String>(visitingGrid.getWidth(), visitingGrid.getHeight(), true);
        grid.set(".");
        grid.set(startX, startY, "s");
        grid.setDivider("");

        for (int i=knots.length-1; 0<=i; i--) {
            Pair knot = knots[i];
            grid.set(knot.getX(), knot.getY(), i + "");
        }
        grid.print();
    }
    
    private static String[] example() {
        return Read.getStrings(2022, 9, "example01.txt"); 
    }
    
    private static String[] example2() {
        return Read.getStrings(2022, 9, "example02.txt"); 
    }
    
    private static String[] input() {
        return Read.getStrings(2022, 9, "input01.txt"); 
    }
}
