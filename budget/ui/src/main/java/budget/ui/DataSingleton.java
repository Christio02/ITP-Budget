package budget.ui;

import budget.core.Calculation;

public class DataSingleton {
    private static final DataSingleton instance = new DataSingleton();

    private Calculation calculation;

    private String calcName;


    private  DataSingleton(){};

    public static DataSingleton getInstance() {
        return instance;
    }
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalcName(String calcName){
        this.calcName = calcName;
    }

    public String getCalcName(){
        return this.calcName;
    }
}
