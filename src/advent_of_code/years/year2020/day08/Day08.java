package advent_of_code.years.year2020.day08;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day08 extends RootDay {
    
    public Day08(Year year, int day) {
        super(year, day, "1262", "1643");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        ArrayList<Action> actions = getActions(input);
        doesRepeat(actions);
        return Action.accumularValue + "";
    }
    
    @Override
    public String run2(String input) {
        ArrayList<Action> actions = getActions(input);
        
        
        for (Action action : actions) {
            Action.accumularValue = 0;
            action.change();
            
            if (!doesRepeat(actions)) {
                return Action.accumularValue + "";
            }
            
            action.change();
            resetRuns(actions);
        }
        
        
        return "ERROR";
    }
    
    /**
     * @param actions
     * @return true if any action repeats
     */
    private boolean doesRepeat(ArrayList<Action> actions) {
        for (int i=0; i<actions.size(); i++) {
            Action action = actions.get(i);
            
            int getNext = action.getIndex();
            
            if (1 < action.getRuns()) {
                action.reverse();
                return true;
            }
            
            i += (getNext - 1);
        }
        return false;
    }
    
    /**
     * Resets the runs for all actions
     * @param actions
     */
    private void resetRuns(ArrayList<Action> actions) {
        for (Action action : actions) {
            action.setRuns(0);
        }
    }
    
    /**
     * @param input
     * @return actions from input
     */
    private ArrayList<Action> getActions(String input) {
        ArrayList<Action> actions = new ArrayList<>();

        for (String action : input.split("\n")) {
            String name = action.split(" ")[0];
            int value = Integer.parseInt(action.split(" ")[1]);
            Action a = new Action(name, value);
            actions.add(a);
        }
        
        return actions;
    }
}
