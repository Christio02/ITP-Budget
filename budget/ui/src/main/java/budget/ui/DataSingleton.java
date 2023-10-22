package budget.ui;

import budget.core.Calculation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataSingleton {
    private static final DataSingleton instance = new DataSingleton();

    private Calculation calculation;

    private String calcName;

    private Map<String, Calculation> mapOfCalculations = new HashMap<>();


    private  DataSingleton(){};

    public static DataSingleton getInstance() {
        return instance;
    }
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public Calculation getCalculation() {
        return this.calculation;
    }

    public void setCalcName(String calcName){
        this.calcName = calcName;
    }

    public String getCalcName(){
        return this.calcName;
    }

    public void addCalculation(String name, Calculation calc) {
        this.mapOfCalculations.put(name, calc);
    }
    public Map<String, Calculation> getCalculations() {
        return new HashMap<>(this.mapOfCalculations);
    }
    public void updateMap(Map<String, Calculation> tempMap) {
        this.mapOfCalculations.putAll(tempMap);
    }
}
