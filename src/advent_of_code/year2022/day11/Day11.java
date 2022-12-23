package advent_of_code.year2022.day11;

import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.utils.RootDay;

public class Day11 extends RootDay {
    public Day11() {
        super(2022, 11, "78678", "15333249714");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        ArrayList<Monkey> monkeys = generateMonkeys(false, input);
        simulateRounds(20, monkeys, 3);
        return getLevelOfMonkeyBusiness(monkeys) + "";
    }
    
    @Override
    public String run2(String input) {
        ArrayList<Monkey> monkeys = generateMonkeys(false, input);
        simulateRounds(10000, monkeys, null);
        return getLevelOfMonkeyBusiness(monkeys) + "";
    }
    
    /**
     * @param monkeys
     * @return level of monkey business
     */
    private long getLevelOfMonkeyBusiness(ArrayList<Monkey> monkeys) {
        ArrayList<Long> inspections = new ArrayList<Long>();
        
        for (Monkey monkey : monkeys) {
            inspections.add(monkey.getInspections());
        }
        Collections.sort(inspections, Collections.reverseOrder());
        return inspections.get(0)* inspections.get(1);
    }
    
    /**
     * 
     * @param nrRounds
     * @param monkeys
     * @param divide
     */
    private void simulateRounds(int nrRounds, ArrayList<Monkey> monkeys, Integer divide) {
        for (int i=0; i<nrRounds; i++) {
            for (Monkey monkey : monkeys) {
                ArrayList<Long> items = monkey.getItems();
                for (int j=0; j<items.size(); j++) {
                    long worryLevel = monkey.computeWorry(items.get(j));
                    if (divide != null) {
                        worryLevel = worryLevel / 3;                
                    }
                    items.set(j, worryLevel);
                }
                monkey.pass(monkeys);
            }
        }
    }
    
    /**
     * @param useExample
     * @return monkeys
     */
    private ArrayList<Monkey> generateMonkeys(boolean useExample, String input) {        
        input += "\n ";
        ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
        
        int id = -1;
        ArrayList<Long> items = null;
        String[] operations = null;
        int divisibleBy = -1;
        int idTrue = -1;
        int idFalse = -1;
        
        for (String line : input.split("\n")) {            
            if (line.contains("Monkey")) {
                String s = line.replaceAll("Monkey ", "").replaceAll(":", "");
                id = Integer.parseInt(s);
            } else if (line.contains("Starting items")) {
                String s = line.replaceAll(" Starting items: ", "");
                s = s.replaceAll(" ", "");
                String[] nrs = s.split(",");
                
                items = new ArrayList<Long>();
                for (String nr : nrs) {
                    items.add(Long.parseLong(nr));
                }
            } else if (line.contains("Operation")) {
                String s = line.replaceAll("  Operation: new = ", "");
                operations = s.split(" ");
            } else if (line.contains("Test")) {
                String s = line.replaceAll("  Test: divisible by ", "");
                divisibleBy = Integer.parseInt(s);
            } else if (line.contains("If true")) {
                String s = line.replaceAll("    If true: throw to monkey ", "");
                idTrue = Integer.parseInt(s);
            } else if (line.contains("If false")) {
                String s = line.replaceAll("    If false: throw to monkey ", "");
                idFalse = Integer.parseInt(s); 
            } else {
                Monkey monkey = new Monkey(id, items, operations, divisibleBy, idTrue, idFalse, useExample);
                monkeys.add(monkey);
            }
        }
        
        return monkeys;
    }
}
