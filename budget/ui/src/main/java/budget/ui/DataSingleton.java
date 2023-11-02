package budget.ui;

import budget.core.Calculation;
import budget.utility.Json;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Singleton class for managing data.
 */
public final class DataSingleton {
    /**
     * Field for instance of class.
     */
    private static final DataSingleton INSTANCE = new DataSingleton();
    private static final String API_URL = "http://localhost:8080/budget";

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
    private ArrayList<Calculation> calculations = new ArrayList<>();

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
    public void clearList() {
        this.calculations.clear();
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
        sendRequest(this.calculation, "GET", false);
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
     * Get a copy of the map of Calculations.
     *
     * @return A copy of the map of Calculations.
     */
    public ArrayList<Calculation> getCalculations() {

        sendRequest(null, "GET", true);
        return this.calculations;
    }





    public void sendRequest(Calculation calculation, String httpMethod, boolean all) {
        try {
            URL url = new URL(API_URL);
            if (!all) {
                url = new URL(API_URL + "/" + calculation.getName());
            }

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpMethod);
            conn.setRequestProperty("Content-Type", "application/json");

            if (httpMethod.equals("POST") || httpMethod.equals("PUT")) {
                conn.setDoOutput(true);
                String jsonCalculation = Json.getMapper().writeValueAsString(calculation);
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
                    writer.write(jsonCalculation);
                }
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Request was successful!");

                if (httpMethod.equals("GET")) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                        String jsonResponse = reader.lines().collect(Collectors.joining("\n"));
                        ArrayList<Calculation> responseCalculations = Json.getMapper().readValue(jsonResponse, new TypeReference<ArrayList<Calculation>>() {});
                        calculations.clear();
                        calculations.addAll(responseCalculations);
                    }
                }
            } else {
                System.out.println("Request was not successful " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addCalculation(final Calculation calc) {
        this.calculations.add(calc);
        sendRequest(calc, "POST", false);
    }

    public void updateCalculation(final Calculation calc) {
        int index = this.calculations.indexOf(calc);
        if (index != -1) {
            this.calculations.set(index, calc);
            sendRequest(calc, "PUT", false);
        }
    }

    public void deleteEntry(final Calculation calc) {
        this.calculations.remove(calc);
        sendRequest(calc, "DELETE", false);
    }
}
