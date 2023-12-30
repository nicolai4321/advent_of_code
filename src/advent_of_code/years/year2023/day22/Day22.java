package advent_of_code.years.year2023.day22;

import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.LightGrid;
import advent_of_code.utils.Lists;
import advent_of_code.utils.math.big.BigInt;


public class Day22 extends RootDay {
    public Day22(Year year, int day) {
        super(year, day, "499", "95059");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    /**
     * How many bricks can be removed without any change
     */
    @Override
    public String run1(String input) {
        ArrayList<Brick> bricks = getBricks(input);
        Collections.sort(bricks);
        fall(bricks);
        return sumStableBricks(bricks) + "";
    }
    
    /**
     * How many bricks will fall if you try to remove every brick
     */
    @Override
    public String run2(String input) {
        ArrayList<Brick> bricks = getBricks(input);
        Collections.sort(bricks);
        fall(bricks);
        return sumBricksFall(bricks).toString();
    }

    /**
     * @param bricks
     * @return sum of stable bricks
     */
    private int sumStableBricks(ArrayList<Brick> bricks) {
        int sum = 0;
        
        for (Brick brick : bricks) {
            if (canRemove(bricks, brick)) {
                sum++;
            }
        }
        return sum;
    }
    
    /**
     * @param bricks
     * @return sum of bricks to fall when all bricks gets removed
     */
    private BigInt sumBricksFall(ArrayList<Brick> bricks) {
        BigInt sum = BigInt.ZERO;
        
        for (int i=0; i<bricks.size(); i++) {
            sum = sum.add(sumBricksFall(bricks, i));
        }
        return sum;
    }
    
    /**
     * @param bricks
     * @param brick
     * @param i
     * @return sum of bricks that will fall when brick gets removed
     */
    private BigInt sumBricksFall(ArrayList<Brick> bricks, int index) {
        ArrayList<Brick> bricksMissing = copy(bricks);
        Collections.sort(bricksMissing);
        bricksMissing.remove(index);
        
        BigInt sum = BigInt.ZERO;
        for (Brick b : bricksMissing) {
            if (b.fall(bricksMissing)) {
                sum = sum.add(BigInt.ONE);
            }
        }
        
        return sum;
    }

    /**
     * @param bricks
     * @param brick
     * @return true if can remove brick without any change
     */
    private boolean canRemove(ArrayList<Brick> bricks, Brick brick) {
        ArrayList<Brick> bricksMissing = copy(bricks);
        Collections.sort(bricksMissing);
        bricksMissing.remove(brick.getId()-1);
        
        for (Brick b : bricksMissing) {
            if (b.fall(bricksMissing)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * @param bricks
     * @return copy of list and copy of bricks
     */
    private ArrayList<Brick> copy(ArrayList<Brick> bricks) {
        ArrayList<Brick> bricksCopy = new ArrayList<>();
        for (Brick brick : bricks) {
            bricksCopy.add(brick.copy());
        }
        
        return bricksCopy;
    }
    
    /**
     * Let bricks fall
     * @param bricks
     */
    private void fall(ArrayList<Brick> bricks) {
        for (Brick brick : bricks) {
            brick.fall(bricks);
        }
    }

    /**
     * @param input
     * @return a list of bricks
     */
    private ArrayList<Brick> getBricks(String input) {
        ArrayList<Brick> bricks = new ArrayList<Brick>();
        
        for (String line : input.split("\n")) {
            String coord0 = line.split("~")[0];
            String coord1 = line.split("~")[1];
            
            bricks.add(new Brick( Lists.stringsToInts(coord0.split(",")) , Lists.stringsToInts(coord1.split(",")) ));
        }
        
        return bricks;
    }
    
    /**
     * Can print side view of the bricks
     * @param bricks
     */
    private void printSideX(ArrayList<Brick> bricks) {
        LightGrid<String> grid = new LightGrid<String>();
        grid.setDefaultValue(".");
        
        int highestZ = 0;
        for (Brick brick : bricks) {
            highestZ = Math.max(highestZ, Math.max(brick.getCoord0()[2], brick.getCoord1()[2]));
        }
        
        for (Brick brick : bricks) {
            int[] c0 = new int[] { brick.getCoord0()[0], brick.getCoord0()[2] }; 
            int[] c1 = new int[] { brick.getCoord1()[0], brick.getCoord1()[2] }; 
            
            for (int x=Math.min(c0[0], c1[0]); x<=Math.max(c0[0], c1[0]); x++) {
                for (int z=Math.min(c0[1], c1[1]); z<=Math.max(c0[1], c1[1]); z++) {
                    int reverseZ = highestZ - z;
                    String value = grid.get(x, reverseZ);
                    if (value.equals(".")) {
                        grid.set(x, reverseZ, brick.getId() + "");
                    } else {
                        grid.set(x, reverseZ, "?");
                    }
                }
            }
        }
        
        grid.print();
    }
}
