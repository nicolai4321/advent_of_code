package advent_of_code.years.year2022.day21;

import java.math.BigInteger;

public class Operation {
    private String operator;
    private BigInteger value;
    private boolean variableOnLeft;
    
    public Operation(String operator, BigInteger value, boolean variableOnLeft) {
        this.operator = operator;
        this.value = value;
        this.variableOnLeft = variableOnLeft;
    }

    public String getOperation() {
        return operator;
    }

    public BigInteger getValue() {
        return value;
    }
    
    public boolean isVarLeft() {
        return variableOnLeft;
    }
}
