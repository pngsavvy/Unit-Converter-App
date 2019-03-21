package com.convert.unitconverter;

import java.util.HashMap;
import java.util.Map;

public class UnitConverter {
    private boolean startIsMetric = false, endIsMetric = false;
    private String startUnits, endUnits;
    private double number;
    private double answer = 0;

    Map <String, Double> imperialUnits = new HashMap();
    Map <String, Double> metricUnits = new HashMap();

    public UnitConverter(){
        initializeUnits();
    }

    public double calculate(double number, String startUnits, String endUnits){
        // initialize to false before I test
        startIsMetric = false;
        endIsMetric = false;

        // determ the unit type of starting units and the end goal for the units
        if(metricUnits.containsKey(startUnits)) startIsMetric = true;
        if(metricUnits.containsKey(endUnits)) endIsMetric = true;

        this.startUnits = startUnits;
        this.endUnits = endUnits;
        this.number = number;

        // convert to meters then convert to correct units
        return convert();
    }

    private double convert() {
        double temp = 0;
        double meters = 0;
        double feet = 0;

        // if initial units is not metric
        if (!startIsMetric) {
            temp = number / imperialUnits.get(getStartUnits());      // divide by convertion factor to convert to feet
            meters = feetToMeters(temp);                                // convert to meters
        } else {
            meters = number / metricUnits.get(startUnits);
                            // multipy by convertion factor to convert to meters
        }

        // if end is not metric
        if(!endIsMetric){
            feet = meters * 3.28084;                        // convert back to feet
            answer = feet * imperialUnits.get(endUnits);    // convert to correct units with convertion factor
        }else{
            answer = meters * metricUnits.get(endUnits);    // convert to correct units with convertion factor
        }
        return answer;
    }

    private double convertionFactorForFeet(){
        return imperialUnits.get(getStartUnits());
    }

    private double feetToMeters(double feet){ return feet/3.28084;}

    // initialize hashmap
    private void initializeUnits(){     // the smaller the convertion factor the bigger the unit
        // the double is used to convert the value to meters first so that you can then convert it to any desired units.
        metricUnits.put("milimeters", 1000.0);
        metricUnits.put("centimeters", 100.0);
        metricUnits.put("meters", 1.0);
        metricUnits.put("kilometers", 0.001);

        imperialUnits.put("inches", 12.0);
        imperialUnits.put("feet",1.0);
        imperialUnits.put("miles",1.0/5280.0);
    }


    public double getAnswer() {
        return answer;
    }

    public String getStartUnits() {
        return startUnits;
    }

    public void setStartUnits(String startUnits) {
        this.startUnits = startUnits;
    }

    public String getEndUnits() {
        return endUnits;
    }

    public void setEndUnits(String endUnits) {
        this.endUnits = endUnits;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public boolean isStartIsMetric() {
        return startIsMetric;
    }

    public void setStartIsMetric(boolean startIsMetric) {
        this.startIsMetric = startIsMetric;
    }

    public boolean isEndIsMetric() {
        return endIsMetric;
    }

    public void setEndIsMetric(boolean endIsMetric) {
        this.endIsMetric = endIsMetric;
    }
}
