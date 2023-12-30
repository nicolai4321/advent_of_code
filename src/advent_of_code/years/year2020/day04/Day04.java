package advent_of_code.years.year2020.day04;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day04 extends RootDay {
    public Day04(Year year, int day) {
        super(year, day, "264", null);
        setInput1("input01.txt");
        setInput2("input01.txt");
        this.disableRun1();
    }

    @Override
    public String run1(String input) {
        String[] passports = generatePassports(input);
        String[] requiredFields = new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
        int nrValid = validate(passports, requiredFields);

        return nrValid + "";
    }

    @Override
    public String run2(String input) {
        String[] passports = generatePassports(input);
        String[] requiredFields = new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
        int nrValid = strictValidate(passports, requiredFields);

        return nrValid + "";
    }

    private int strictValidate(String[] passports, String[] requiredFields) {
        int valid = 0;
        PS: for (String passport : passports) {
            passport = passport.replaceAll("\n", " ");
            
            RF: for (String requiredField : requiredFields) {
                for (String field : passport.split(" ")) {
                    String key = field.split(":")[0];
                    String value = field.split(":")[1];

                    if (key.equals(requiredField)) {
                       if (validateField(key, value)) {
                           continue RF;
                       }
                    }
                }
                continue PS;
            }
            valid++;
        }
        return valid;
    }

    private boolean validateField(String key, String value) {
        switch (key) {
            case "byr":
                if (value.matches("^[0-9][0-9][0-9][0-9]$")) {
                    int i = Integer.parseInt(value);
                    return 1920 <= i && i <= 2002;
                } else {
                    return false;
                }
            case "iyr":
                if (value.matches("^[0-9][0-9][0-9][0-9]$")) {
                    int i = Integer.parseInt(value);
                    return 2010 <= i && i <= 2020;
                } else {
                    return false;
                }
            case "eyr":
                if (value.matches("^[0-9][0-9][0-9][0-9]$")) {
                    int i = Integer.parseInt(value);
                    return 2020 <= i && i <= 2030;
                } else {
                    return false;
                }
            case "hgt":
                if (value.matches("^[0-9]+cm$")) {
                    int i = Integer.parseInt(value.replaceAll("cm", ""));
                    return 150 <= i && i <= 193;
                } else if (value.matches("^[0-9]+in$")) {
                    int i = Integer.parseInt(value.replaceAll("in", ""));
                    return 59 <= i && i <= 76;
                } else {
                    return false;
                }
            case "hcl":
                return value.matches("^#([0-9]|a|b|c|d|e|f)([0-9]|a|b|c|d|e|f)([0-9]|a|b|c|d|e|f)([0-9]|a|b|c|d|e|f)([0-9]|a|b|c|d|e|f)([0-9]|a|b|c|d|e|f)$");
            case "ecl":
                return value.matches("^amb|blu|brn|gry|grn|hzl|oth$");
            case "pid":
                return value.matches("^[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$");
        }
        
        return true;
    }
    
    private int validate(String[] passports, String[] requiredFields) {
        int valid = 0;
        PS: for (String passport : passports) {
            passport = passport.replaceAll("\n", " ");
            
            RF: for (String requiredField : requiredFields) {
                for (String field : passport.split(" ")) {
                    String key = field.split(":")[0];
                    String value = field.split(":")[1];

                    if (key.equals(requiredField)) {
                        continue RF;
                    }
                }
                continue PS;
            }
            valid++;
        }
        return valid;
    }

    private String[] generatePassports(String input) {
        return input.split("\n\n");
    }
}
