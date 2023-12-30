package advent_of_code.utils.pathing.graph;

import java.util.ArrayList;

import advent_of_code.utils.Lists;
import advent_of_code.utils.Log;
import advent_of_code.utils.helper.Tuple;

public class GraphFinder {
    private ArrayList<Node> nodes;
    
    public GraphFinder(ArrayList<String[]> nodeLinks, boolean directedGraph) {
        nodes = new ArrayList<Node>();
        
        addNodes(nodeLinks);
        linkNodes(nodeLinks, directedGraph);
    }
    
    public GraphFinder(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
    
    /**
     * @param startNode
     * @param endNode
     * @return the edges for the shortest route to the endNode
     */
    public static ArrayList<Edge> bfs(Node startNode, Node endNode) {
        Tuple<Node, ArrayList<Edge>> tuple = new Tuple<Node, ArrayList<Edge>>(startNode, new ArrayList<Edge>());
        
        ArrayList<Tuple<Node, ArrayList<Edge>>> queue = new ArrayList<>();
        queue.add(tuple);
        
        return bfs(queue, endNode, new ArrayList<Edge>());
    }

    /**
     * @param startNode
     * @param endNode
     * @return the edges for the shortest route to the endNode
     */    
    public static ArrayList<Edge> bfs(ArrayList<Tuple<Node, ArrayList<Edge>>> queue, Node endNode, ArrayList<Edge> visited) {
        if (queue.isEmpty()) {
            return null;
        }
        
        Tuple<Node, ArrayList<Edge>> tuple = queue.get(0);
        queue.remove(0);
        
        Node node = tuple.first;
        ArrayList<Edge> tracks = tuple.second;
        
        if (node.name.equals(endNode.name)) {
            return tracks;
        }
        
        ArrayList<Node> links = node.getLinks();
        for (Node n : links) {
            Edge newEdge = new Edge(node, n);
            if (!hasEdge(visited, newEdge)) {
                visited.add(newEdge);

                ArrayList<Edge> copyTracks = Lists.shallowCopy(tracks);
                copyTracks.add(newEdge);
                
                queue.add(new Tuple<Node, ArrayList<Edge>>(n, copyTracks));
            }
        }
        
        return bfs(queue, endNode, visited);
    }
    
    public boolean hasNode(String name) {
        for (Node node : nodes) {
            if (name.equals(node.getName())) {
                return true;
            }
        }
        
        return false;
    }
    
    public Node getNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }
    
    public String toString() {
        String printString = "";
        for (Node node : nodes) {
            printString += node.contentToString() + "\n";
        }
        
        return printString;
    }
    
    public void print() {
        Log.show(toString());
    }
    
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    
    public static ArrayList<Node> cloneNodes(ArrayList<Node> nodes, boolean cloneNodeContent) {
        if (cloneNodeContent) {
            throw new RuntimeException("cloneNodeContent not implemented!");
        }
        
        ArrayList<Node> clonedNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            clonedNodes.add(node);
        }
        
        return clonedNodes;
    }
    
    private void addNodes(ArrayList<String[]> nodeLinks) {
        for (String[] nodeLink : nodeLinks) {
            String nodeNameA = nodeLink[0];
            String nodeNameB = nodeLink[1];
            
            if (!hasNode(nodeNameA)) {
                nodes.add(new Node(nodeNameA));
            }
            
            if (!hasNode(nodeNameB)) {
                nodes.add(new Node(nodeNameB));
            }

        }
    }
    
    private void linkNodes(ArrayList<String[]> nodeLinks, boolean directedGraph) {
        for (String[] nodeLink : nodeLinks) {
            String nodeNameA = nodeLink[0];
            String nodeNameB = nodeLink[1];
            
            Node nodeA = getNode(nodeNameA);
            Node nodeB = getNode(nodeNameB);
            nodeA.addLink(nodeB);
            
            if (!directedGraph) {
                nodeB.addLink(nodeA);
            }
        }
    }
    
    /**
     * @param edges
     * @param edge
     * @return true if edges has edge
     */
    private static boolean hasEdge(ArrayList<Edge> edges, Edge edge) {
        for (Edge e : edges) {
            if (e.equals(edge)) {
                return true;
            }
        }
        return false;
    }
}
