package advent_of_code.year2022.day08;

import advent_of_code.utils.Lists;
import advent_of_code.utils.RootDay;

public class Day08 extends RootDay {
    public Day08() {
        super(2022, 8, "1827", "335580");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        int[][] trees = Lists.getStaticToIntss(input, true);        
        boolean[][] visibleTrees = getVisibleTrees(trees);
        return sumVisibleTrees(visibleTrees) + "";
    }

    @Override
    public String run2(String input) {
        int[][] trees = Lists.getStaticToIntss(input, true);        
        return getBestView(trees) + "";
    }
    
    /**
     * @param trees
     * @return best view
     */
    private int getBestView(int[][] trees) {
        int rowLength = trees[0].length;
        int bestScore = 0;
        
        for (int r=0; r<rowLength; r++) {
            for (int c=0; c<trees.length; c++) {
                int value = getView(r, c, trees);
                if (bestScore < value) {
                    bestScore = value;
                }
            }
        }
        return bestScore;
    }

    /**
     * @param x
     * @param y
     * @param trees
     * @return view
     */
    private int getView(int x, int y, int[][] trees) {
        int height = trees[x][y];

        //left
        int left = 0;
        for (int l=x-1; l>=0; l--) {
            left++;
            if (height <= trees[l][y]) {
                break;
            }
        }
        
        //right
        int right = 0;
        for (int r=x+1; r<trees.length; r++) {
            right++;
            if (height <= trees[r][y]) {
                break;
            }
        }
        
        //up
        int up = 0;
        for (int u=y-1; u>=0; u--) {
            up++;
            if (height <= trees[x][u]) {
                break;
            }
        }
        
        //down
        int down = 0;
        for (int d=y+1; d<trees[0].length; d++) {
            down++;
            if (height <= trees[x][d]) {
                break;
            }
        }
        
        return left * right * up * down;
    }

    /**
     * @param visibles
     * @return the sum of visible trees
     */
    private int sumVisibleTrees(boolean[][] visibleTrees) {
        int sum = 0;
        for (boolean[] bools : visibleTrees) {
            for (boolean b : bools) {
                if (b) {
                    sum++;                    
                }
            }
        }
        return sum;
    }
    
    /**
     * @param trees
     * @return a matrx over visible trees
     */
    private boolean[][] getVisibleTrees(int[][] trees) {
        int rowLength = trees[0].length;
        boolean[][] visibleTrees = new boolean[trees.length][rowLength];
        
        for (int r=0; r<rowLength; r++) {
            for (int c=0; c<trees.length; c++) {
                visibleTrees[r][c] = false; 
                
                if (isTreeVisible(r, c, trees, Dir.Left)) {
                    visibleTrees[r][c] = true; 
                } if (isTreeVisible(r, c, trees, Dir.Right)) {
                    visibleTrees[r][c] = true; 
                } if (isTreeVisible(r, c, trees, Dir.Up)) {
                    visibleTrees[r][c] = true; 
                } if (isTreeVisible(r, c, trees, Dir.Down)) {
                    visibleTrees[r][c] = true; 
                }
            }
        }
        
        return visibleTrees;
    }
    
    /**
     * @param x
     * @param y
     * @param trees
     * @param dir
     * @param rowLength
     * @return true if the tree is visible in the given direction
     */
    private static boolean isTreeVisible(int r, int c, int[][] trees, Dir direction) {
        int rowLength = trees[0].length;
        int height = trees[r][c];

        if (direction == Dir.Left) {
            for (int d=r-1; d>=0; d--) {
                int currentHeight = trees[d][c];
                if (height <= currentHeight) {
                    return false;
                }
            }
        }
        
        if (direction == Dir.Right) {
            for (int d=r+1; d<trees.length; d++) {
                int currentHeight = trees[d][c];
                if (height <= currentHeight) {
                    return false;
                }
            }
        }
        
        if (direction == Dir.Up) {
            for (int d=c-1; d>=0; d--) {
                int currentHeight = trees[r][d];
                if (height <= currentHeight) {
                    return false;
                }
            }
        }
        
        if (direction == Dir.Down) {
            for (int d=c+1; d<rowLength; d++) {
                int currentHeight = trees[r][d];
                if (height <= currentHeight) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
