package advent_of_code.years.year2022.day16;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day16 extends RootDay {
    public Day16(Year year, int day) {
        super(year, day, "2056", "2513");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * What is the most pressure you can release in 30 minutes?
     */
    @Override
    public String run1(String input) {
        ArrayList<Valve> valves = generateValves(input);
        int getPressure = getMaxPressure(getStartValve(valves), 30, getRateValves(valves));
        
        return getPressure + "";
    }

    /**
     * With you and an elephant working together for 26 minutes, what is the most pressure you could release?
     */
    @Override
    public String run2(String input) {
        ArrayList<Valve> valves = generateValves(input);
        ArrayList<Valve> rateValves = getRateValves(valves);
        return getMaxPressureShared(rateValves.get(0), getStartValve(valves), rateValves, new ArrayList<Valve>(), new ArrayList<Valve>()) + "";
    }
    
    /**
     * @param valve
     * @param startValve
     * @param valves
     * @param myValves
     * @param eValves
     * @return max pressure when the task can be shared between two individuals
     */
    private int getMaxPressureShared(Valve valve, Valve startValve, ArrayList<Valve> valves, ArrayList<Valve> myValves, ArrayList<Valve> eValves) {
        if (valves.size() == 0) {
            int myPressure = getMaxPressure(startValve, 26, myValves);
            int elephantPressure = getMaxPressure(startValve, 26, eValves);
            return myPressure + elephantPressure;
        } else {
            Valve nextValve = null;
            if (1 < valves.size()) {
                nextValve = valves.get(1);
            }
            
            int myValue = getMaxPressureShared(nextValve, startValve, removeAndCopy(valves, valve), addAndCopy(myValves, valve), eValves);
            int eValue = getMaxPressureShared(nextValve, startValve, removeAndCopy(valves, valve), myValves, addAndCopy(eValves, valve));
            return Math.max(myValue, eValue);
        }
    }

    /**
     * @param currentValve
     * @param minutes
     * @param remainingValves
     * @return max amount of pressure
     */
    private int getMaxPressure(Valve currentValve, int minutes, ArrayList<Valve> remainingValves) {        
        if (remainingValves.size() == 0) {
            return 0;
        }
        
        int bestPressure = 0;
        for (Valve remainingValve : remainingValves) {
            int cost = currentValve.getCost(remainingValve);
            int remainingMinutes = (minutes - cost - 1);
            if (0 < remainingMinutes) {
                int pressure = (remainingMinutes * remainingValve.getRate()) + getMaxPressure(remainingValve, remainingMinutes, removeAndCopy(remainingValves, remainingValve));                
                bestPressure = Math.max(pressure, bestPressure);
            }
        }

        return bestPressure;
    }
    
    /**
     * @param valves
     * @return start valve
     */
    private Valve getStartValve(ArrayList<Valve> valves) {
        for (Valve valve : valves) {
            if (valve.getName().equals("AA")) {
                return valve;
            }
        }
        return null;
    }
    
    /**
     * @param valves
     * @param valve
     * @return new list of valves without valve
     */
    private ArrayList<Valve> removeAndCopy(ArrayList<Valve> valves, Valve valve) {
        ArrayList<Valve> newValves = new ArrayList<Valve>();
        for (Valve v : valves) {
            if (!v.is(valve)) {
                newValves.add(v);
            }
        }
        return newValves;
    }
    
    /**
     * @param valves
     * @param valve
     * @return new list of valves where valve is included
     */
    private ArrayList<Valve> addAndCopy(ArrayList<Valve> valves, Valve valve) {
        ArrayList<Valve> newValves = new ArrayList<Valve>();
        for (Valve v : valves) {
            newValves.add(v);
        }
        newValves.add(valve);
        return newValves;
    }

    /**
     * Helper method to print information about the valves
     * @param valves
     */
    private void printValves(ArrayList<Valve> valves) {
        for (Valve v : valves) {
            v.print();
        }
    }
    
    /**
     * @param valves
     * @return valves that have rates higher than 0
     */
    private ArrayList<Valve> getRateValves(ArrayList<Valve> valves) {
        ArrayList<Valve> rateValves = new ArrayList<Valve>();
        for (Valve valve : valves) {
            if (0 < valve.getRate()) {
                rateValves.add(valve);
            }
        }
        return rateValves;
    }
    
    /**
     * Extract information and put the data into valve objects
     * @param input
     * @return valves
     */
    private ArrayList<Valve> generateValves(String input) {
        //Extract basic info
        ArrayList<Valve> valves = new ArrayList<Valve>();
        for (String line : input.split("\n")) {
            String[] parts = line.split(";");
            String[] subParts = parts[0].split(" has flow rate=");
            String name = subParts[0].replaceAll("Valve ", "");
            int rate = Integer.parseInt(subParts[1]);
            String valveStrings = parts[1].replaceAll("tunnels", "tunnel").replaceAll("valves", "valve").replaceAll("leads", "lead").replaceAll(" tunnel lead to valve ", "").replaceAll(" ", "");
            String[] neighbours = valveStrings.split(",");
            
            valves.add(new Valve(name, rate, neighbours));
        }

        //Add tunnels for each valve
        for (Valve valve : valves) {
            String[] neighbours = valve.getNeighbourStrings();
            for (String neighbour : neighbours) {
                for (Valve v : valves) {
                    if (!valve.is(v)) {
                        if (neighbour.equals(v.getName())) {
                            valve.addTunnel(v);
                        }
                    }
                }
            }
        }
        
        //Calculate travel cost to every valve with 0<rate for each valve
        for (Valve valveFrom : valves) {
            for (Valve valveTo : getRateValves(valves)) {
                if (valveFrom.is(valveTo)) {
                    valveFrom.setCost(valveTo, 0);
                } else {
                    ArrayList<Valve> visitedValves = new ArrayList<Valve>();
                    visitedValves.add(valveFrom);
                    Integer cost = getTravelCost(valveFrom, valveTo, visitedValves);
                    if (cost == Integer.MAX_VALUE) {
                        cost = null;
                    }
                    valveFrom.setCost(valveTo, cost);
                }
            }
        }
        
        return valves;
    }

    /**
     * @param valveFrom
     * @param valveTo
     * @param valveVisited
     * @return traveling cost from valveFrom to valveTo
     */
    private int getTravelCost(Valve valveFrom, Valve valveTo, ArrayList<Valve> valveVisited) {
        int cost = Integer.MAX_VALUE;
        
        for (Valve valve : valveFrom.getTunnels()) {
            if (valve.is(valveTo)) {
                return 1;
            }
            
            if (!valveVisited.contains(valve)) {
                ArrayList<Valve> valveVisitedUpdated = new ArrayList<Valve>(valveVisited);
                valveVisitedUpdated.add(valve);
                int c = getTravelCost(valve, valveTo, valveVisitedUpdated);
                if (c != Integer.MAX_VALUE) {
                    cost = Math.min(cost, c + 1);
                }
            }
        }
        
        return cost;
    }
}
