package budget.ui;

import budget.core.Calculation;
import budget.utility.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.*;
import java.net.http.HttpClient;

import java.io.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
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

    private final HttpClient client;
    /**
     * private constructor for retrieving instance.
     */
    private DataSingleton() {
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
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
        getRequest(false, this.calcName, calculation);
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

        getRequest(true, null, null);
        return this.calculations;
    }





    public void sendPOSTRequest(Calculation calculation) {
        try {


            String jsonCalculation = Json.getMapper().writeValueAsString(calculation);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URL(API_URL).toURI())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonCalculation))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();
            String responseBody = response.body();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Request was successful!");
                System.out.println("Response Body: " + responseBody);
            } else {
                System.out.println("Request was not successful for POST " + responseCode);
            }
        } catch (MalformedURLException | JsonProcessingException | URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPUTRequest(Calculation calculation) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("PUT");

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8))) {
                String jsonCalc = Json.getMapper().writeValueAsString(calculation);
                writer.write(jsonCalc);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Request was successful!");
            } else {
                System.out.println("Request was not successful for PUT " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getRequest(boolean getAll, String name, Calculation calc) {
        try {
            if (getAll) {
                URL url = new URL("http://localhost:8080/budget");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                this.calculations = Json.getMapper().readValue(conn.getInputStream(), new TypeReference<ArrayList<Calculation>>() {});
            } else {
                URL url = new URL("http://localhost:8080/budget/" + name);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
               this.calculation =  Json.getMapper().readValue(conn.getInputStream(), Calculation.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addCalculation(final Calculation calc) {
        this.calculations.add(calc);
        sendPOSTRequest(calc);
    }

    public void updateCalculation(final Calculation calc) {
        int index = this.calculations.indexOf(calc);
        if (index != -1) {
            this.calculations.set(index, calc);
            sendPUTRequest(calc);
        }
    }

    public void deleteEntry(final Calculation calc) {
        this.calculations.remove(calc);
        deleteRequest(calc.getName());
    }

    public void deleteRequest(final String name) {
        try {
            URL url = new URL(API_URL + name);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("DELETE");
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                ArrayList<Calculation> calc = getCalculations();
                Iterator<Calculation> calculationIterator = getCalculations().iterator();
                while (calculationIterator.hasNext()) {
                    Calculation calculation = calculationIterator.next();
                    if (calculation.getName().equals(name)) {
                        calculationIterator.remove();
                        break;
                    }
                }
                System.out.println("Calculation was deleted correctly");

            } else {
                System.out.println("Request was not successful " + responseCode);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
