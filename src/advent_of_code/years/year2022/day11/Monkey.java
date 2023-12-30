package advent_of_code.years.year2022.day11;

import java.util.ArrayList;

public class Monkey {
    private int id;
    private ArrayList<Long> items = new ArrayList<Long>();
    private String[] operation = new String[3];    
    private int divisible;
    private int idTrue;
    private int idFalse;
    private long inspections = 0;
    private long max;
    
    public Monkey(int id, ArrayList<Long> items, String[] operation, int divisible, int idTrue, int idFalse, boolean useExample) {
        this.id = id;
        this.items = items;
        this.operation = operation;
        this.divisible = divisible;
        this.idTrue = idTrue;
        this.idFalse = idFalse;
        
        if (useExample) {
            max = 13 * 17 * 19 * 23;
        } else {
            max = 2 * 3 * 5 * 7 * 11 * 13 * 17 * 19;
        }
    }

    /**
     * @param currentItem
     * @return worry
     */
    public long computeWorry(long currentItem) {
        inspections++;
                
        long value0 = (operation[0].equals("old")) ? currentItem : Long.parseLong(operation[0]);
        long value1 = (operation[2].equals("old")) ? currentItem : Long.parseLong(operation[2]);
        
        if (operation[1].equals("*")) {
            long newValue = value0 * value1;
        
            //sanity check
            if (newValue < 0) {
                throw new RuntimeException("Invalid newValue '" + newValue + "' calculated from " + value0 + " and " + value1);
            }
            return newValue;
        }
        
        if (operation[1].equals("+")) {
            long newValue = value0 + value1;
            
            //sanity check
            if (newValue < 0) {
                throw new RuntimeException("Invalid newValue '" + newValue + "' calculated from " + value0 + " and " + value1);
            }
            
            return newValue;
        }
        
        throw new RuntimeException("Unknown operator " + operation[1]);
    }
    
    /**
     * Monkey passes all of its items
     * @param monkeys
     */
    public void pass(ArrayList<Monkey> monkeys) {
        for (int j=0; j<items.size(); j++) {
            long item = items.get(j);
            if (test(item)) {
                passTo(monkeys, true, item);
            } else {
                passTo(monkeys, false, item);
            }
        }
        
        items = new ArrayList<Long>();
    }

    /**
     * @param id
     * @param monkeys
     * @return monkey
     */
    public Monkey findMonkey(int id, ArrayList<Monkey> monkeys) {
        for (Monkey monkey : monkeys) {
            if (id == monkey.getId()) {
                return monkey;
            }
        }
        throw new RuntimeException("Could not find monkey with id " + id);
    }
    
    @Override
    public String toString() {
        String s = "{" + id + ": [";
        
        for (long i : items) {
            s += i + ",";
        }
        s += "], " + operation[0] + operation[1] + operation[2] + "|" + divisible + ", true: " + idTrue + ", false: " + idFalse + "}";
        return s;
    }
    
    public ArrayList<Long> getItems() {
        return items;
    }

    public String[] getOperation() {
        return operation;
    }

    public long getInspections() {
        return inspections;
    }
    
    public int getId() {
        return id;
    }

    private void addItem(Long item) {
        items.add(item);
    }
    
    /**
     * @param monkeys
     * @param b
     * @param item
     */
    private void passTo(ArrayList<Monkey> monkeys, boolean b, Long item) {
        Monkey monkey = (b) ? findMonkey(idTrue, monkeys) : findMonkey(idFalse, monkeys);
        if (max <= item) {            
            monkey.addItem(item % max);
        } else {
            monkey.addItem(item);
        }
    }
    
    /**
     * @param item
     * @return true if test is successful
     */
    private boolean test(long item) {
        return item % divisible == 0;
    }
}
