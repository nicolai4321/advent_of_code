package advent_of_code.years.year2021.day12;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.pathing.graph.GraphFinder;
import advent_of_code.utils.pathing.graph.Node;

public class Day12 extends RootDay {
    
	public Day12(Year year, int day) {
		super(year, day, "3000", "74222");
        setInput1("input01.txt");
        setInput2("input01.txt");
	}

	@Override
	public String run1(String input) {
	    GraphFinder graphFinder = new GraphFinder(formatInput(input), false);
        graphFinder.print();
	    return nrValidPaths(graphFinder, "start", new ArrayList<Node>(), true) + "";
	}

	@Override
	public String run2(String input) {
        GraphFinder graphFinder = new GraphFinder(formatInput(input), false);
        return nrValidPaths(graphFinder, "start", new ArrayList<Node>(), false) + "";
	}
	
	private int nrValidPaths(GraphFinder graphFinder, String nodeName, ArrayList<Node> visited, boolean usedOne) {
	    if (nodeName.equals("end")) {
	        return 1;
	    }
	    
	    Boolean legalVisit = isVisitedCaveLegal(nodeName, visited, usedOne);
	    
	    if (legalVisit == null) {
	        legalVisit = true;
	        usedOne = true;
	    }
	    
	    if (!legalVisit) {
	        return 0;
	    }
	    
	    Node currentNode = graphFinder.getNode(nodeName);
	    visited.add(currentNode);
	    
        int validPaths = 0;
	    for (Node node : currentNode.getLinks()) {
	        ArrayList<Node> visitedCloned = GraphFinder.cloneNodes(visited, false);
	        validPaths += nrValidPaths(graphFinder, node.getName(), visitedCloned, usedOne);
	    }
	    
	    return validPaths;
	}
	
	private Boolean isVisitedCaveLegal(String nodeName, ArrayList<Node> visited, boolean usedOne) {
	    if (isBigCave(nodeName)) {
	        return true;
	    }
	    
	    if (hasVisitedBefore(nodeName, visited)) {
	        if (nodeName.equals("start")) {
	            return false;
	        }
	        
	        if (usedOne) {
	            return false;
	        } else {
	            return null; //legal but uses one
	        }
	    }

	    return true;
	}
	
	private boolean hasVisitedBefore(String nodeName, ArrayList<Node> visited) {
        for (Node visitedNode : visited) {
            if (visitedNode.getName().equals(nodeName)) {
                return true;
            }
        }
        return false;
	}
	
	private boolean isBigCave(String nodeName) {
	    for (char c : nodeName.toCharArray()) {
	        if (!Character.isUpperCase(c)) {
	            return false;
	        }
	    }
        return true;
    }

    private ArrayList<String[]> formatInput(String input) {
	    ArrayList<String[]> nodes = new ArrayList<String[]>();
	    for (String line : input.split("\n")) {
            String nodeA = line.split("-")[0];
            String nodeB = line.split("-")[1];
	        
	        nodes.add( new String[] { nodeA, nodeB });
	    }
	    return nodes;
	}
}
