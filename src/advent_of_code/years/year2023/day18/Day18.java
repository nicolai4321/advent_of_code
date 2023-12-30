package advent_of_code.years.year2023.day18;

import java.math.BigInteger;
import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.math.big.BigInt;


public class Day18 extends RootDay {
    public char UP = 'U';
    public char DOWN = 'D';
    public char LEFT = 'L';
    public char RIGHT = 'R';
    
    public Day18(Year year, int day) {
        super(year, day, "70253", "131265059885080");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        ArrayList<Instruction> instructions = getColorInstructions(input);
        ArrayList<BigInt[]> coordinates = getCoordinates(instructions);
        
        BigInt area = BigInt.getAreaFromCoordinates(coordinates);
        BigInt boundaryPoints = getBoundaryPoints(instructions);
        BigInt interioirPoints = BigInt.getInterioirPoints(area, boundaryPoints);

        return interioirPoints.add(boundaryPoints) + "";
    }

    @Override
    public String run2(String input) {
        ArrayList<Instruction> instructions = getHexaInstructions(input);
        ArrayList<BigInt[]> coordinates = getCoordinates(instructions);
        
        BigInt area = BigInt.getAreaFromCoordinates(coordinates);
        BigInt boundaryPoints = getBoundaryPoints(instructions);
        BigInt interioirPoints = BigInt.getInterioirPoints(area, boundaryPoints);

        return interioirPoints.add(boundaryPoints) + "";
    }
    
    /**
     * @param instructions
     * @return boundaryPoints
     */
    private BigInt getBoundaryPoints(ArrayList<Instruction> instructions) {
        BigInt boundaryPoints = BigInt.ZERO;
        
        for (Instruction instruction : instructions) {
            boundaryPoints = boundaryPoints.add(instruction.getAmount());
        }
        
        return boundaryPoints;
    }

    /**
     * @param instructions
     * @return coordinates
     */
    private ArrayList<BigInt[]> getCoordinates(ArrayList<Instruction> instructions) {
        BigInt[] previousCoordinate = new BigInt[] { BigInt.ZERO, BigInt.ZERO };
        
        ArrayList<BigInt[]> coordinates = new ArrayList<BigInt[]>();
        coordinates.add(previousCoordinate);
        
        for (Instruction instruction : instructions) {
            char direction = instruction.getDirection();
            
            BigInt xChange = (direction == LEFT) ? instruction.getAmount().mult(-1) : ((direction == RIGHT) ? instruction.getAmount() : new BigInt(0));
            BigInt yChange = (direction == UP) ? instruction.getAmount().mult(-1) : ((direction == DOWN) ? instruction.getAmount() : new BigInt(0));
            
            BigInt[] newCoordinate = { previousCoordinate[0].add(xChange), previousCoordinate[1].add(yChange) };
            coordinates.add(newCoordinate);
            
            previousCoordinate = newCoordinate;
        }
        
        return coordinates;
    }
    
    /**
     * @param input
     * @return instructions
     */
    private ArrayList<Instruction> getColorInstructions(String input) {
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        String[] lines = input.split("\n");
        
        for (String line : lines) {
            String[] strings = line.split(" ");
            char direction = strings[0].charAt(0);
            BigInt amount = new BigInt(strings[1]);
            
            instructions.add(new Instruction(direction, amount));
        }
        
        return instructions;
    }

    /**
     * @param input
     * @return instructions
     */
    private ArrayList<Instruction> getHexaInstructions(String input) {
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        String[] lines = input.split("\n");
        
        for (String line : lines) {
            String[] strings = line.split(" ");
            String hexadecimals = strings[2].substring(1, strings[2].length()-1).replaceAll("#", "");
            BigInt amount = new BigInt(new BigInteger(hexadecimals.substring(0, 5), 16));
            String direction = hexadecimals.substring(5, 6);
            
            switch(direction) {
                case "0":
                    instructions.add(new Instruction(RIGHT, amount));
                    break;
                case "1":
                    instructions.add(new Instruction(DOWN, amount));
                    break;
                case "2":
                    instructions.add(new Instruction(LEFT, amount));
                    break;
                case "3":
                    instructions.add(new Instruction(UP, amount));
                    break;
                default:
                    throw new RuntimeException("Invalid direction");
            }
        }
        
        return instructions;
    }
}
