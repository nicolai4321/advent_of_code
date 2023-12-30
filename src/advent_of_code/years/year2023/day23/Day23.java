package advent_of_code.years.year2023.day23;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.pathing.graph.Node;


public class Day23 extends RootDay {
    private static HashMap<String, Node> map = new HashMap<String, Node>();

    public Day23(Year year, int day) {
        super(year, day, "2442", "6898");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    /**
     * What is the longest route where <,>,^,v force a direction?
     */
    @Override
    public String run1(String input) {
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");
        
        int[] start = {1, 0};
        int[] end = { grid.getWidth()-2, grid.getHeight()-1 };
        return longestRoute(grid, start, end) + "";
    }
    
    /**
     * What is the longest route, if you ignore <,>,^,v?
     */
    @Override
    public String run2(String input) {
        input = input.replaceAll("<", ".");
        input = input.replaceAll(">", ".");
        input = input.replaceAll("\\^", ".");
        input = input.replaceAll("v", ".");
        
        Grid<String> grid = new Grid<String>(input, false);
        grid.setDivider("");

        buildNodeNetwork(grid);
        Node startNode = map.get("1,0");
        Node endNode = map.get((grid.getWidth()-2) + "," + (grid.getHeight()-1));
        
        ArrayList<Node> visited = new ArrayList<Node>();
        visited.add(startNode);
        
        return longestRoute(startNode, visited, 0, endNode) + "";
    }
    
    /**
     * @param node
     * @param visited
     * @param rounds
     * @param endNode
     * @return rounds for longest route in a node network
     */
    private Integer longestRoute(Node node, ArrayList<Node> visited, int rounds, Node endNode) {
        if (node.getName().equals(endNode.getName())) {
            return rounds;
        }
        
        ArrayList<Node> newOptions = new ArrayList<Node>();
        
        ArrayList<Node> options = node.getLinks();
        for (Node n : options) {
            if (!lstContains(visited, n)) {
                newOptions.add(n);
            }
        }
        visited.add(node);
        
        if (newOptions.isEmpty()) {
            return null;
        }
        
        Integer max = null;
        
        for (Node n : newOptions) {
            max = max(max, longestRoute(n, deepCopy(visited), rounds + node.getLinkCost(n), endNode));
        }
        
        return max;
    }
    
    /**
     * Builds a network of nodes given a grid and put the nodes in the map
     * @param grid
     */
    private void buildNodeNetwork(Grid<String> grid) {
        int[] start = {1, 0};
        int[] end = { grid.getWidth()-2, grid.getHeight()-1 };
        
        Node startNode = new Node("1,0");
        map.put(start[0] + "," + start[1], startNode);
        
        buildNodeNetworkRecur(grid, start[0], start[1], end, 0, startNode, new ArrayList<int[]>());
    }

    /**
     * Builds a network of nodes given a grid and put the nodes in the map
     * @param grid
     * @param x
     * @param y
     * @param end
     * @param rounds
     * @param node
     * @param visited
     */
    private void buildNodeNetworkRecur(Grid<String> grid, int x, int y, int[] end, int rounds, Node node, ArrayList<int[]> visited) {
        String value = grid.safeGet(x, y);
        if (value == null || value.equals("#")) {
            return;
        }
        
        if (x == end[0] && y == end[1]) {
            node = addNode(x, y, node, rounds);
            rounds = 0;
            grid.set(x, y, "N");
        } else if(y != 0 && y != grid.getHeight()-1) {
            String l = grid.safeGet(x-1, y);
            String r = grid.safeGet(x+1, y);
            String u = grid.safeGet(x, y-1);
            String d = grid.safeGet(x, y+1);
            
            int left = (l != null && !l.equals("#")) ? 1 : 0;
            int right = (r != null && !r.equals("#")) ? 1 : 0;
            int up = (u != null && !u.equals("#")) ? 1 : 0;
            int down = (d != null && !d.equals("#")) ? 1 : 0;
            
            if (2 < left + right + up + down) {
                node = addNode(x, y, node, rounds);
                rounds = 0;
                grid.set(x, y, "N");
            }
        }
        
        for (int[] v : visited) {
            if (v[0] == x && v[1] == y) {
                return;
            }
        }
        visited.add(new int[] { x, y });
        
        buildNodeNetworkRecur(grid, x-1, y, end, rounds+1, node, visited);
        buildNodeNetworkRecur(grid, x+1, y, end, rounds+1, node, visited);
        buildNodeNetworkRecur(grid, x, y-1, end, rounds+1, node, visited);
        buildNodeNetworkRecur(grid, x, y+1, end, rounds+1, node, visited);
    }
    
    /**
     * Adds node to the map
     * @param x
     * @param y
     * @param node
     * @param rounds
     * @return node
     */
    private Node addNode(int x, int y, Node node, int rounds) {
        if (node.getName().equals(x + "," + y)) {
            return node;
        }
        
        Node mapNode = map.get(x + "," + y);
        if (mapNode == null) {
            Node newNode = new Node(x + "," + y);
            node.addLinkCost(newNode, rounds);
            newNode.addLinkCost(node, rounds);
            map.put(x + "," + y, newNode);
            return newNode;
        } else {
            node.addLinkCost(mapNode, rounds);
            mapNode.addLinkCost(node, rounds);
            return mapNode;
        }
    }
    
    /**
     * @param node
     * @param visited
     * @return true if node is in the lst
     */
    private boolean lstContains(ArrayList<Node> lst, Node node) {
        for (Node v : lst) {
            if (v.getName().equals(node.getName())) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * @param grid
     * @param start
     * @param end
     * @return longest route
     */
    private Integer longestRoute(Grid<String> grid, int[] start, int[] end) {
        return longestRouteRecur(grid, start[0], start[1], end, 0, new ArrayList<int[]>());
    }
    
    /**
     * @param grid
     * @param x
     * @param y
     * @param end
     * @param rounds
     * @param visited
     * @return longest route
     */
    private Integer longestRouteRecur(Grid<String> grid, int x, int y, int[] end, int rounds, ArrayList<int[]> visited) {
        if (x == end[0] && y == end[1]) {
            return rounds;
        }
        
        String value = grid.safeGet(x, y);
        if (value == null || value.equals("#")) {
            return null;
        }
        
        for (int[] v : visited) {
            if (v[0] == x && v[1] == y) {
                return null;
            }
        }
        
        visited.add(new int[] { x, y });
        
        if (value.equals("^")) {
            return longestRouteRecur(grid, x, y-1, end, rounds+1, deepCopy(visited));
        }

        if (value.equals("v")) {
            return longestRouteRecur(grid, x, y+1, end, rounds+1, deepCopy(visited));
        }
        
        if (value.equals("<")) {
            return longestRouteRecur(grid, x-1, y, end, rounds+1, deepCopy(visited));
        }
        
        if (value.equals(">")) {
            return longestRouteRecur(grid, x+1, y, end, rounds+1, deepCopy(visited));
        }

        Integer l = longestRouteRecur(grid, x-1, y  , end, rounds+1, deepCopy(visited));
        Integer r = longestRouteRecur(grid, x+1, y  , end, rounds+1, deepCopy(visited));
        Integer u = longestRouteRecur(grid, x  , y-1, end, rounds+1, deepCopy(visited));
        Integer d = longestRouteRecur(grid, x  , y+1, end, rounds+1, deepCopy(visited));
        
        return max(max(l, r), max(u, d));
    }

    /**
     * @param <T>
     * @param lst
     * @return copy of list
     */
    private <T> ArrayList<T> deepCopy(ArrayList<T> lst) {
        ArrayList<T> copy = new ArrayList<T>();
        
        for (T t : lst) {
            copy.add(t);
        }
        
        return copy;
    }
    
    /**
     * @param a
     * @param b
     * @return max of a and b
     */
    private Integer max(Integer a, Integer b) {
        if (a == null) {
            return b;
        }
        
        if (b == null) {
            return a;
        }
        
        return Math.max(a, b);
    }
}
