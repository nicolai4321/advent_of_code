package advent_of_code.years.year2023.day22;

import java.util.ArrayList;

import advent_of_code.utils.Interval;

public class Brick implements Comparable<Brick> {
    public final int id;
    private static int ID_COUNTER= 0;
    
    //x,y,z
    private int[] coord0;
    private int[] coord1;
    
    public Brick(int[] coord0, int[] coord1) {
        ID_COUNTER++;
        id = ID_COUNTER;
        this.coord0 = coord0;
        this.coord1 = coord1;
    }
    
    public Brick(int[] coord0, int[] coord1, int id) {
        this.id = id;
        this.coord0 = coord0;
        this.coord1 = coord1;
    }

    public String toString() {
        return "{(" + coord0[0] + "," + coord0[1] + "," + coord0[2] + "), " +
               "(" + coord1[0] + "," + coord1[1] + "," + coord1[2] + ")}";
    }

    /**
     * Allow brick to fall
     * @param bricks
     * @return true if there is a change in the position
     */
    public boolean fall(ArrayList<Brick> bricks) {
        boolean collision = false;
        boolean change = false;
        
        while (!collision) {
            int[] newCoord0 = new int[] { coord0[0], coord0[1], coord0[2]-1 };
            int[] newCoord1 = new int[] { coord1[0], coord1[1], coord1[2]-1 };
            collision = collideAnything(newCoord0, newCoord1, bricks);
            if (!collision) {
                coord0 = newCoord0;
                coord1 = newCoord1;
                change = true;
            }
        }
        
        return change;
    }

    /**
     * @param newCoord0
     * @param newCoord1
     * @param bricks
     * @return true if overlap with other bricks or the ground
     */
    private boolean collideAnything(int[] newCoord0, int[] newCoord1, ArrayList<Brick> bricks) {
        if (newCoord0[2] < 1 || newCoord1[2] < 1) {
            return true;
        }
        
        for (Brick brick : bricks) {
            if (brick.getId() != id && collide(brick, newCoord0, newCoord1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param brick
     * @param newCoord0
     * @param newCoord1
     * @return true if there is a collision
     */
    private boolean collide(Brick brick, int[] newCoord0, int[] newCoord1) {
        return Interval.intervalOverlap(new int[] { newCoord0[0], newCoord1[0] }, new int[] { brick.getCoord0()[0], brick.getCoord1()[0] }) &&
               Interval.intervalOverlap(new int[] { newCoord0[1], newCoord1[1] }, new int[] { brick.getCoord0()[1], brick.getCoord1()[1] }) &&
               Interval.intervalOverlap(new int[] { newCoord0[2], newCoord1[2] }, new int[] { brick.getCoord0()[2], brick.getCoord1()[2] });
    }
    
    public int getId() {
        return id;
    }

    public int[] getCoord0() {
        return coord0;
    }

    public int[] getCoord1() {
        return coord1;
    }

    @Override
    public int compareTo(Brick brick) {
        return Math.min(coord0[2], coord1[2]) - Math.min(brick.coord0[2], brick.coord1[2]);
    }

    public Brick copy() {
        return new Brick(coord0, coord1 , id);
    }
}
