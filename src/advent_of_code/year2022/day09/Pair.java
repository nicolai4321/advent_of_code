package advent_of_code.year2022.day09;

public class Pair {
	private int x;
	private int y;
	
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void addX(int v) {
		x += v;
	}
	
	public void addY(int v) {
		y += v;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
