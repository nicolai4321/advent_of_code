package advent_of_code.years.year2023.day18;

import advent_of_code.utils.math.big.BigInt;

public class Instruction {
    private char direction;
    private BigInt amount;

    public Instruction(char direction, BigInt amount) {
        this.direction = direction;
        this.amount = amount;
    }

    public String toString() {
        return "(" + direction + ", " + amount + ")";
    }

    public char getDirection() {
        return direction;
    }

    public BigInt getAmount() {
        return amount;
    }
}
