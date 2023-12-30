package advent_of_code.years.year2023.day20;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.math.big.BigInt;


public class Day20 extends RootDay {
    public Day20(Year year, int day) {
        super(year, day, "856482136", "224046542165867");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        ArrayList<TvModule> tvModules = getModules(lines);
        linkModules(tvModules);
        
        for (int i=0; i<1000; i++) {
            buttonLowPulse(tvModules, "broadcaster");
        }
        
        BigInt highPulsesSent = new BigInt(TvModule.HIGH_PULSES_SENT);
        BigInt lowPulsesSent = new BigInt(TvModule.LOW_PULSES_SENT);
        
        return highPulsesSent.mult(lowPulsesSent).toString() + "";
    }
    
    @Override
    public String run2(String input) {
        String[] lines = input.split("\n");
        ArrayList<TvModule> tvModules = getModules(lines);
        linkModules(tvModules);
        
        BigInt qn = BigInt.ZERO;
        BigInt xf = BigInt.ZERO;
        BigInt xn = BigInt.ZERO;
        BigInt zl = BigInt.ZERO;

        int i = 0;
        while (qn.eq(BigInt.ZERO) || xf.eq(BigInt.ZERO) || xn.eq(BigInt.ZERO) || zl.eq(BigInt.ZERO)) {
            i++;
            buttonLowPulse(tvModules, "broadcaster");
            
            if (qn.eq(BigInt.ZERO) && TvModule.QN) {
                qn = new BigInt(i);
            }
            
            if (xf.eq(BigInt.ZERO) && TvModule.XF) {
                xf = new BigInt(i);
            }
            
            if (xn.eq(BigInt.ZERO) && TvModule.XN) {
                xn = new BigInt(i);
            }
            
            if (zl.eq(BigInt.ZERO) && TvModule.ZL) {
                zl = new BigInt(i);
            }
        }
        
        return BigInt.lcm(BigInt.lcm(qn, xf), BigInt.lcm(xn, zl)).toString();
    }
    
    private void buttonLowPulse(ArrayList<TvModule> tvModules, String startName) {
        TvModule.LOW_PULSES_SENT++;
        
        TvModule broadcast = findTvModule(tvModules, startName);
        broadcast.sendPulse(false);
        
        ArrayList<TvModule> sentModules = new ArrayList<TvModule>();
        for (TvModule tvModule : broadcast.getTvModules()) {
            sentModules.addAll(tvModule.process());
        }
        
        if (!sentModules.isEmpty()) {
            process(sentModules);
        }
    }
    
    private void process(ArrayList<TvModule> tvModules) {
        ArrayList<TvModule> sentModules = new ArrayList<TvModule>();
        for (TvModule tvModule : tvModules) {
            sentModules.addAll(tvModule.process());
        }
        if (!sentModules.isEmpty()) {
            process(sentModules);
        }
    }

    private TvModule findTvModule(ArrayList<TvModule> tvModules, String name) {
        for (TvModule tvModule : tvModules) {
            if (name.equals(tvModule.getName())) {
                return tvModule;
            }
        }
        
        throw new RuntimeException("could not find tv module");
    }
    
    private void linkModules(ArrayList<TvModule> tvModules) {
        for (TvModule tvModule : tvModules) {
            tvModule.link(tvModules);
        }
        
        for (TvModule tvModule : tvModules) {
            tvModule.setupConnections();
        }
    }
    
    private ArrayList<TvModule> getModules(String[] lines) {
        ArrayList<TvModule> tvModules = new ArrayList<TvModule>();
        for (String line : lines) {
            String name = line.split(" -> ")[0];
            String[] links = line.split(" -> ")[1].replaceAll(" ", "").split(",");
            
            TvModule tvModule = new TvModule(name, links);
            tvModules.add(tvModule);
        }
        return tvModules;
    }
}
