package advent_of_code.years.year2023.day08;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Log;
import advent_of_code.utils.math.big.BigInt;
import advent_of_code.utils.pathing.graph.GraphFinder;
import advent_of_code.utils.pathing.graph.Node;

public class Day08 extends RootDay {
    public Day08(Year year, int day) {
        super(year, day, "15517", "14935034899483");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        GraphFinder graphFinder = getGraphFinder(lines);
        return steps(graphFinder , lines[0].toCharArray()) + "";
    }

    @Override
    public String run2(String input) {
        String[] lines = input.split("\n");
        GraphFinder graphFinder = getGraphFinder(lines);
        return stepsGhost2(graphFinder , lines[0].toCharArray()) + "";
    }
    
    private BigInt stepsGhost2(GraphFinder graphFinder, char[] instructions) {
        ArrayList<Node> startingNodes = new ArrayList<Node>();
        for (Node node : graphFinder.getNodes()) {
            String name = node.getName();
            if (name.charAt(name.length() - 1) == 'A') {
                startingNodes.add(node);
            }
        }
        return travelSmart(startingNodes, instructions);
    }
    
    private BigInt travelSmart(ArrayList<Node> startingNodes, char[] instructions) {
        ArrayList<Round> rounds = new ArrayList<Round>();
        for (Node node : startingNodes) {
            ArrayList<Integer> smart = stepSmart(node, new ArrayList<Integer>(), 0, instructions);
            rounds.add(new Round(new BigInt(smart.get(0))));
        }
        
        return getMult(rounds);
    }

    private BigInt getMult(ArrayList<Round> rounds) {
        BigInt result =null;
        
        for (Round r : rounds) {
            if (result == null) {
                result = r.getState();
            } else {
                result = BigInt.lcm(r.getState(), result);
            }
        }
        
        return result;
    }

    private ArrayList<Integer> stepSmart(Node startNode, ArrayList<Integer> rounds, int round, char[] instructions) {
        //check repeat and stop
        if (5 < rounds.size()) {
            return rounds;
        }
        
        if (10000000 < round && 0 < rounds.size()) {
            Log.warn("Stop early");
            return rounds;
        }
        
        char instruction = instructions[round % instructions.length];
        round++;
        
        Node nextNode;
        if (instruction == 'L') {
            nextNode = startNode.getLinks().get(0);
        } else if (instruction == 'R') {
            ArrayList<Node> links = startNode.getLinks();
            
            if (links.size() == 1) {
                nextNode = startNode.getLinks().get(0);
            } else if (links.size() == 2) {
                nextNode = startNode.getLinks().get(1);
            } else {
                throw new RuntimeException("Too many links");
            }
        } else {
            throw new RuntimeException("Invlaid char");
        }

        
        if (nextNode.getName().charAt(2) == 'Z') {
            rounds.add(round);
        }

        return stepSmart(nextNode, rounds, round, instructions);
    }
    
    private int steps(GraphFinder graphFinder, char[] instructions) {
        Node startNode = graphFinder.getNode("AAA");
        return travel(startNode, 0, instructions);
    }
    
    private int travel(Node startNode, int round, char[] instructions) {
        char instruction = instructions[round % instructions.length];
        round++;
        
        Node nextNode;
        if (instruction == 'L') {
            nextNode = startNode.getLinks().get(0);
        } else if (instruction == 'R') {
            ArrayList<Node> links = startNode.getLinks();
            
            if (links.size() == 1) {
                nextNode = startNode.getLinks().get(0);
            } else if (links.size() == 2) {
                nextNode = startNode.getLinks().get(1);
            } else {
                throw new RuntimeException("Too many links");
            }
        } else {
            throw new RuntimeException("Invlaid char");
        }

        
        if (nextNode.getName().equals("ZZZ")) {
            return round;
        }

        return travel(nextNode, round, instructions);
    }

    private ArrayList<String[]> getNodes(String[] lines) {
        ArrayList<String[]> nodes = new ArrayList<String[]>();
        for (int i=2; i<lines.length; i++) {
            String line = lines[i];
            
            String nodeA = line.split(" = ")[0];
            
            String nodeB = line.split(" = ")[1].replaceAll("\\(", "").split(", ")[0];
            String nodeC = line.split(", ")[1].replaceAll("\\)", "");
            
            nodes.add( new String[] { nodeA, nodeB });
            nodes.add( new String[] { nodeA, nodeC });
        }
        return nodes;
    }
    
    private GraphFinder getGraphFinder(String[] lines) {
        GraphFinder graphFinder = new GraphFinder(getNodes(lines), true);
        return graphFinder;
    }
}
