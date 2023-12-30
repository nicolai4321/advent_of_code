package advent_of_code.years.year2022.day18;

import advent_of_code.utils.Log;

public class Cube {
    public static int NO_SIDE = -1;
    public static int SIDE_X = 0;
    public static int SIDE_Y = 1;
    public static int SIDE_Z = 2;
    
    public static int SIDE_X_PLUS = 3;
    public static int SIDE_X_MINUS = 4;
    public static int SIDE_Y_PLUS = 5;
    public static int SIDE_Y_MINUS = 6;
    public static int SIDE_Z_PLUS = 7;
    public static int SIDE_Z_MINUS = 8;
    
    private int x;
    private int y;
    private int z;
    private int[] sidesCovered;

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        sidesCovered = new int[6];
        for (int i=0; i<6; i++) {
            sidesCovered[i] = 0;
        }
    }
    
    /**
     * @param cube
     * @return the side of this cube that collides with the cube given as a parameter
     */
    public int getCollideSide(Cube cube) {
        int diffX = diff(x, cube.getX());
        int diffY = diff(y, cube.getY());
        int diffZ = diff(z, cube.getZ());
        
        //SIDE X
        if (diffX == 1 && diffY == 0 && diffZ == 0) {
            if (cube.getX() < x) {
                return SIDE_X_MINUS;
            } else {
                return SIDE_X_PLUS;
            }
        }
        
        //SIDE Y
        if (diffX == 0 && diffY == 1 && diffZ == 0) {
            if (cube.getY() < y) {
                return SIDE_Y_MINUS;
            } else {
                return SIDE_Y_PLUS;
            }
        }
        
        //SIDE Z
        if (diffX == 0 && diffY == 0 && diffZ == 1) {
            if (cube.getZ() < z) {
                return SIDE_Z_MINUS;
            } else {
                return SIDE_Z_PLUS;
            }
        }
        
        return NO_SIDE;
    }
    
    /**
     * The cube will store the side that is given as a parameter and set it as covered
     * @param side
     */
    public void coverSide(int side) {
        if (side == SIDE_X_PLUS) {
            sidesCovered[0] = 1;            
        } else if (side == SIDE_X_MINUS) {
            sidesCovered[1] = 1;            
        } else if (side == SIDE_Y_PLUS) {
            sidesCovered[2] = 1;            
        } else if (side == SIDE_Y_MINUS) {
            sidesCovered[3] = 1;            
        } else if (side == SIDE_Z_PLUS) {
            sidesCovered[4] = 1;            
        } else if (side == SIDE_Z_MINUS) {
            sidesCovered[5] = 1;            
        } else {
            throw new RuntimeException("Error! Unknown side: " + side);
        }
    }

    /**
     * @param cube
     * @return true if this cube is colliding with the cube given as a parameter
     */
    public boolean isColliding(Cube cube) {
        int diff = diff(x, cube.getX()) + diff(y, cube.getY()) + diff(z, cube.getZ());
        return diff <= 1;
    }

    /**
     * @return the number of sides that are not covered
     */
    public int countFreeSides() {
        int sum = 0;
        for (int side : sidesCovered) {
            if (side == 0) {
                sum++;
            }
        }
        
        return sum;
    }

    /**
     * @param cube
     * @return true if this cube has same position as the other cube
     */
    public boolean samePosition(Cube cube) {
        return x == cube.getX() && y == cube.getY() && z == cube.getZ();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
    
    public String toString() {
        return "{(" + x + "," + y + "," + z + ")(" + sidesCovered[0] + "," + sidesCovered[1] + "," + sidesCovered[2] + "," + sidesCovered[3] + "," + sidesCovered[4] + "," + sidesCovered[5] + ")}";
    }

    public void print() {
        Log.show(toString());
    }
    
    private int diff(int a, int b) {
        return Math.max(a, b) - Math.min(a, b);
    }
}
