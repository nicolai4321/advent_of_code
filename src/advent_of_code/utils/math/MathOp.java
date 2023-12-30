package advent_of_code.utils.math;

public class MathOp {
    /**
     * @param a
     * @param b
     * @return least common multiple of a and b
     */
    public static int lcm(int a, int b) {
        int lcm = Math.min(a, b);
        
        while(true) {
            if (Math.floorMod(lcm, a) == 0 && Math.floorMod(lcm, b) == 0) {
                break;
            }
            lcm += Math.min(a, b);
        }
        
        return lcm;
    }
    
    /**
     * @param x0
     * @param x1
     * @return manhatten distance
     */
    public static int difference(int a, int b) {
        return (Math.max(a, b) - Math.min(a, b));
    }
    
    /**
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @return manhatten distance
     */
    public static int manhattenDistance(int x0, int y0, int x1, int y1) {
        return (Math.max(x0, x1) - Math.min(x0, x1)) + (Math.max(y0, y1) - Math.min(y0, y1));
    }
}
