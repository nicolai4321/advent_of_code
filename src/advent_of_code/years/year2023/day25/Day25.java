package advent_of_code.years.year2023.day25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Log;
import advent_of_code.utils.helper.Tuple;
import advent_of_code.utils.pathing.graph.Edge;
import advent_of_code.utils.pathing.graph.GraphFinder;
import advent_of_code.utils.pathing.graph.Node;

public class Day25 extends RootDay {
    public Day25(Year year, int day) {
        super(year, day, "547410", null);
        setInput1("input01.txt");
        setInput2("input01.txt");
        this.disableRun2();
    }
    
    /**
     * Find the 3 edges that cuts the graph in two. Multiply the sizes of the two sub graphs.
     */
    @Override
    public String run1(String input) {
        ArrayList<Node> nodes = getNodes(input);
        ArrayList<Edge> edges = getEdgesOnThreePaths(nodes);
        Integer result = splitGraph(nodes, edges);
        
        return result + "";
    }
    
    @Override
    public String run2(String input) {
        // there is no run2
        return "";
    }
    
    /**
     * Takes two random nodes and find paths between those where none of the edges are shared between the paths.
     * If only 3 paths exists then the involving edges will be returned.
     * 
     * @param nodes
     * @return edges that are part of the 3 paths
     */
    public static ArrayList<Edge> getEdgesOnThreePaths(ArrayList<Node> nodes) {
        Random random = new Random();
        
        int index0 = random.nextInt(nodes.size());
        int index1 = random.nextInt(nodes.size());
        
        if (index0 == index1) {
            return getEdgesOnThreePaths(nodes);
        }
        
        Node node0 = nodes.get(index0);
        Node node1 = nodes.get(index1);
        
        Log.show("trying: " + node0.name + " & " + node1.name);
        
        ArrayList<ArrayList<Edge>> paths = new ArrayList<ArrayList<Edge>>();
        ArrayList<Tuple<Node, ArrayList<Edge>>> queue = new ArrayList<>();
        queue.add(new Tuple<Node, ArrayList<Edge>>(node0, new ArrayList<Edge>()));
        
        ArrayList<Edge> visited = new ArrayList<Edge>();
        ArrayList<Edge> edges = GraphFinder.bfs(queue, node1, visited);
        paths.add(edges);
        
        while (edges != null) {
            queue = new ArrayList<Tuple<Node, ArrayList<Edge>>>();
            queue.add(new Tuple<Node, ArrayList<Edge>>(node0, new ArrayList<Edge>()));
            
            visited = new ArrayList<Edge>();
            for (ArrayList<Edge> path : paths) {
                visited.addAll(path);
            }
            
            edges = GraphFinder.bfs(queue, node1, visited);
            
            if (edges == null) {
                break;
            }
            
            paths.add(edges);
        }
        
        if (paths.size() == 3) {
            ArrayList<Edge> edgesRem = new ArrayList<Edge>();
            for (ArrayList<Edge> path : paths) {
                edgesRem.addAll(path);
            }
            
            return edgesRem;
        } else {
            //try again
            return getEdgesOnThreePaths(nodes);
        }
    }
    
    /**
     * Splits graph in two
     * @param nodes
     * @param edges
     * @return the sizes of the two graphs multiplied
     */
    public static Integer splitGraph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        for (int i=0; i<edges.size()-2; i++) {
            for (int j=i+1; j<edges.size()-1; j++) {
                if (j<=i) {j = i+1;}
                
                for (int h=j+1; h<edges.size(); h++) {
                    if (h<=j) {h = j+1;}
                    
                    if (j != i && j!=h && i!=h) {
                        Edge edge0 = edges.get(i);
                        Edge edge1 = edges.get(j);
                        Edge edge2 = edges.get(h);
                        
                        if (edge0.equals(edge1)) {
                            continue;
                        }

                        if (edge0.equals(edge2)) {
                            continue;
                        }
                        
                        if (edge1.equals(edge2)) {
                            continue;
                        }

                        Integer result = splitGraph(nodes, edge0, edge1, edge2);
                        if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Splits graph in two
     * @param nodes
     * @param edge0
     * @param edge1
     * @param edge2
     * @return the sizes of the two graphs multiplied
     */
    public static Integer splitGraph(ArrayList<Node> nodes, Edge edge0, Edge edge1, Edge edge2) {
        ArrayList<HashSet<Node>> groups = new ArrayList<>();
        
        for (Node node : nodes) {
            if (isNodeInGroup(groups, node)) {
                continue;
            }
            
            HashSet<Node> group = new HashSet<Node>();
            group.add(node);
            groups.add(group);
            
            if (2 < groups.size()) {
                return null;
            }
            
            ArrayList<Node> links = node.getLinks();
            for (Node n : links) {
                if (!isEdgeCut(node, n.name, edge0, edge1, edge2)) {
                    createOfUpdateGroups(n, group, edge0, edge1, edge2);
                }
            }
        }
        
        if (groups.size() == 2) {
            return (groups.get(0).size() * groups.get(1).size());
        } else {
            return null;
        }
    }
    
    /**
     * Create or update groups with node
     * @param node
     * @param group
     * @param edge0
     * @param edge1
     * @param edge2
     */
    private static void createOfUpdateGroups(Node node, HashSet<Node> group, Edge edge0, Edge edge1, Edge edge2) {
        if (group.contains(node)) {
            return;
        }
        
        group.add(node);
        
        ArrayList<Node> links = node.getLinks();
        for (Node n : links) {
            if (!isEdgeCut(node, n.name, edge0, edge1, edge2)) {
                createOfUpdateGroups(n, group, edge0, edge1, edge2);
            }
        }
    }
    
    private static boolean isEdgeCut(Node node, String name, Edge edge0, Edge edge1, Edge edge2) {
        return (edge0.equals(node.name, name)) || (edge1.equals(node.name, name)) || (edge2.equals(node.name, name));
    }
    
    /**
     * @param input
     * @return a list of components
     */
    private ArrayList<Node> getNodes(String input) {
        HashMap<Node, String[]> map = new HashMap<Node, String[]>();
        ArrayList<Node> nodes = new ArrayList<Node>();
        
        for (String line : input.split("\n")) {
            String name = line.split(": ")[0];
            String[] names = line.split(": ")[1].split(" ");

            Node node = getNode(nodes, name);
            if (node == null) {
                node = new Node(name);
            }
            
            nodes.add(node);
            map.put(node, names);
            
            for (String n : names) {
                Node linkNode = new Node(n);
                if (!hasNode(nodes, linkNode)) {
                    nodes.add(linkNode);
                }
            }
        }
        
        for (Node node : map.keySet()) {
            String[] links = map.get(node);
            node.addLinks(nodes, links, false);
        }
        
        return nodes;
    }
    
    private static boolean isNodeInGroup(ArrayList<HashSet<Node>> groups, Node node) {
        for (HashSet<Node> group : groups) {
            if (group.contains(node)) {
                return true;
            }
        }
        return false;
    }

    
    private static Node getNode(ArrayList<Node> nodes, String nodeName) {
        for (Node n : nodes) {
            if (n.name.equals(nodeName)) {
                return n;
            }
        }
        
        return null;
    }
    
    private static boolean hasNode(ArrayList<Node> nodes, Node node) {
        for (Node n : nodes) {
            if (n.name.equals(node.name)) {
                return true;
            }
        }
        
        return false;
    }
}
