package advent_of_code.years.year2022.day21;

import java.math.BigInteger;
import java.util.ArrayList;

import advent_of_code.utils.math.big.BigInt;

public class Var {
    private String name;
    private Integer value = null;
    private String referenceName = null;
    private String referenceName0 = null;
    private String referenceName1 = null;
    private String operator = null;
    private ArrayList<Operation> operations = new ArrayList<Operation>();
    private Var reference;
    private Var reference0;
    private Var reference1;
    
    public Var(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    public Var(String name, String referenceName) {
        this.name = name;
        this.referenceName = referenceName;
    }

    public Var(String name, String referenceName0, String operator, String  referenceName1) {
        this.name = name;
        this.referenceName0 = referenceName0;
        this.operator = operator;
        this.referenceName1 = referenceName1;
    }
    
    /**
     * @param x
     * @param targetValue
     * @return the value of x such that the operations applied on x equals the target value
     */
    private BigInteger solve(Var x, BigInteger targetValue) {
        ArrayList<Operation> operations = x.getOperations(); 
        for (int i=operations.size()-1; 0<=i; i--) {
            Operation operation = operations.get(i);
            
            if (operation.getOperation().equals("/")) {
                if (operation.isVarLeft()) {
                    targetValue = targetValue.multiply(operation.getValue());
                } else {
                    throw new RuntimeException("Not implemented");
                }
            }
            
            if (operation.getOperation().equals("*")) {
                targetValue = targetValue.divide(operation.getValue());
            }
            
            if (operation.getOperation().equals("+")) {
                targetValue = targetValue.subtract(operation.getValue());
            }
            
            if (operation.getOperation().equals("-")) {
                if (operation.isVarLeft()) {
                    targetValue = targetValue.add(operation.getValue());
                } else {
                    targetValue = (targetValue.subtract(operation.getValue())).multiply(BigInt.get(-1));
                }
            }
        }
        
        return targetValue;
    }
    
    /**
     * @param x
     * @return the value that should be assigned x to match
     */
    public BigInteger solve(String x) {
        Var variable0 = reference0.getVariable(x);
        Var variable1 = reference1.getVariable(x);
        
        BigInteger value0 = variable0.getValue();
        BigInteger value1 = variable1.getValue();
        
        if (value0 == null) {
            return solve(variable0, value1);
        } else if (value1 == null) {
            return solve(variable1, value0);
        }
        throw new RuntimeException("Variable " + x + " was not found");
    }
    
    /**
     * @param x
     * @return variable with operations if it contains x. Otherwise the normal variable is returned
     */
    private Var getVariable(String x) {
        //return variable x
        if (name.equals(x)) {
            return new Var(x, x);
        }
        
        //number
        if (value != null) {
            return this;
        }

        //value from another variable
        if (reference != null) {
            return this;
        }
        
        //operation between two variables
        if (reference0 != null && reference1 != null) {
            Var var0 = reference0.getVariable(x);
            Var var1 = reference1.getVariable(x);
            
            //var0 is x
            if (var0.getName().equals(x)) {
                Operation op = new Operation(operator, var1.getValue(), true);
                var0.operations.add(op);
                return var0;
            //var1 is x
            } else if (var1.getName().equals(x)) {
                Operation op = new Operation(operator, var0.getValue(), false);
                var1.operations.add(op);
                return var1;
            } else {
                return this;
            }
        }
                
        return new Var("notX", "x");
    }
    
    /**
     * @return the value of this variable
     */
    public BigInteger getValue() {
        //number
        if (value != null) {
            return BigInt.get(value);
        }
        
        //value from another variable
        if (reference != null) {
            return reference.getValue();
        }
        
        //operation between two variables
        if (operator != null) {
            BigInteger value0 = reference0.getValue();
            BigInteger value1 = reference1.getValue();
            BigInteger val = null;
            
            if (operator.equals("*")) {
                val = value0.multiply(value1);
            }
            
            if (operator.equals("/")) {
                val = value0.divide(value1);
            }
            
            if (operator.equals("+")) {
                val = value0.add(value1);
            }
            
            if (operator.equals("-")) {
                val = value0.subtract(value1);
            }
            
            if (val == null) {
                throw new RuntimeException("Unknown operator: '" + operator + "'");                
            }
                        
            return val;
        }
        
        return null;
    }
    
    /**
     * This method links the names of variables to the actual variable objects
     * @param variables
     */
    public void link(ArrayList<Var> variables) {
        for (Var variable : variables) {
            //assign reference
            if (referenceName != null && reference == null && variable.getName().equals(referenceName)) {
                reference = variable;
            }
            
            //assign reference0
            if (referenceName0 != null && reference0 == null && variable.getName().equals(referenceName0)) {
                reference0 = variable;
            }
            
            //assign reference1
            if (referenceName1 != null && reference1 == null && variable.getName().equals(referenceName1)) {
                reference1 = variable;
            }
        }
    }
    
    public String toString() {
        if (value != null) {
            return "{" + name + ":" + value + "}";
        }
        
        if (referenceName != null) {
            return "{" + name + ":" + referenceName + "}";
        }
        
        return "{" + name + ":" + referenceName0 + " " + operator + " " + referenceName1 + "}";
    }
    
    public ArrayList<Operation> getOperations() {
        return operations;
    }
    
    public String getName() {
        return name;
    }
}
