package advent_of_code.years.year2023.day15;

public class Lens {
    private String label;
    private Integer strength = null;
    
    public Lens(String s) {
        boolean conEq = s.contains("=");
        label = (conEq) ? s.split("=")[0] : s.split("-")[0];
        if (conEq) {
            strength = Integer.parseInt(s.split("=")[1]);
        }
    }
    
    public String getLabel() {
        return label;
    }
    
    public Integer getStrength() {
        return strength;
    }
    
    public String toString() {
        if (strength == null) {
            return label;
        }
        
        return label + " " + strength;
    }
}
