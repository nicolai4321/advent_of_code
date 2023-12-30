package advent_of_code.years.year2022.day22;

public class Instruction {    
    private Integer amount;
    private Integer rotate;
    
    public Instruction(Integer amount, Integer rotate) {
        this.amount = amount;
        this.rotate = rotate;
    }
    
    public static String mapFacing(int rotate) {
        if (rotate == Direction.LEFT) { return "Left"; }
        if (rotate == Direction.RIGHT) { return "Right"; }
        return null;
    }
    
    public String toString() {
        if (amount == null) {
            return "{" + mapFacing(rotate) + "}";            
        } else if (rotate == null) {
            return "{" + amount + "}";
        }
        return "{" + amount + " " + mapFacing(rotate) + "}";
    }
    
    public Integer getRotate() {
        return rotate;
    }
    
    public Integer getAmount() {
        return amount;
    }
}
