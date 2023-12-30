package advent_of_code.utils.pathing.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    public final String name;
    private ArrayList<Node> links;
    private HashMap<Node, Integer> linkCost;
    
    public Node(String name) {
        this.name = name;
        links = new ArrayList<Node>();
        linkCost = new HashMap<Node, Integer>();
    }
    
    public Node(String name, Node link) {
        this.name = name;
        links = new ArrayList<Node>();
        links.add(link);
        linkCost = new HashMap<Node, Integer>();
    }
    
    public Node(String name, ArrayList<Node> links) {
        this.name = name;
        this.links = links;
        linkCost = new HashMap<Node, Integer>();
    }

    public String getName() {
        return name;
    }
    
    public void addLinkCost(Node node, int cost) {
        if (!hasLink(node)) {
            links.add(node);
            linkCost.put(node, cost);
        }
    }

    public void addLink(Node node) {
        if (!hasLink(node)) {
            links.add(node);
        }
    }
    
    public void addLinks(ArrayList<Node> allNodes, String[] nodeNames, boolean directed) {
        for (String nodeName : nodeNames) {
            boolean nodeFound = false;
            
            for (Node node : allNodes) {
                if (node.getName().equals(nodeName)) {
                    if (directed) {
                        addLink(node);
                    } else {
                        addLink(node);
                        node.addLink(this);
                    }
                    nodeFound = true;
                    break;
                }
            }
            
            if (!nodeFound) {
                throw new RuntimeException("Could not link to node: " + nodeName);
            }
        }
    }
    
    public boolean hasLink(Node node) {
        for (Node n : links) {
            if (n.getName().equals(node.getName())) {
                return true;
            }
        }
        
        return false;
    }

    public ArrayList<Node> getLinks() {
        return links;
    }
    
    public String toString() {
        String linkNodes = "";
        
        for (Node node : links) {
            linkNodes += node.name + ",";
        }
        linkNodes = linkNodes.replaceAll(",$", "");
        
        return "{" + name + ": [" + linkNodes + "]}";
    }
    
    public Integer getLinkCost(Node node) {
        return linkCost.get(node);
    }
    
    public String contentToString() {
        String printString = name + ": [";
        
        for (Node node : links) {
            Integer cost = linkCost.get(node);
            String c = "";
            if (cost != null) {
                c += " (" + cost + ")";
            }
            
            printString += node.getName() + c + ", ";
        }
        printString = printString.replaceAll(", $", "") + "]";
        
        return printString;
    }
}
