package advent_of_code.years.year2022.day21;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day21 extends RootDay {
    public Day21(Year year, int day) {
        super(year, day, "63119856257960", "3006709232464");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * Find the value of the variable root
     */
    @Override
    public String run1(String input) {
        ArrayList<Var> variables = mapToVariables(input);
        linkVariables(variables);
        Var root = getVar(variables, "root");
        return root.getValue() + "";
    }
    
    /**
     * Find the value of the variable humn such that two variables in the variable root matches
     */
    @Override
    public String run2(String input) {
        ArrayList<Var> variables = mapToVariables(input);
        linkVariables(variables);
        Var root = getVar(variables, "root");
        return root.solve("humn") + "";
    }
        
    /**
     * @param variables
     * @param name
     * @return variable that matches the name given as a parameter
     */
    private Var getVar(ArrayList<Var> variables, String name) {
        for (Var v : variables) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }

    /**
     * This methods links all the values to the variables
     * @param variables
     */
    private void linkVariables(ArrayList<Var> variables) {
        for (Var v : variables) {
            v.link(variables);
        }
    }

    /**
     * @param input
     * @return a list of variables
     */
    private ArrayList<Var> mapToVariables(String input) {
        ArrayList<Var> variables = new ArrayList<Var>();
        for (String line : input.split("\n")) {
            String name = line.replaceAll(":.*$", "");
            String value = line.split(": ")[1];
                        
            //value is number
            if (value.matches("[0-9]+")) {
                int nr = Integer.parseInt(value);
                variables.add(new Var(name, nr));
            //value is from another variable
            } else if (value.length() == 4) {
                variables.add(new Var(name, value));
            //value is from an operation
            } else {
                String[] operation = value.split(" ");
                variables.add(new Var(name, operation[0] , operation[1], operation[2]));
            }
        }
        return variables;
    }
}
