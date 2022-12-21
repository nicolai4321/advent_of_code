package advent_of_code.year2022.day20;

public class Value {
	private int originalIndex;
	private int value;
	
	public Value(int originalIndex, int value) {
		this.originalIndex = originalIndex;
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getOriginalIndex() {
		return originalIndex;
	}
}
