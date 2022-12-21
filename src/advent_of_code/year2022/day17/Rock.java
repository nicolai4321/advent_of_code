package advent_of_code.year2022.day17;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Log;

/**
 * Rock class
 *    -every rock is a grid of 4x4
 *    -the type defines which cells are solid
 *    -the position of a rock is the lower left corner
 */
public class Rock {
    public static int NR_TYPES = 5;
    public static int TYPE_LINE_H = 0;
    public static int TYPE_BALL = 1;
    public static int TYPE_L = 2;
    public static int TYPE_LINE_V = 3;
    public static int TYPE_SQUARE = 4;
    
    private Grid<String> grid;
    private int width;
    private int posX;
    private int posY;
    private int type;
    
    /**
     * Create the rock and set properties depending on the rock type
     * @param posX
     * @param posY
     * @param type
     */
    public Rock(int posX, int posY, int type) {
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        
        grid = new Grid<String>(4, 4, true);
        grid.set(".");
        grid.setDivider("");
        
        if (type == TYPE_LINE_H) {
            grid.set(0, 0, "¤");
            grid.set(1, 0, "¤");
            grid.set(2, 0, "¤");
            grid.set(3, 0, "¤");
            width = 4;
        } else if (type == TYPE_BALL) {
            grid.set(0, 1, "¤");
            grid.set(1, 1, "¤");
            grid.set(2, 1, "¤");
            grid.set(1, 0, "¤");
            grid.set(1, 2, "¤");
            width = 3;
        } else if (type == TYPE_L) {
            grid.set(0, 0, "¤");
            grid.set(1, 0, "¤");
            grid.set(2, 0, "¤");
            grid.set(2, 1, "¤");
            grid.set(2, 2, "¤");
            width = 3;
        } else if (type == TYPE_LINE_V) {
            grid.set(0, 0, "¤");
            grid.set(0, 1, "¤");
            grid.set(0, 2, "¤");
            grid.set(0, 3, "¤");    
            width = 1;
        } else if (type == TYPE_SQUARE) {
            grid.set(0, 0, "¤");
            grid.set(0, 1, "¤");
            grid.set(1, 0, "¤");
            grid.set(1, 1, "¤");
            width = 2;
        } else {
            throw new RuntimeException("Invalid type");
        }
    }
    
    /**
     * @param rock
     * @return true if this rock collides with the other rock given as a parameter
     */
    public boolean collide(Rock rock) {
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (isSolid(i, j)) {
                    for (int k=0; k<4; k++) {
                        for (int l=0; l<4; l++) {
                            if (rock.isSolid(k, l)) {
                                int x = posX + i;
                                int y = posY + j;
                                int rockX = rock.getPosX() + k;
                                int rockY = rock.getPosY() + l;
                                if (x == rockX && y == rockY) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * @return the highest point on the rock
     */
    public int highestPoint() {
        int highestPoint = posY;
        for (int x=0; x<4; x++) {
            for (int y=0; y<4; y++) {
                if (isSolid(x, y)) {
                    highestPoint = Math.max(highestPoint, posY + y);
                }
            }
        }
        return highestPoint + 1;
    }
    
    /**
     * @param indexX
     * @param indexY
     * @return true if the index in the rock is solid
     */
    public boolean isSolid(int indexX, int indexY) {
        return grid.get(indexX, indexY).equals("¤");
    }
    
    /**
     * Decrease x with minimum of minX
     * @param minX
     */
    public void decreasePosX(int minX) {
        posX = Math.max(minX, posX - 1);
    }
    
    /**
     * Increase x while the width of the rock does not cross maxX
     * @param maxX
     */
    public void increasePosX(int maxX) {
        posX = Math.min(maxX - (width - 1), posX + 1);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
    public static Rock generateRock(int posX, int posY, int round) {
        return new Rock(posX, posY, round % 5);
    }
    
    public String toString() {
        return grid.toString();
    }

    public void printType() {
        Log.show(toString());
    }

    public void print() {
        Log.show(type + ": (" + posX + "," + posY + ")");
    }

    public void decreasePosY() {
        posY--;
    }
    
    public void increasePosY() {
        posY++;
    }

    public boolean collideBottom() {
        return posY < 0;
    }
}
