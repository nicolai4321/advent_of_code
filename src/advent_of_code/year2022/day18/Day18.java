package advent_of_code.year2022.day18;

import java.util.ArrayList;

import advent_of_code.utils.Lists;
import advent_of_code.utils.Log;
import advent_of_code.utils.RootDay;

public class Day18 extends RootDay {
    public Day18() {
        super(2022, 18, "4418", "2486");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        ArrayList<Cube> cubes = mapToCubes(input);
        coverCubes(cubes);
        return countAllFreeSides(cubes) + "";
    }

    @Override
    public String run2(String input) {
        ArrayList<Cube> cubes = mapToCubes(input);        
        addTrappedAirCubes(cubes);
        coverCubes(cubes);
        return countAllFreeSides(cubes) + "";
    }

    /**
     * This method finds trapped air cubes and replaces them with regular cubes such that no trapped air exists
     */
    private void addTrappedAirCubes(ArrayList<Cube> cubes) {
        //Find coordinate boundaries
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
                
        for (Cube cube : cubes) {
            minX = (cube.getX() < minX) ? cube.getX() : minX;
            minY = (cube.getY() < minY) ? cube.getY() : minY;
            minZ = (cube.getZ() < minZ) ? cube.getZ() : minZ;
            maxX = (maxX < cube.getX()) ? cube.getX() : maxX;
            maxY = (maxY < cube.getY()) ? cube.getY() : maxY;
            maxZ = (maxZ < cube.getZ()) ? cube.getZ() : maxZ;
        }
        
        //Find air cubes
        ArrayList<Cube> airCubes = new ArrayList<Cube>();
        for (int x=minX; x<=maxX; x++) {
            Log.show("(" + x + "/" + maxX + ")");
            for (int y=minY; y<=maxY; y++) {
                for (int z=minZ; z<=maxZ; z++) {
                    Cube potentialAirCube = new Cube(x, y, z);
                    for (Cube cube : cubes) {
                        if (potentialAirCube.samePosition(cube)) {
                            potentialAirCube = null;
                            break;
                        }
                    }
                    
                    if (potentialAirCube != null && !canEscape(cubes, new ArrayList<Cube>(), potentialAirCube, minX, maxX, minY, maxY, minZ, maxZ)) {
                        airCubes.add(potentialAirCube);
                    }
                }                
            }
        }
        
        Log.show("Found " + airCubes.size() + " air cube(s)");
        
        for (Cube cube : airCubes) {
            cubes.add(cube);
        }
    }
    
    /**
     * If a coordinate is an air cube, it should not be possible to find a path from the cube to the void.
     * 
     * @param cubes
     * @param visited
     * @param cubeStart
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @param minZ
     * @param maxZ
     * @return true if the cube can escape outside the boundaries
     */
    private boolean canEscape(ArrayList<Cube> cubes, ArrayList<Cube> visited, Cube cubeStart, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        if (cubeStart.getX() < minX || maxX < cubeStart.getX()) {
            return true;
        }
        
        if (cubeStart.getY() < minY || maxY < cubeStart.getY()) {
            return true;
        }
        
        if (cubeStart.getZ() < minZ || maxZ < cubeStart.getZ()) {
            return true;
        }
        
        for (Cube c : cubes) {
            if (c.samePosition(cubeStart)) {
                return false;
            }
        }
        
        for (Cube c : visited) {
            if (c.samePosition(cubeStart)) {
                return false;
            }
        }
        
        visited.add(cubeStart);
        if (canEscape(cubes, visited, new Cube(cubeStart.getX() + 1, cubeStart.getY(), cubeStart.getZ()), minX, maxX, minY, maxY, minZ, maxZ)) {
            return true;
        }
        if (canEscape(cubes, visited, new Cube(cubeStart.getX() - 1, cubeStart.getY(), cubeStart.getZ()), minX, maxX, minY, maxY, minZ, maxZ)) {
            return true;
        }
        if (canEscape(cubes, visited, new Cube(cubeStart.getX(), cubeStart.getY() + 1, cubeStart.getZ()), minX, maxX, minY, maxY, minZ, maxZ)) {
            return true;
        }
        if (canEscape(cubes, visited, new Cube(cubeStart.getX(), cubeStart.getY() - 1, cubeStart.getZ()), minX, maxX, minY, maxY, minZ, maxZ)) {
            return true;
        }
        if (canEscape(cubes, visited, new Cube(cubeStart.getX(), cubeStart.getY(), cubeStart.getZ() + 1), minX, maxX, minY, maxY, minZ, maxZ)) {
            return true;
        }
        if (canEscape(cubes, visited, new Cube(cubeStart.getX(), cubeStart.getY(), cubeStart.getZ() - 1), minX, maxX, minY, maxY, minZ, maxZ)) {
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Checks every cube and covers the sides that are touching other cubes
     * @param cubes
     */
    private void coverCubes(ArrayList<Cube> cubes) {
        for (int i=0; i<cubes.size(); i++) {
            for (int j=i+1; j<cubes.size(); j++) {
                if (i != j) {
                    Cube cubeI = cubes.get(i);
                    Cube cubeJ = cubes.get(j);
                    
                    if (cubeI.isColliding(cubeJ)) {
                        int collisionSideI = cubeI.getCollideSide(cubeJ);
                        cubeI.coverSide(collisionSideI);
                        int collisionSideJ = cubeJ.getCollideSide(cubeI);
                        cubeJ.coverSide(collisionSideJ);
                    }
                }
            }
        }
    }
    
    /**
     * @param cubes
     * @return number of free sides
     */
    private int countAllFreeSides(ArrayList<Cube> cubes) {
        int sum = 0;
        for (Cube c : cubes) {
            sum += c.countFreeSides();
        }
        return sum;
    }
    
    /**
     * @param input
     * @return a list of cubes
     */
    private ArrayList<Cube> mapToCubes(String input) {
        ArrayList<Cube> cubes = new ArrayList<Cube>();
        for (String line : input.split("\n")) {
            String[] coords = line.split(",");
            int[] ints = Lists.stringsToInts(coords);
            cubes.add(new Cube(ints[0], ints[1], ints[2]));
        }
        
        return cubes;
    }
}
