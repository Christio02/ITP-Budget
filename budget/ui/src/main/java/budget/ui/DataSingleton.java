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

    public void sendPUTRequest(String name) {
        try {
            String jsonCalc = Json.getMapper().writeValueAsString(calculation);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + "/" + name))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonCalc))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("PUT Request was successful!");
            } else {
                System.out.println("PUT Request was not successful. Response Code: " + responseCode);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getRequest(boolean getAll, String name, Calculation calc) {
        try {
            String apiUrl = "http://localhost:8080/budget" + (getAll ? "" : "/" + name);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type", "application/json")
                    .GET();

            HttpResponse<String> response = client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
            int responseCode = response.statusCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseBody = response.body();
                System.out.println("GET Request was successful!");
                if (getAll) {
                    this.calculations = Json.getMapper().readValue(responseBody, new TypeReference<ArrayList<Calculation>>() {});
                } else {
                    this.calculation = Json.getMapper().readValue(responseBody, Calculation.class);
                }
            } else {
                System.out.println("GET Request was not successful. Response Code: " + responseCode);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void addCalculation(final Calculation calc) {
        this.calculations.add(calc);
        sendPOSTRequest(calc);
    }

    private boolean checkIfExists(String name) {
        return !this.calculations.stream()
                .filter(p -> p.getName().equals(name)).toList().isEmpty();
    }

    public void updateCalculation(final Calculation calc) {
       int index = -1;
        for (int i = 0; i < calculations.size(); i++) {
            if (calculations.get(i).getName().equals(calc.getName())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            this.calculations.set(index, calc);
            sendPUTRequest(calc.getName());
        }
    }

    public void deleteEntry(final Calculation calc) {
        this.calculations.remove(calc);
        deleteRequest(calc.getName());
    }

    public void deleteRequest(final String name) {
        try {
            String apiUrl = API_URL + "/" + name;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                removeCalculationLocally(name);
                System.out.println("Calculation was deleted correctly");
            } else {
                System.out.println("Request was not successful " + responseCode);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void removeCalculationLocally(String name) {
        Iterator<Calculation> calculationIterator = getCalculations().iterator();
        while (calculationIterator.hasNext()) {
            Calculation calculation = calculationIterator.next();
            if (calculation.getName().equals(name)) {
                calculationIterator.remove();
                break;
            }
        }
    }
}
