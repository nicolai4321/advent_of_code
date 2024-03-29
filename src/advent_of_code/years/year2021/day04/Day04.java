package advent_of_code.years.year2021.day04;

import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Text;

public class Day04 extends RootDay {
    public Day04(Year year, int day) {
        super(year, day, "11774", "4495");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    protected String run1(String input) {
        int[] numbers = getNumbers(input);
        ArrayList<int[][]> boards = getBoards(input);
        Integer sum = getWinningBoard(numbers, boards);
        
        return sum + "";
    }

    @Override
    protected String run2(String input) {
        int[] numbers = getNumbers(input);
        ArrayList<int[][]> boards = getBoards(input);
        Integer sum = getLosingBoard(numbers, boards);
        
        return sum + "";
    }
	
	private int[] getNumbers(String input) {
		String numbersInString = input.split("\n")[0];
		String[] strings = numbersInString.split(",");
		int[] numbers = new int[strings.length];
		for (int i=0; i<strings.length; i++) {
			numbers[i] = Integer.parseInt(strings[i]);
		}
		return numbers;
	}
	
	private ArrayList<int[][]> getBoards(String input) {
		ArrayList<int[][]> boards = new ArrayList<int[][]>();
		int index = -1;
		int row = 0;
		
		String[] lines = input.split("\n");
		for (int i=1; i<lines.length; i++) {
			String line = lines[i];
			
			if (Text.isBlank(line)) {
				index++;
				row = 0;
				boards.add(new int[5][5]);
			} else {
				int[][] board = boards.get(index);
				for (int j=0; j<5; j++) {
					String number = line.replaceAll("( )+", " ").replaceAll("^( )*", "").replaceAll("( )*$", "").split(" ")[j];
					board[row][j] = Integer.parseInt(number);
				}
				row++;
			}
		}
		
		return boards;
	}
	
	private Integer getLosingBoard(int[] numbers, ArrayList<int[][]> boards) {
		Integer losingSum = null;
		
		for (int maxIndex=4; maxIndex<numbers.length; maxIndex++) {
			int[] partNumbers = new int[maxIndex + 1];
			for (int j=0; j<maxIndex + 1; j++) {
				partNumbers[j] = numbers[j];
			}
			
			ArrayList<Integer[]> winners = getWinningBoards(partNumbers, boards);
			ArrayList<Integer> boardsToRemove = new ArrayList<Integer>();
			for (Integer[] result : winners) {
				Integer boardIndex = result[0];
				Integer sum = result[1];
				if (boardIndex != null) {
					losingSum = sum;
					boardsToRemove.add(boardIndex);
					if (boards.isEmpty()) {
						return sum;
					}
				}
			}
			
			Collections.sort(boardsToRemove, Collections.reverseOrder());
			for (int i : boardsToRemove) {
				boards.remove(i);
			}
		}
		
		return losingSum;
	}
	
	private Integer getWinningBoard(int[] numbers, ArrayList<int[][]> boards) {
		for (int maxIndex=4; maxIndex<numbers.length; maxIndex++) {
			int[] partNumbers = new int[maxIndex + 1];
			for (int j=0; j<maxIndex + 1; j++) {
				partNumbers[j] = numbers[j];
			}
			
			ArrayList<Integer[]> winners = getWinningBoards(partNumbers, boards);
			if (!winners.isEmpty()) {
				Integer sum = winners.get(0)[1];
				if (sum != null) {
					return sum;
				}				
			}
		}
		
		return null;
	}
	
	private ArrayList<Integer[]> getWinningBoards(int[] partNumbers, ArrayList<int[][]> boards) {
		ArrayList<Integer[]> winners = new ArrayList<Integer[]>();
		int lastNr = partNumbers[partNumbers.length -1];
		for (int i=0; i<boards.size(); i++) {
			int[][] board = boards.get(i);
			
			if (fiveInRow(partNumbers, board) || fiveInCol(partNumbers, board)) {
				winners.add(new Integer[] {i, sumBoard(partNumbers, board) * lastNr});
			}
		}
		return winners;
	}
	
	private int sumBoard(int[] partNumbers, int[][] board) {
		int sum = 0;
		for (int[] row : board) {
			for (int i : row) {
				if (!contain(i, partNumbers)) {
					sum += i;
				}
			}
		}
		return sum;
	}
	
	private boolean fiveInRow(int[] numbers, int[][] board) {
		for (int i=0; i<board.length; i++) {
			int[] row = board[i];
			boolean success = true;
			for (int nr : row) {
				if (!contain(nr, numbers)) {
					success = false;
				}
			}
			if (success) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean fiveInCol(int[] numbers, int[][] board) {
		for (int c=0; c<5; c++) {
			boolean success = true;
			for (int r=0; r<5; r++) {
				int nr = board[r][c];
				if (!contain(nr, numbers)) {
					success = false;
				}
			}
			if (success) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean contain(int nr, int[] lst) {
		for (int i : lst) {
			if (i == nr) {
				return true;
			}
		}
		return false;
	}
}
