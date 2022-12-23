package advent_of_code.year2022.day22;

public class Cube {
    private String name;
    private int x0;
    private int y0;
    private int x1;
    private int y1;
    
    public Cube(String name, int x0, int y0, int x1, int y1) {
        this.name = name;
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    public int getX0() {
        return x0;
    }
    
    public int getY0() {
        return y0;
    }
    
    public int getX1() {
        return x1;
    }
    
    public int getY1() {
        return y1;
    }
    
    public String getName() {
        return name;
    }
}
