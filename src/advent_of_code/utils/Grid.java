package advent_of_code.utils;

public class Grid<T> {
	private T[][] grid;
	private boolean verticalGoesUp;
	private String divider = ",";
	private String t = "t";
	private String f = "f";
	private int shiftX = 0;
	private int shiftY = 0;
	
	@SuppressWarnings("unchecked")
	public Grid(int width, int height, boolean verticalGoesUp) {
		grid = (T[][]) new Object[height][width];
		this.verticalGoesUp = verticalGoesUp;
	}
	
	public Grid(String string, boolean verticalGoesUp) {
		String[] strings = string.split("\n");
		constructor(strings, verticalGoesUp);
	}
	
	public Grid(String[] strings, boolean verticalGoesUp) {
		constructor(strings, verticalGoesUp);
	}
	
	public void shift(int shiftX, int shiftY) {
		this.shiftX = shiftX;
		this.shiftY = shiftY;
	}

	public static Grid<Integer> toIntGrid(Grid<String> grid) {
		Grid<Integer> gridInt = new Grid<Integer>(grid.getWidth(), grid.getHeight(), grid.verticalGoesUp);
		gridInt.setDivider(grid.getDivider());
		
		int shiftX = grid.getShiftX();
		int shiftY = grid.getShiftY();
		grid.shift(0, 0);
		for (int x=0; x<grid.getWidth(); x++) {
			for (int y=0; y<grid.getHeight(); y++) {
				String value = grid.get(x, y);
				Integer i = Integer.parseInt(value);
				gridInt.set(x, y, i);
			}
		}
		
		grid.shift(shiftX, shiftY);
		gridInt.shift(shiftX, shiftY);
		return gridInt;
	}

	public void set(T value) {
		for (int x=0; x<getWidth(); x++) {
			for (int y=0; y<getHeight(); y++) {
				internalSet(x, y, value);
			}
		}
	}

	public void set(int x, int y, T value) {
		x += shiftX;
		y += shiftY;
		internalSet(x, y, value);
	}

	private void internalSet(int x, int y, T value) {
		if (verticalGoesUp) {
			grid[(grid.length - 1) - y][x] = value;
		} else {
			grid[y][x] = value;
		}
	}
	
	public T get(int x, int y) {
		x += shiftX;
		y += shiftY;
		if (verticalGoesUp) {
			return grid[(grid.length - 1) - y][x];
		} else {
			return grid[y][x];			
		}
	}
	
	public int getWidth() {
		return grid[0].length;
	}
	
	public int getHeight() {
		return grid.length;
	}
	
	public void print() {
		Log.show(toString());
	}
	
	public void setBoolInterpretation(String t, String f) {
		this.t = t;
		this.f = f;
	}
	
	public void setDivider(String divider) {
		this.divider = divider;
	}
	
	public boolean getVerticalGoesUp() {
		return verticalGoesUp;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int r=0; r<getHeight(); r++) {
			for (int c=0; c<getWidth(); c++) {
				T value = grid[r][c];
				if (value instanceof Boolean) {
					Boolean b = (Boolean) value;
					s += ((b == null) ? "" : ((b) ? t : f)) + divider; 
				} else {
					s += value + divider;						
				}
			}
			s = s.replaceAll(divider + "$", "") + "\n";
		}
		return s;
	}
	
	@SuppressWarnings("unchecked")
	private void constructor(String[] strings, boolean verticalGoesUp) {
		int width = strings[0].length();
		int height = strings.length;
		
		grid = (T[][]) new Object[height][width];
		this.verticalGoesUp = verticalGoesUp;
		
		for (int y=0; y<height; y++) {
			char[] row = strings[y].toCharArray();
			for (int x=0; x<width; x++) {
				T t = (T) (row[x] + "");
				if (verticalGoesUp) {
					set(x, (height-1) - y, t);
				} else {
					set(x, y, t);
				}
			}
		}
	}
	
	public int getShiftX() {
		return shiftX;
	}
	
	public int getShiftY() {
		return shiftY;
	}
	
	public String getDivider() {
		return divider;
	}
}
