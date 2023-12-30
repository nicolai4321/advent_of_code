package advent_of_code.years.year2020.day08;

public class Action {
    public static int accumularValue = 0;
    
    public static final String ACC = "acc";
    public static final String JMP = "jmp";
    public static final String NOP = "nop";
    
    private String type;
    private int value;
    private int runs = 0;
    
    public Action(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return accumularValue + " |" + type + ":" + value + "|";
    }

    
    public void print() {
        System.out.println(toString());
    }

    public int getRuns() {
        return runs;
    }

    public int getIndex() {
        runs++;
        
        if (type.equals(ACC)) {
            accumularValue += value;
            return 1;
        }
        
        if (type.equals(JMP)) {
            return value;
        }
        
        if (type.equals(NOP)) {
            return 1;
        }
        
        throw new RuntimeException("Invalid run for type: " + type);
    }

    public void reverse() {
        if (type.equals(ACC)) {
            accumularValue -= value;
        }
    }

    public void change() {
        if (type.equals(NOP)) {
            type = JMP;
            return;
        }
        
        if (type.equals(JMP)) {
            type = NOP;
            return;
        }
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }
}
