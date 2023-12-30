package advent_of_code.utils.pathing.graph;

public class Edge {
    public final Node node0;
    public final Node node1;
    
    public Edge(Node node0, Node node1) {
        this.node0 = node0;
        this.node1 = node1;
    }
    
    public boolean equals(Edge e) {
        return (e.node0.getName().equals(node0.getName()) && e.node1.getName().equals(node1.getName())) ||
               (e.node1.getName().equals(node0.getName()) && e.node0.getName().equals(node1.getName()));
    }
    
    public boolean equals(String nodeName0, String nodeName1) {
        return (node0.getName().equals(nodeName0) && node1.getName().equals(nodeName1)) ||
               (node1.getName().equals(nodeName0) && node0.getName().equals(nodeName1));
    }
    
    public String toString() {
        return "(" + node0.getName() + "," + node1.getName() + ")";
    }
}
