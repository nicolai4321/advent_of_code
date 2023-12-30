package advent_of_code.years.year2023.day08;

import advent_of_code.utils.math.big.BigInt;

public class Round {
    private BigInt jump;
    private BigInt state;
    
    public Round(BigInt jump) {
        this.jump = jump;
        this.state = jump;
    }
    
    public void increase() {
        state = state.add(jump);
    }
    
    public BigInt getState() {
        return state;
    }
}
