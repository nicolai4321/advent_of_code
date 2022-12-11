package advent_of_code.year2021.day08;

public class Display {
	private String display;
	private int value;
	
	public Display(String display) {
		this.display = display;
	}
	
	public Display(String display, int value) {
		this.display = display;
		this.value = value;
	}
	
	public boolean contains(Display d) {
		for (char c : d.getCodes()) {
			if (!display.contains(c+"")) {
				return false;
			}
		}
		return true;
	}
	
	public char[] getCodes() {
		return display.toCharArray();
	}
	
	public String getCode() {
		return display;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public int shares(String code) {
		int shares = 0;
		for (char c : code.toCharArray()) {
			if (display.contains(c+"")) {
				shares++;
			}
		}
		return shares;
	}

	public boolean eq(String code) {
		return shares(code) == display.length() && shares(code) == code.length();
	}

	public String getDisplay() {
		return display;
	}
}
