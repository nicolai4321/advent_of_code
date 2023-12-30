package advent_of_code.years.year2022.day22;

public class Position {
    private int x;
    private int y;
    private int facing;
    private Integer newFacing = null;
    
    public Position(int x, int y, int facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    public int getFacing() {
        return facing;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void proposeFacing(int newFacing) {
        this.newFacing = newFacing; 
    }

    public void applyNewFacing() {
        if (newFacing != null) {
            facing = newFacing; 
        }
    }

    public void resetNewFacing() {
        newFacing = null;
    }
}
