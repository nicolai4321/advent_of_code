package advent_of_code.years.year2023.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TvModule {
    public static final String FLIP_FLOP = "%";
    public static final String CONJUNCTION = "&";
    public static final String BROADCASTER = "b";
    public static final boolean HIGH_PULSE = true;
    public static final boolean LOW_PULSE = false;
    public static int LOW_PULSES_SENT = 0;
    public static int HIGH_PULSES_SENT = 0;
    
    public static boolean ZL = false;
    public static boolean QN = false;
    public static boolean XN = false;
    public static boolean XF = false;

    private LinkedList<Boolean> processingQueue = new LinkedList<Boolean>();
    private String name;
    private String[] links;
    private String type;
    private boolean on;
    private HashMap<String, TvModule> map = new HashMap<String, TvModule>();
    private HashMap<String, Boolean> inputMap = new HashMap<String, Boolean>();
    
    public TvModule(String name, String[] links) {
        if (name.contains("%")) {
            type = FLIP_FLOP;
            this.name = name.substring(1, name.length());
            on = false;
        } else if (name.contains("&")) {
            type = CONJUNCTION;
            this.name = name.substring(1, name.length());
        } else if (name.equals("broadcaster")) {
            type = BROADCASTER;
            this.name = name;
        } else {
            type = "output";
            this.name = name;
        }
        
        this.links = links;
    }
    
    public void receivePulse(String fromName, boolean highPulse) {
        if (name.equals("rx")) {
            processingQueue.add(highPulse);
        } else if (type.equals(FLIP_FLOP)) {
            if (!highPulse) {
                if (on) {
                    processingQueue.add(false);
                } else {
                    processingQueue.add(true);
                }
                
                on = !on;
            }
        } else if (type.equals(CONJUNCTION)) {
            inputMap.put(fromName, highPulse);
            
            if (isAllHigh()) {
                processingQueue.add(false);
            } else {
                processingQueue.add(true);
            }
        }
    }
    
    private boolean isAllHigh() {
        for (String key : inputMap.keySet()) {
            boolean isHigh = inputMap.get(key);
            if (!isHigh) {
                return false;
            }
        }
        return true;
    }
    
    public ArrayList<TvModule> process() {
        if (!processingQueue.isEmpty()) {
            return sendPulse(processingQueue.poll());
        }
        
        return new ArrayList<TvModule>();
    }

    public ArrayList<TvModule> sendPulse(boolean highPulse) {
        ArrayList<TvModule> sentModules = new ArrayList<TvModule>();
        for (String link : map.keySet()) {
            if (highPulse) {
                HIGH_PULSES_SENT++;
            } else {
                LOW_PULSES_SENT++;
            }

            TvModule tvModule = map.get(link);
            tvModule.receivePulse(this.getName(), highPulse);
            sentModules.add(tvModule);
            
            if (tvModule.getName().equals("th") && highPulse) {
                if (name.equals("zl")) {
                    ZL = true;
                }
                
                if (name.equals("qn")) {
                    QN = true;
                }
                
                if (name.equals("xn")) {
                    XN = true;
                }
                
                if (name.equals("xf")) {
                    XF = true;
                }
            }
        }
        return sentModules;
    }
    
    public ArrayList<TvModule> getTvModules() {
        ArrayList<TvModule> tvModules = new ArrayList<TvModule>();
        
        for (String link : map.keySet()) {
            TvModule tvModule = map.get(link);
            tvModules.add(tvModule);
        }
        
        return tvModules;
    }

    public void link(ArrayList<TvModule> tvModules) {
        for (String link : links) {
            String linkName = link.replaceAll("%", "").replaceAll("&", "");
            
            boolean found = false;
            for (TvModule tvModule : tvModules) {
                if (tvModule.getName().equals(linkName)) {
                    map.put(linkName, tvModule);
                    found = true;
                    break;
                }
            }
            if (!found) {
                map.put(linkName, new TvModule(linkName, new String[] {}));
            }
        }
        
        if (links.length != map.keySet().size()) {
            throw new RuntimeException("different size");
        }
    }

    public void setupConnections() {
        for (String link : map.keySet()) {
            map.get(link).pingAsInput(this);
        }
    }

    private void pingAsInput(TvModule tvModule) {
        inputMap.put(tvModule.getName(), false);
    }
    
    public String toString() {
        String s = "";
        for (String link : links) {
            s += link + ",";
        }
        
        return "{" + name + " (" + type + "): [" + s + "]}";
    }
    
    public String getName() {
        return name;
    }
}
