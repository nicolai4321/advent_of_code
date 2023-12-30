package advent_of_code.years.year2020.day07;

import java.util.HashMap;

import advent_of_code.utils.Log;

public class Rule {
    private String bagName;
    private HashMap<Rule, Integer> rules;
    
    public Rule(String bagName) {
        this.bagName = bagName;
        rules = new HashMap<Rule, Integer>();
    }
    
    public void addRule(Rule rule, int nr) {
        rules.put(rule, nr);
    }

    public void print() {
        String s = bagName + ": [";
        for (Rule rule : rules.keySet()) {
            s += "{" + rule.getBagName() + ":" + rules.get(rule) + "}, ";
        }
        s = s.replaceAll(", $", "") + "]";
        
        Log.show(s);
    }

    public boolean canContainBag(String bagName) {
        for (Rule rule : rules.keySet()) {
            if (rule.canContainBagRecursive(bagName)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean canContainBagRecursive(String bagName) {
        if (this.bagName.equals(bagName)) {
            return true;
        }

        for (Rule rule : rules.keySet()) {
            if (rule.canContainBagRecursive(bagName)) {
                return true;
            }
        }
        
        return false;
    }
    
    public String getBagName() {
        return bagName;
    }

    public int countBags() {
        int count = 0;
        
        for (Rule rule : rules.keySet()) {
            count += rules.get(rule) + (rules.get(rule) * rule.countBags());
        }
        
        return count;
    }
}
