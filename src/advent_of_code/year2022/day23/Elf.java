package advent_of_code.year2022.day23;

import java.util.ArrayList;

public class Elf {
    private static int NORTH = 0;
    private static int SOUTH = 1;
    private static int EAST = 2;
    private static int WEST = 3;
    private int x;
    private int y;
    private int proposeX;
    private int proposeY;
    
    public Elf(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Plan the best move for the elf
     * @param elves
     * @param prio
     */
    public void planMove(ArrayList<Elf> elves, int prefferedDireciton) {
        boolean canGoN = true;
        boolean canGoS = true;
        boolean canGoW = true;
        boolean canGoE = true;
        
        for (Elf elf : elves) {
            int elfX = elf.getX();
            int elfY = elf.getY();
            
            canGoN = (canGoN) ? !((elfX-1 == x && elfY+1 == y) || (elfX == x && elfY+1 == y) || (elfX+1 == x && elfY+1 == y)) : false;
            canGoS = (canGoS) ? !((elfX-1 == x && elfY-1 == y) || (elfX == x && elfY-1 == y) || (elfX+1 == x && elfY-1 == y)) : false;
            canGoW = (canGoW) ? !((elfX+1 == x && elfY-1 == y) || (elfX+1 == x && elfY == y) || (elfX+1 == x && elfY+1 == y)) : false;
            canGoE = (canGoE) ? !((elfX-1 == x && elfY-1 == y) || (elfX-1 == x && elfY == y) || (elfX-1 == x && elfY+1 == y)) : false;
        }      
        
        Integer bestMove = null;
        if (prefferedDireciton == NORTH) {
            bestMove = mapMove(getBestMove(canGoN, canGoS, canGoW, canGoE), NORTH);
        } else if (prefferedDireciton == SOUTH) {
            bestMove = mapMove(getBestMove(canGoS, canGoW, canGoE, canGoN), SOUTH);
        } else if (prefferedDireciton == EAST) {
            bestMove = mapMove(getBestMove(canGoW, canGoE, canGoN, canGoS), EAST);
        } else if (prefferedDireciton == WEST) {
            bestMove = mapMove(getBestMove(canGoE, canGoN, canGoS, canGoW), WEST);
        }
        
        if (bestMove == null) {
            proposeX = x;
            proposeY = y;
        } else if (bestMove == NORTH) {
            proposeX = x;
            proposeY = y - 1;
        } else if (bestMove == SOUTH) {
            proposeX = x;
            proposeY = y + 1;
        } else if (bestMove == EAST) {
            proposeX = x - 1;
            proposeY = y;
        } else if (bestMove == WEST) {
            proposeX = x + 1;
            proposeY = y;
        }
    }

    /**
     * Move the elf
     * @param elves
     * @return true if the elf could move
     */
    public boolean move(ArrayList<Elf> elves) {
        for (Elf elf : elves) {
            if (elf != this && proposeX == elf.getProposeX() && proposeY == elf.getProposeY()) {
                return false;
            }
        }
        
        int xNow = x;
        int yNow = y;
        x = proposeX;
        y = proposeY;
        return !(xNow == x && yNow == y);
    }
    
    /**
     * @param bestMove (shifted)
     * @param direction
     * @return mapped move
     */
    private Integer mapMove(Integer bestMove, int direction) {
        if (bestMove == null) {
            return null;
        }
        return (bestMove + direction) % 4;
    }
    
    /**
     * The directions N, S, W, E has different priorities depending on the position in the cycle.
     * N, S, W, E are therefore represented as priorityMove0, priorityMove1, priorityMove2, priorityMove3
     * in this method and the best option is returned
     * 
     * @param priorityMove0
     * @param priorityMove1
     * @param priorityMove2
     * @param priorityMove3
     * @return the best most (null if none)
     */
    private Integer getBestMove(boolean priorityMove0, boolean priorityMove1, boolean priorityMove2, boolean priorityMove3) {
        if (priorityMove0 && priorityMove1 && priorityMove2 && priorityMove3) {
            return null;
        } if (priorityMove0) {
            return 0;
        } else if (priorityMove1) {
            return 1;
        } else if (priorityMove2) {
            return 2;
        } else if (priorityMove3) {
            return 3;
        } else {
            return null;
        }
    }
    
    private Integer getProposeX() {
        return proposeX;
    }

    private Integer getProposeY() {
        return proposeY;
    }
        
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
