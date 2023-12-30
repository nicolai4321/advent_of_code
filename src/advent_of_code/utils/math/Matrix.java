package advent_of_code.utils.math;

public class Matrix<T> {
	private int nrRows;
	private int nrColumns;
	private T[][] matrix;
	
	@SuppressWarnings("unchecked")
	public Matrix(int nrRows, int nrColumns) {
		this.nrRows = nrRows;
		this.nrColumns = nrColumns;
		matrix = (T[][]) new Object[nrRows][nrColumns];
	}
	
	/**
	 * Insert value at row, col
	 * @param row
	 * @param col
	 * @param value
	 */
	public void insert(int row, int col, T value) {
		matrix[row][col] = value;
	}
	
	/**
	 * @param row
	 * @param col
	 * @return value at row, col
	 */
	public T get(int row, int col) {
		return matrix[row][col];
	}
	
	/**
	 * Inserts the value on every index
	 * @param value
	 */
	public void insert(T value) {
		for (int row=0; row<nrRows; row++) {
			for (int col=0; col<nrColumns; col++) {
				matrix[row][col] = value;
			}
		}
	}

	@Override
	public String toString() {
		String matrixString = "";
		
		for (T[] row : matrix) {
			for (T value : row) {
				if (value == null) {
					matrixString += "   ";
				} else {
					int length = (value + "").length();
					
					if (length == 0) {
						matrixString += "   ";
					} else if (length == 1) {
						matrixString += " " + value + " ";
					} else {
						matrixString += value + " ";
					}
				}
			}
			matrixString += "\n";
		}
		
		return matrixString;
	}
}
