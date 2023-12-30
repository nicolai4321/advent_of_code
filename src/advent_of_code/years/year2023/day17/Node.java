package advent_of_code.years.year2023.day17;

public class Node implements Comparable<Node> {
    public final int x;
    public final int y;
    public final int cost;
    public final int facing;
    public final int facingCount;
    private final int potentialCost;
    
    public Node(int x, int y, int cost, int facing, int facingCount, int potentialCost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.facing = facing;
        this.facingCount = facingCount;
        this.potentialCost = potentialCost;
    }
    
    @Override
    public boolean equals(Object object) {
        Node node = (Node) object;
        return x == node.x &&
               y == node.y &&
               facing == node.facing &&
               facingCount == node.facingCount;
    }
    
    @Override
    public int hashCode() {
        return x + 1000 * (y + 1);
    }

    @Override
    public int compareTo(Node o) {
        return potentialCost - o.potentialCost;
    }
    
    public String toString() {
        return "(" + x + "," + y + "), " + facing + " - " + facingCount + " (" + potentialCost + ")";
    }
}
