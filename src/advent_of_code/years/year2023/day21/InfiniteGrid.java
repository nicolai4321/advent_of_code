package advent_of_code.years.year2023.day21;

import java.util.ArrayList;

import advent_of_code.utils.Grid;
import advent_of_code.utils.math.big.BigInt;

public class InfiniteGrid {
    /**
     * Given an infinite grid and a number of steps, this method will return
     * the amount of possible plots
     * 
     * @param grid
     * @param steps
     * @return possible plots
     */
    public static BigInt moveGardener(Grid<String> grid, int steps) {
        int cellsWidth = (steps - (grid.getWidth() - 1)/2) / grid.getWidth();
        
        BigInt sum = getFullCellSteps(grid, cellsWidth, steps);
        sum = sum.add(getPartCellsEnd(grid));
        sum = sum.add(getPartCellsSmallDiagonal(grid, cellsWidth));
        sum = sum.add(getPartCellsBigDiagonal(grid, cellsWidth));
        
        return sum;
    }
    
    /**
     * @param grid
     * @param cellsWidth
     * @param steps
     * @return steps of the full covered cells
     */
    private static BigInt getFullCellSteps(Grid<String> grid, int cellsWidth, int steps) {
        BigInt sum = BigInt.ZERO;
        int[] fullyCoveredCell = getFullyCoveredCell(grid, Day21.find(grid, "S"));
        int evenIndex = steps % 2;
        
        for (int i=(cellsWidth-1); 0<=i; i--) {
            //center cells
            if (i == (cellsWidth-1)) {
                sum = sum.add(fullyCoveredCell[evenIndex]);
            } else {
                sum = sum.add(BigInt.mult(2, fullyCoveredCell[evenIndex]));
            }

            //additional cells (excluding the center cell)
            int least = (int) Math.floor(i / 2);
            int most = (int) Math.ceil((double) i / 2);
            
            if (i == (cellsWidth-1)) {
                sum = sum.add(BigInt.add(BigInt.mult(2 * least, fullyCoveredCell[evenIndex]), BigInt.mult(2 * most, fullyCoveredCell[(evenIndex + 1) % 2])));
            } else {
                sum = sum.add(BigInt.add(BigInt.mult(4 * least, fullyCoveredCell[evenIndex]), BigInt.mult(4 * most, fullyCoveredCell[(evenIndex + 1) % 2])));
            }
            
            evenIndex = (evenIndex + 1) % 2;
        }
        
        return sum;
    }
    
    /**
     * @param grid
     * @return the steps for the end center part cells
     */
    private static BigInt getPartCellsEnd(Grid<String> grid) {
        BigInt sum = BigInt.ZERO;
        
        int steps = grid.getWidth()-1;

        sum = sum.add(Day21.moveGardener(grid, grid.getWidth()/2, grid.getHeight()-1, steps));
        sum = sum.add(Day21.moveGardener(grid,                 0, grid.getHeight()/2, steps));
        sum = sum.add(Day21.moveGardener(grid, grid.getWidth()/2,                  0, steps));
        sum = sum.add(Day21.moveGardener(grid, grid.getWidth()-1, grid.getHeight()/2, steps));
        
        return sum;
    }
    
    /**
     * @param grid
     * @param cellsWidth
     * @return the steps for the small diagonal part cells
     */
    private static BigInt getPartCellsSmallDiagonal(Grid<String> grid, int cellsWidth) {
        BigInt sum = BigInt.ZERO;
        
        int steps = (grid.getWidth()-1) - ((grid.getWidth()-1)/2 + 1);
        int nrDiagonalCells = cellsWidth;
        
        sum = sum.add(BigInt.mult(Day21.moveGardener(grid,                 0, grid.getHeight()-1, steps), nrDiagonalCells));
        sum = sum.add(BigInt.mult(Day21.moveGardener(grid,                 0,                  0, steps), nrDiagonalCells));
        sum = sum.add(BigInt.mult(Day21.moveGardener(grid, grid.getWidth()-1,                  0, steps), nrDiagonalCells));
        sum = sum.add(BigInt.mult(Day21.moveGardener(grid, grid.getWidth()-1, grid.getHeight()-1, steps), nrDiagonalCells));
        
        return sum;
    }
    
    /**
     * @param grid
     * @param cellsWidth
     * @return the steps for the big diagonal part cells
     */
    private static BigInt getPartCellsBigDiagonal(Grid<String> grid, int cellsWidth) {
        BigInt sum = BigInt.ZERO;
        
        int steps = (grid.getWidth()-1) + grid.getWidth() - ((grid.getWidth()-1)/2 + 1);
        int nrDiagonalCells = (cellsWidth - 1);
        
        sum = sum.add(BigInt.mult(Day21.moveGardener(grid,                 0, grid.getHeight()-1, steps), nrDiagonalCells));
        sum = sum.add(BigInt.mult(Day21.moveGardener(grid,                 0,                  0, steps), nrDiagonalCells));
        sum = sum.add(BigInt.mult(Day21.moveGardener(grid, grid.getWidth()-1,                  0, steps), nrDiagonalCells));
        sum = sum.add(BigInt.mult(Day21.moveGardener(grid, grid.getWidth()-1, grid.getHeight()-1, steps), nrDiagonalCells));
        
        return sum;
    }
    
    /**
     * @param grid
     * @param start
     * @return [ fullyCoveredEven, fullyCoveredOdd ]
     */
    private static int[] getFullyCoveredCell(Grid<String> grid, int[] start) {
        ArrayList<int[]> oddPositions = new ArrayList<int[]>();
        ArrayList<int[]> evenPositions = new ArrayList<int[]>();
        evenPositions.add(start);
        
        int oddSize = oddPositions.size();
        int evenSize = evenPositions.size();
        
        while (true) {
            int newOddSize = Day21.moveGardener(grid, evenPositions, oddPositions, 1).size();
            int newEvenSize = Day21.moveGardener(grid, oddPositions, evenPositions, 1).size();
            
            if (oddSize == newOddSize && evenSize == newEvenSize) {
                break;
            } else {
                oddSize = newOddSize;
                evenSize = newEvenSize;
            }
        }
            
        return new int[] { evenPositions.size(), oddPositions.size() };
    }
}
