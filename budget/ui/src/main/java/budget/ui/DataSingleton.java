package budget.ui;

import budget.core.Calculation;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class for managing data.
 */
public final class DataSingleton {
    /**
     * Field for instance of class.
     */
    private static final DataSingleton INSTANCE = new DataSingleton();

    /**
     * Calculation data.
     */
    private Calculation calculation;
    /**
     * Calculation name data.
     */
    private String calcName;

    /**
     * Map data.
     */
    private Map<String, Calculation> mapOfCalculations = new HashMap<>();

    /**
     * private constructor for retrieving instance.
     */
    private DataSingleton() {
    }

    /**
     * Get the singleton instance of DataSingleton.
     *
     * @return The DataSingleton instance.
     */
    public static DataSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * Clear the map of Calculations.
     */
    public void clearMap() {
        this.mapOfCalculations.clear();
    }

    /**
     * Set the current Calculation.
     *
     * @param newCalculation The Calculation to set.
     */
    public void setCalculation(final Calculation newCalculation) {
        this.calculation = newCalculation;
    }

    /**
     * Get the current Calculation.
     *
     * @return The current Calculation.
     */
    public Calculation getCalculation() {
        return this.calculation;
    }

    /**
     * Set the Calculation name.
     *
     * @param newCalcName The name to set.
     */
    public void setCalcName(final String newCalcName) {
        this.calcName = newCalcName;
    }

    /**
     * Get the Calculation name.
     *
     * @return The Calculation name.
     */
    public String getCalcName() {
        return this.calcName;
    }

    /**
     * Add a Calculation to the map.
     *
     * @param name The name of the Calculation.
     * @param calc The Calculation to add.
     */
    public void addCalculation(final String name, final Calculation calc) {
        this.mapOfCalculations.put(name, calc);
    }

    /**
     * Get a copy of the map of Calculations.
     *
     * @return A copy of the map of Calculations.
     */
    public Map<String, Calculation> getCalculations() {
        return new HashMap<>(this.mapOfCalculations);
    }

    /**
     * Update the map of Calculations.
     *
     * @param tempMap The map to update with.
     */
    public void updateMap(final Map<String, Calculation> tempMap) {
        this.mapOfCalculations.putAll(tempMap);
    }
}
