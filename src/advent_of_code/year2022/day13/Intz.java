package advent_of_code.year2022.day13;

public class Intz extends Item {
	private int i;
	
	public Intz(int i) {
		this.i = i;
	}

	public int getInt() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	
	@Override
	public String toString() {
		return i + "";
	}

	@Override
	public boolean isInt() {
		return true;
	}

	@Override
	public boolean isList() {
		return false;
	}
}
