package advent_of_code.years.year2023.day05;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.math.big.BigInt;

public class Day05 extends RootDay {
    public Day05(Year year, int day) {
        super(year, day, "993500720", "4917124");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        SeedCollection seedCollection = mapInput(input);
        
        ArrayList<BigInt[]> seeds = toNoRange(seedCollection.getSeeds());
        ArrayList<BigInt[]> soils = seedCollection.transformRange(seeds, seedCollection.getSeedToSoil());
        ArrayList<BigInt[]> fertilizer = seedCollection.transformRange(soils, seedCollection.getSoilToFertilizer());
        ArrayList<BigInt[]> water = seedCollection.transformRange(fertilizer, seedCollection.getFertilizerToWater());
        ArrayList<BigInt[]> light = seedCollection.transformRange(water, seedCollection.getWaterToLight());
        ArrayList<BigInt[]> temp = seedCollection.transformRange(light, seedCollection.getLightToTemp());
        ArrayList<BigInt[]> humidity = seedCollection.transformRange(temp, seedCollection.getTempToHumidity());
        ArrayList<BigInt[]> location = seedCollection.transformRange(humidity, seedCollection.getHumidityToLocation());
        
        return getMin(location) + "";
    }

    @Override
    public String run2(String input) {
        SeedCollection seedCollection = mapInput(input);
        
        ArrayList<BigInt[]> seeds = toRange(seedCollection.getSeeds());
        ArrayList<BigInt[]> soils = seedCollection.transformRange(seeds, seedCollection.getSeedToSoil());
        ArrayList<BigInt[]> fertilizer = seedCollection.transformRange(soils, seedCollection.getSoilToFertilizer());
        ArrayList<BigInt[]> water = seedCollection.transformRange(fertilizer, seedCollection.getFertilizerToWater());
        ArrayList<BigInt[]> light = seedCollection.transformRange(water, seedCollection.getWaterToLight());
        ArrayList<BigInt[]> temp = seedCollection.transformRange(light, seedCollection.getLightToTemp());
        ArrayList<BigInt[]> humidity = seedCollection.transformRange(temp, seedCollection.getTempToHumidity());
        ArrayList<BigInt[]> location = seedCollection.transformRange(humidity, seedCollection.getHumidityToLocation());
        
        return getMin(location) + "";

    }

    private BigInt getMin(ArrayList<BigInt[]> bigInts) {
        BigInt min = null;
        
        for (BigInt[] i : bigInts) {
            if (min == null) {
                min = i[0];
            } else {
                min = BigInt.min(min, i[0]);
            }
        }
        
        return min;
    }
    
    private ArrayList<BigInt[]> toRange(BigInt[] bigInts) {
        ArrayList<BigInt[]> ranges = new ArrayList<BigInt[]>();
        
        for (int i=0; i<bigInts.length/2; i++) {
            int index = i*2;
            int nextIndex = index + 1;
            
            ranges.add(new BigInt[] { bigInts[index], bigInts[index].add(bigInts[nextIndex].sub(1)) });
        }
        
        return ranges;
    }
    
    private ArrayList<BigInt[]> toNoRange(BigInt[] seeds) {
        ArrayList<BigInt[]> bigInts = new ArrayList<BigInt[]>();
        
        for (int i=0; i<seeds.length; i++) {
            bigInts.add(new BigInt[] { seeds[i], seeds[i] });
        }
        
        return bigInts;
    }
    
    private SeedCollection mapInput(String input) {
        SeedCollection seedCollection = new SeedCollection();
        
        String[] lines = input.split("\n");
        String[] seeds = lines[0].split("seeds: ")[1].split(" ");
        seedCollection.insertSeeds(seeds);
        
        lines = input.split("\n\n");
        seedCollection.insertSeedToSoil(lines[1]);
        seedCollection.insertSoilToFertilizer(lines[2]);
        seedCollection.insertFertilizerToWater(lines[3]);
        seedCollection.insertWaterToLight(lines[4]);
        seedCollection.insertLightToTemp(lines[5]);
        seedCollection.insertTempToHumidity(lines[6]);
        seedCollection.insertHumidityToLocation(lines[7]);
        
        return seedCollection;
    }
}
