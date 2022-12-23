package advent_of_code.year2022.day22;

import java.util.ArrayList;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day22 extends RootDay {    
    public Day22() {
        super(true, true, "117102", true, true, "135297");
    }

    /**
     * Simulate the movement with normal wrapping and give the password. The password is: 1000 * finalRow + 4 * finalColumn + facing
     */
    @Override
    public String run1() {
        String[] input = input();
        ArrayList<Instruction> instructions = getInstructions(input);
        Grid<String> grid = generateGrid(input);
        Position position = getStartPosition(grid, Direction.RIGHT);
        executeInstructions(grid, position, instructions, null);        
        return getPassword(position) + "";
    }
    
    /**
     * Simulate the movement with cube wrapping and give the password. The password is: 1000 * finalRow + 4 * finalColumn + facing
     */
    @Override
    public String run2() {
        String[] input = input();
        ArrayList<Instruction> instructions = getInstructions(input);
        Grid<String> grid = generateGrid(input);
        Position position = getStartPosition(grid, Direction.RIGHT);
        executeInstructions(grid, position, instructions, generateCubes());
        return getPassword(position) + "";
    }
    
    /**
     * Execute instructions on the grid
     * @param grid
     * @param pos
     * @param instructions
     * @param cubes
     */
    private void executeInstructions(Grid<String> grid, Position pos, ArrayList<Instruction> instructions, ArrayList<Cube> cubes) {
        for (Instruction instruction : instructions) {
            Integer rotate = instruction.getRotate();
            Integer amount = instruction.getAmount();
            
            if (rotate != null) {
                int facing = pos.getFacing();
                if (rotate == Direction.LEFT) {
                    facing = Math.floorMod(facing - 1, 4);
                } else if (rotate == Direction.RIGHT) {
                    facing = Math.floorMod(facing + 1, 4);
                }
                pos.setFacing(facing);
            } else if (amount != null) {
                pos.resetNewFacing();
                for (int i=0; i<amount; i++) {
                    int posX = pos.getX();
                    int posY = pos.getY();

                    if (pos.getFacing() == Direction.RIGHT) {
                        move(grid, pos, posX + 1, posY, cubes);
                    } else if (pos.getFacing() == Direction.LEFT) {
                        move(grid, pos, posX - 1, posY, cubes);
                    } else if (pos.getFacing() == Direction.UP) {
                        move(grid, pos, posX, posY - 1, cubes);
                    } else if (pos.getFacing() == Direction.DOWN) {
                        move(grid, pos, posX, posY + 1, cubes);
                    }
                }
            }
        }
    }
 
    /**
     * Move from position to (newX, newY) if there are no obstacles.
     * 
     * The normal wrapping is used if the list of cubes is null.
     * Otherwise the cube wrapping is used
     * 
     * @param grid
     * @param pos
     * @param newX
     * @param newY
     * @param cubes
     */
    private void move(Grid<String> grid, Position pos, int newX, int newY, ArrayList<Cube> cubes) {
        boolean outOfBounce = newX < 0 || grid.getWidth() <= newX || newY < 0 || grid.getHeight() <= newY;           
        String value = (outOfBounce) ? " " : grid.get(newX, newY);        
        if (outOfBounce || value.equals(" ")) {
            int[] wrapPos;
            if (cubes == null) {
                wrapPos = wrap(grid, pos, newX, newY);
            } else {
                wrapPos = cubeWrap(grid, pos, newX, newY, cubes);                
            }
            newX = wrapPos[0];
            newY = wrapPos[1];
            value = grid.get(newX, newY);
        }
        
        if (value.equals("#")) {
            return;
        }
        
        if (value.equals(".")) {
            pos.setX(newX);
            pos.setY(newY);
            pos.applyNewFacing();
        }
    }
    
    /**
     * WARNING: this method is hardcoded for the input
     * Finds and returns the wrap coordinates
     * @param grid
     * @param pos
     * @param newX
     * @param newY
     * @param cubes
     * @return { wrapX, wrapY }
     */
    //TODO generalize this method to handle arbitrary input
    private int[] cubeWrap(Grid<String> grid, Position pos, int newX, int newY, ArrayList<Cube> cubes) {
        int x = pos.getX();
        int y = pos.getY();

        Cube cubeNow = getCube(x, y, cubes);
        int facing = pos.getFacing();
        int cubeSize = 50;

        int xRel = (x % cubeSize);
        int yRel = (y % cubeSize);
        int xRelInv = (cubeSize-1) - xRel;
        int yRelInv = (cubeSize-1) - yRel;
        int wrapX = x;
        int wrapY = y;

        //Wrapping for cube A
        if (cubeNow.getName().equals("A")) {
            if (facing == Direction.UP) {
                wrapX = 0;
                wrapY = 150 + xRel;
                pos.proposeFacing(Direction.RIGHT);
            } else if (facing == Direction.LEFT) {
                wrapX = 0;
                wrapY = 100 + yRelInv;
                pos.proposeFacing(Direction.RIGHT);
            }
        //Wrapping for cube B
        } else if (cubeNow.getName().equals("B")) {
            if (facing == Direction.DOWN) {
                wrapX = 99;
                wrapY = 50 + xRel;
                pos.proposeFacing(Direction.LEFT);                
            } else if (facing == Direction.UP) {
                wrapX = 0 + xRel;
                wrapY = 199;
                pos.proposeFacing(Direction.UP);
            } else if (facing == Direction.RIGHT) {
                wrapX = 99;
                wrapY = 100 + yRelInv;
                pos.proposeFacing(Direction.LEFT);
            }
        //Wrapping for cube C
        } else if (cubeNow.getName().equals("C")) {
            if (facing == Direction.LEFT) {
                wrapX = 0 + yRel;
                wrapY = 100;
                pos.proposeFacing(Direction.DOWN);                
            } else if (facing == Direction.RIGHT) {
                wrapX = 100 + yRel;
                wrapY = 49;
                pos.proposeFacing(Direction.UP);
            }
        //Wrapping for cube D
        } else if (cubeNow.getName().equals("D")) {
            if (facing == Direction.DOWN) {
                wrapX = 49;
                wrapY = 150 + xRel;
                pos.proposeFacing(Direction.LEFT);
            } else if (facing == Direction.RIGHT) {
                wrapX = 149;
                wrapY = 0 + yRelInv;
                pos.proposeFacing(Direction.LEFT);
            }
        //Wrapping for cube E
        } else if (cubeNow.getName().equals("E")) {
            if (facing == Direction.UP) {
                wrapX = 50;
                wrapY = 50 + xRel;
                pos.proposeFacing(Direction.RIGHT);
            } else if (facing == Direction.LEFT) {
                wrapX = 50;
                wrapY = 0 + yRelInv;
                pos.proposeFacing(Direction.RIGHT);
            }
        //Wrapping for cube F
        } else if (cubeNow.getName().equals("F")) {
            if (facing == Direction.DOWN) {
                wrapX = 100 + xRel;
                wrapY = 0;
                pos.proposeFacing(Direction.DOWN);
            } else if (facing == Direction.LEFT) {
                wrapX = 50 + yRel;
                wrapY = 0;
                pos.proposeFacing(Direction.DOWN);
            } else if (facing == Direction.RIGHT) {
                wrapX = 50 + yRel;
                wrapY = 149;
                pos.proposeFacing(Direction.UP);
            }
        }
        
        return new int[] { wrapX, wrapY };        
    }
    
    /**
     * Finds and returns the wrap coordinates
     * @param grid
     * @param pos
     * @param newX
     * @param newY
     * @return { wrapX, wrapY }
     */
    private int[] wrap(Grid<String> grid, Position pos, int newX, int newY) {
        boolean vertical = pos.getX() == newX;
        String wrapValue = null;
        int wrapX = pos.getX();
        int wrapY = pos.getY();
        
        if (vertical) {
            if (pos.getY() < newY) {
                while(true) {
                    wrapY = Math.floorMod(wrapY + 1, grid.getHeight());
                    wrapValue = grid.get(wrapX, wrapY);
                    if (!(wrapValue.equals(" "))) {
                        break;
                    }
                }
            } else {
                while(true) {
                    wrapY = Math.floorMod(wrapY - 1, grid.getHeight());
                    wrapValue = grid.get(wrapX, wrapY);
                    if (!(wrapValue.equals(" "))) {
                        break;
                    }
                }
            }
        } else {
            if (pos.getX() < newX) {
                while(true) {
                    wrapX = Math.floorMod(wrapX + 1, grid.getWidth());
                    wrapValue = grid.get(wrapX, wrapY);
                    if (!(wrapValue.equals(" "))) {
                        break;
                    }
                }
            } else {
                while(true) {
                    wrapX = Math.floorMod(wrapX - 1, grid.getWidth());
                    wrapValue = grid.get(wrapX, wrapY);
                    if (!(wrapValue.equals(" "))) {
                        break;
                    }
                }
            }
        }
        return new int[] { wrapX, wrapY };
    }
    
    /**
     * @param position
     * @return password
     */
    private int getPassword(Position position) {
        return (1000 * (position.getY() + 1)) + (4 * (position.getX() + 1)) + position.getFacing();
    }
    
    /**
     * @param grid
     * @return the start position
     */
    private Position getStartPosition(Grid<String> grid, int facing) {
        int y = 0;
        for (int x=0; x<grid.getWidth(); x++) {
            String s = grid.get(x, y);
            if (s.equals(".")) {
                return new Position(x, y, facing);
            }
        }
        return null;
    }

    /**
     * WARNING: this method is hardcoded and can only generate cubes that matches the input
     * @return cubes
     */
    //TODO generalize this method to handle arbitrary input
    private ArrayList<Cube> generateCubes() {
        ArrayList<Cube> cubes = new ArrayList<Cube>();
        cubes.add(new Cube("A", 50, 0, 99, 49));
        cubes.add(new Cube("B", 100, 0, 149, 49));
        cubes.add(new Cube("C", 50, 50, 99, 99));
        cubes.add(new Cube("D", 50, 100, 99, 149));
        cubes.add(new Cube("E", 0, 100, 49, 149));
        cubes.add(new Cube("F", 0, 150, 49, 199));
        return cubes;
    }
    
    /**
     * @param x
     * @param y
     * @param cubes
     * @return cube that contains (x,y)
     */
    private Cube getCube(int x, int y, ArrayList<Cube> cubes) {
        for (Cube c : cubes) {
            if (c.getX0() <= x && x <= c.getX1() && c.getY0() <= y && y <= c.getY1()) {
                return c;
            }            
        }
        return null;
    }
    
    /**
     * @param input
     * @return grid
     */
    private Grid<String> generateGrid(String[] input) {
        int height = input.length - 2;
        int width = 0;
        
        for (String row : input) {
            width = Math.max(width, row.length());
        }
        
        Grid<String> grid = new Grid<String>(width, height, false);
        grid.setDivider("");
        grid.set(" ");
        
        for (int y=0; y<height; y++) {
            char[] chars = input[y].toCharArray();
            for (int x=0; x<width; x++) {
                if (x < chars.length) {
                    char c = chars[x];
                    grid.set(x, y, c+"");                    
                }
            }
        }        
        return grid;
    }
    
    /**
     * @param input
     * @return instructions
     */
    private ArrayList<Instruction> getInstructions(String[] input) {
        String lastLine = input[input.length - 1];
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();    
        Integer amount = null;
        
        for (char c : lastLine.toCharArray()) {
            //amount
            if ((c + "").matches("[0-9]")) {
                amount = (amount == null) ? Integer.parseInt(c+"") : (amount * 10) + Integer.parseInt(c+"");
            //rotate
            } else {
                if (amount != null) {
                    instructions.add(new Instruction(amount, null));                    
                    amount = null;
                }
                
                if (c == 'R') { instructions.add(new Instruction(null, Direction.RIGHT)); }
                if (c == 'L') { instructions.add(new Instruction(null, Direction.LEFT)); }
            }
        }
        
        if (amount != null) {
            instructions.add(new Instruction(amount, null));                    
        }
        
        return instructions;
    }
    
    private static String[] example() {
        return Read.getStrings(2022, 22, "example01.txt"); 
    }
    
    private static String[] example2() {
        return Read.getStrings(2022, 22, "example02.txt"); 
    }
    
    private static String[] input() {
        return Read.getStrings(2022, 22, "input01.txt"); 
    }
}
