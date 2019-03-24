package com.convert.unitconverter;

import java.util.HashMap;
import java.util.Map;

public class UnitConverter {
    private boolean startIsMetric = false, endIsMetric = false;
    private String startUnits, endUnits, unitType;
    private double number;
    private double answer = 0;

    Map <String, Double> imperialUnits = new HashMap();
    Map <String, Double> metricUnits = new HashMap();

    public UnitConverter(){
        initializeUnits();
    }

    public void setUnitType(String unitType){
        this.unitType = unitType;
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
        double metric = 0;
        double imperial = 0;

        // if initial units is imperial
        if (!startIsMetric) {
            imperial = number / imperialUnits.get(getStartUnits());      // divide by convertion factor to convert to feet
            metric = imperialToMetric(imperial);                         // convert feet to meters
        } else { // if start is metric
            metric = number / metricUnits.get(startUnits);            // divide by convertion factor to convert to meters
        }

        // if end is imperial
        if(!endIsMetric){
            imperial = metricToImperial(metric);                        // convert back to feet
            answer = imperial * imperialUnits.get(endUnits);    // convert to correct units with convertion factor
        }else{ // if end is metric
            answer = metric * metricUnits.get(endUnits);    // convert to correct units with convertion factor
        }
        return answer;
    }

    // initialize hashmap
    private void initializeUnits(){
        // the double is used to convert the value to feet
        imperialUnits.put("inches", 12.0);
        imperialUnits.put("feet",1.0);
        imperialUnits.put("yard",1.0/3.0);
        imperialUnits.put("miles",1.0/5280.0);

        // the double is used to convert the value to meters first so that you can then convert it to any desired units.
        metricUnits.put("milimeters", 1000.0);
        metricUnits.put("centimeters", 100.0);
        metricUnits.put("meters", 1.0);
        metricUnits.put("kilometers", 0.001);

        //**********************************
        //weight
        imperialUnits.put("ounce", 16.0);
        imperialUnits.put("pound", 1.0);
        imperialUnits.put("ton", 1.0/2000.0);

        metricUnits.put("microgram", 1_000_000.0);
        metricUnits.put("milligram", 1000.0);
        metricUnits.put("centigram",100.0);
        metricUnits.put("decigram", 10.0);
        metricUnits.put("gram",1.0);
        metricUnits.put("hectogram", .01);
        metricUnits.put("kilogram", .001);

        //**********************************
        //volume
        imperialUnits.put("tsp", 768.0);
        imperialUnits.put("tbsp", 256.0);
        imperialUnits.put("cup",16.0);
        imperialUnits.put("pint", 8.0);
        imperialUnits.put("quart", 4.0);
        imperialUnits.put("gallon", 1.0);

        metricUnits.put("milliliter", 1000.0);
        metricUnits.put("liter", 1.0);
    }

    private double convertionFactorForFeet(){
        return imperialUnits.get(getStartUnits());
    }

    private double imperialToMetric(double number){
        switch(unitType) {
            case "distances": return number/3.28084; // feet to meters
            case "weights": return number*453.592;  // pound to gram
            case "volumes": return number*3.78541;
            default: return -1.0;
        }
    }

    private double metricToImperial(double number){
        switch(unitType) {
            case "distances": return number*3.28084; // meters to feet
            case "weights": return number/453.592;  // gram to pound
            case "volumes": return number/3.78541;
            default: return -1.0;
        }
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
