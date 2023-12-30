package advent_of_code.years.year2022.day16;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.utils.Log;

public class Valve {
    private String name;
    private int rate;
    String[] neighbourStrings;
    ArrayList<Valve> tunnels;
    HashMap<String, Integer> costMap;
    
    public Valve(String name, int rate, String[] neighbourStrings) {
        this.name = name;
        this.rate = rate;
        this.neighbourStrings = neighbourStrings;
        
        tunnels = new ArrayList<Valve>();
        costMap = new HashMap<String, Integer>();
    }

    public String[] getNeighbourStrings() {
        return neighbourStrings;
    }

    public boolean is(Valve v) {
        return name.equals(v.getName());
    }
    
    public String getName() {
        return name;
    }

    public void addTunnel(Valve v) {
        tunnels.add(v);
    }
    
    public void print() {
        Log.show(toString());
    }
    
    public String toString() {
        String costs = "";
        for (String key : costMap.keySet()) {
            costs += "(" + key + ":" + costMap.get(key) + ")";
        }
        
        return name + ":" + rate + "|" + costs;
    }

    public ArrayList<Valve> getTunnels() {
        return tunnels;
    }

    public int getRate() {
        return rate;
    }

    public void setCost(Valve v, Integer cost) {
        costMap.put(v.getName(), cost);
    }

    public Integer getCost(Valve remainingValve) {
        return costMap.get(remainingValve.getName());
    }
}
