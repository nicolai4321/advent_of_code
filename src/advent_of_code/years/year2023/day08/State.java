package advent_of_code.years.year2023.day08;

import java.util.ArrayList;

import advent_of_code.utils.pathing.graph.Node;

public class State {
    private int round;
    private ArrayList<Node> nextNodes = null;
    
    public State(int round) {
        this.round = round;
    }

    public void apply(ArrayList<Node> nextNodes) {
        this.nextNodes = nextNodes;
    }

    public ArrayList<Node> getNextNodes() {
        return nextNodes;
    }

    public int getRound() {
        return round;
    }

}
