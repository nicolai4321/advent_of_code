package advent_of_code.years.year2023.day02;

import java.util.HashMap;

import advent_of_code.utils.Log;

public class Game {
    private int id;
    private HashMap<String, Integer> colors;
    
    public Game(int id, String[] rounds) {
        this.id = id;
        colors = new HashMap<String, Integer>();
        
        for (String round : rounds) {
            String color = round.split(" ")[1];
            int value = Integer.parseInt(round.split(" ")[0]);
            int currentValue = (colors.get(color) == null) ? 0 : colors.get(color);
            colors.put(color, Math.max(value, currentValue));
        }
    }

    public int getRed() {
        return colors.get("red");
    }

    public int getBlue() {
        return colors.get("blue");
    }

    public int getGreen() {
        return colors.get("green");
    }

    public int getId() {
        return id;
    }
    
    public String toString() {
        return id + ":[" + colors + "]";
    }
    
    public void print() {
        Log.show(toString());
    }
}
