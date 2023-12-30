package advent_of_code.years.year2023.day05;

import java.util.ArrayList;

import advent_of_code.utils.Interval;
import advent_of_code.utils.Log;
import advent_of_code.utils.math.big.BigInt;

public class SeedCollection {
    private String[] seeds;
    private String[][] seedToSoil;
    private String[][] soilToFertilizer;
    private String[][] fertilizerToWater;
    private String[][] waterToLight;
    private String[][] lightToTemp;
    private String[][] tempToHumidity;
    private String[][] humidityToLocation;
    
    public static void printBigs(ArrayList<BigInt[]> bi) {
        for (BigInt[] i : bi) {
            Log.show(i);
        }
    }
    
    public ArrayList<BigInt[]> transformRange(ArrayList<BigInt[]> ranges, ArrayList<BigInt[]> maps) {
        ArrayList<BigInt[]> results = new ArrayList<>();
        
        for (BigInt[] range : ranges) {
            results.addAll(applyMap(range, maps));
        }
        
        return results;
    }
    
    public ArrayList<BigInt[]> applyMap(BigInt[] range0, ArrayList<BigInt[]> maps) {
        ArrayList<BigInt[]> newReturnInts = new ArrayList<>();
        
        for (BigInt[] map : maps) {
            //if the map apply to zero elements then continue
            if (BigInt.eq(map[2], BigInt.ZERO)) {
                continue;
            }
            
            BigInt[] range1 = getIntervalFromMap(map);
            BigInt[] overlap = Interval.getOverlap(range0, range1);
            
            if (overlap != null) {
                ArrayList<BigInt[]> cuts = Interval.cut(range0, overlap);
                for (BigInt[] cut : cuts) {
                    newReturnInts.addAll(applyMap(cut, maps));
                }
                
                //apply changes
                BigInt jump = map[0].sub(map[1]);
                overlap[0] = overlap[0].add(jump);
                overlap[1] = overlap[1].add(jump);
                
                newReturnInts.add(overlap);
                return newReturnInts;
            }
        }
        
        newReturnInts.add(range0);
        return newReturnInts;
    }
    
    private BigInt[] getIntervalFromMap(BigInt[] map) {
        BigInt[] range = new BigInt[2];
        range[0] = map[1];
        range[1] = map[1].add(map[2]).sub(1);
        
        if (!range[0].leq(range[1])) {
            throw new RuntimeException("Invalid range: " + range[0] + " " + range[1] + " - map: " + map[0] + "," + map[1] + "," + map[2]);
        }
        return range;
    }

    public void insertSeeds(String[] seeds) {
        this.seeds = seeds;
    }

    public void insertSeedToSoil(String input) {
        seedToSoil = trimString(input, "seed-to-soil map:");
    }

    public void insertSoilToFertilizer(String input) {
        soilToFertilizer = trimString(input, "soil-to-fertilizer map:");
    }

    public void insertFertilizerToWater(String input) {
        fertilizerToWater = trimString(input, "fertilizer-to-water map:");
    }

    public void insertWaterToLight(String input) {
        waterToLight = trimString(input, "water-to-light map:");
    }

    public void insertLightToTemp(String input) {
        lightToTemp = trimString(input, "light-to-temperature map:");
    }

    public void insertTempToHumidity(String input) {
        tempToHumidity = trimString(input, "temperature-to-humidity map:");
    }

    public void insertHumidityToLocation(String input) {
        humidityToLocation= trimString(input, "humidity-to-location map:");
    }

    private String[][] trimString(String input, String remove) {
        String[] flat = input.replaceAll(remove, "")
                             .replaceAll("\n", " ")
                             .replaceAll("^( )", "")
                             .trim()
                             .split(" ");
        
        int length = (flat.length) / 3;
        String[][] re = new String[length][];
        
        for (int i=0; i<length; i++) {
            String[] s = new String[3];
            for (int j=0; j<3; j++) {
                s[j] = flat[i * 3 + j];
            }
            re[i] = s;
        }
        return re;
    }

    public BigInt[] getSeeds() {
        BigInt[] bigInts = new BigInt[seeds.length];
        
        for (int i=0; i<seeds.length; i++) {
            bigInts[i] = new BigInt(seeds[i]);
        }

        return bigInts;
    }

    private ArrayList<BigInt[]> getBigInts(String[][] strings) {
        ArrayList<BigInt[]> bigInts = new ArrayList<BigInt[]>();
        
        for (int i=0; i<strings.length; i++) {
            BigInt[] bigInt = new BigInt[strings[i].length];
            for (int j=0; j<strings[i].length; j++) {
                bigInt[j] = new BigInt(strings[i][j]);
            }
            bigInts.add(bigInt);
        }
        
        return bigInts;
    }
    
    public ArrayList<BigInt[]> getSeedToSoil() {
        return getBigInts(seedToSoil);
    }
    
    public ArrayList<BigInt[]> getSoilToFertilizer() {
        return getBigInts(soilToFertilizer);
    }
    
    public ArrayList<BigInt[]> getFertilizerToWater() {
        return getBigInts(fertilizerToWater);
    }
    
    public ArrayList<BigInt[]> getWaterToLight() {
        return getBigInts(waterToLight);
    }
    
    public ArrayList<BigInt[]> getLightToTemp() {
        return getBigInts(lightToTemp);
    }
    
    public ArrayList<BigInt[]> getTempToHumidity() {
        return getBigInts(tempToHumidity);
    }

    public ArrayList<BigInt[]> getHumidityToLocation() {
        return getBigInts(humidityToLocation);
    }
}
