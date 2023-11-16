package budget.ui;

import budget.core.Calculation;
import budget.utility.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URI;
import java.net.http.HttpClient;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.nio.charset.StandardCharsets;


/**
 * Singleton class for managing data.
 */
public final class DataSingleton {
    /**
     * Field for instance of class.
     */
    private static final DataSingleton INSTANCE = new DataSingleton();
    /**
     * Url for REST API.
     */
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
     * HttpClient to be used with HTTp requests.
     */
    private final HttpClient client;
    /**
     * private constructor for retrieving instance and to set up http client.
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
        this.calculation = new Calculation(newCalculation);
    }

    /**
     * Get the current Calculation.
     *
     * @return The current Calculation.
     */

    public Calculation getCalculation() {
        getRequest(false, this.calcName, calculation);
        return new Calculation(this.calculation);
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
        return new ArrayList<>(this.calculations);
    }



    /**
     * Sends a POST request with a {@link Calculation} object to a specified API.
     *
     * This method serializes the given {@link Calculation} object into JSON format
     * and sends it as the body of a POST request to the API specified by the {@code API_URL}.
     * It prints out the response status and body to the console.
     *
     * @param newCalculation The {@link Calculation} object to be sent as part of the POST request.
     *
     * @throws MalformedURLException If the API URL is not formatted correctly.
     * @throws JsonProcessingException If there is an error in processing (serializing) the {@link Calculation} object into JSON.
     * @throws URISyntaxException If the URI syntax is incorrect.
     * @throws IOException If an I/O error occurs when sending or receiving.
     * @throws InterruptedException If the operation is interrupted.
     * @throws RuntimeException If an I/O or interrupted exception occurs, it is wrapped in a RuntimeException and rethrown.
     */
    public void sendPOSTRequest(final Calculation newCalculation) {
        try {
            String jsonCalculation = Json.getMapper().writeValueAsString(newCalculation);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URL(API_URL).toURI())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonCalculation))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();
            String responseBody = response.body();

            if (responseCode == HttpURLConnection.HTTP_OK
                    || responseCode == HttpURLConnection.HTTP_CREATED
                    || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
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

    /**
     * Sends a PUT request with a {@link Calculation} object to the specified API endpoint.
     * This method serializes the provided {@link Calculation} object into JSON and sends it
     * as the body of a PUT request to the specified API URL. The name of the calculation is
     * used to construct the endpoint URI by appending it to the base API URL after URL encoding.
     * The response status is printed to the console.
     *
     * @param newCalculation The {@link Calculation} object to be updated via the PUT request.
     *                    The object's name is used to identify the specific resource on the server.
     *
     * @throws URISyntaxException If there is a syntax error in creating the URI.
     * @throws IOException If an I/O error occurs when sending the request.
     * @throws InterruptedException If the operation is interrupted.
     * @throws RuntimeException If an exception occurs during URI creation or request execution,
     *                          it is caught and rethrown as a RuntimeException.
     */
    public void sendPUTRequest(final Calculation newCalculation) {

        String encodedName = URLEncoder.encode(newCalculation.getName(), StandardCharsets.UTF_8);

        try {
            String jsonCalc = Json.getMapper().writeValueAsString(newCalculation);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + "/" + encodedName))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonCalc))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();

            if (responseCode == HttpURLConnection.HTTP_OK
                    || responseCode == HttpURLConnection.HTTP_CREATED
                    || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("PUT Request was successful!");
            } else {
                System.out.println("PUT Request was not successful. Response Code: " + responseCode);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Sends a GET request to retrieve either a single {@link Calculation}
     * object or a list of {@link Calculation} o
     * objects from the specified API endpoint.
     * This method constructs the endpoint URI based on the specified parameters. If {@code getAll} is true, it fetches all calculations.
     * Otherwise, it fetches a specific calculation identified by the provided {@code name}. The response is then deserialized into
     * either a single {@link Calculation} object or an ArrayList of {@link Calculation} objects, depending on the request type.
     *
     * @param getAll A boolean flag indicating whether to fetch all calculations (true) or a specific calculation (false).
     * @param name   The name of the calculation to fetch. This parameter is used if {@code getAll} is false.
     * @param calc   A {@link Calculation} object to hold the response data when fetching a single calculation. This parameter is used
     *               if {@code getAll} is false.
     *
     * @throws URISyntaxException If there is a syntax error in creating the URI.
     * @throws IOException If an I/O error occurs when sending the request or processing the response.
     * @throws InterruptedException If the operation is interrupted.
     */
    public void getRequest(final boolean getAll, final String name, final Calculation calc) {
        try {
            String decodedName = null;
            if (name != null) {
                decodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            }
            String apiUrl = "http://localhost:8080/budget" + (getAll ? "" : "/" + decodedName);
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
                    this.calculations = Json.getMapper().readValue(responseBody, new TypeReference<ArrayList<Calculation>>() {

                    });
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


    /**
     * Adds calculation to this object's list and sends POST-request.
     * @param calc object to be added
     */
    public void addCalculation(final Calculation calc) {
        this.calculations.add(calc);
        sendPOSTRequest(calc);
    }

    /**
     * Goes through list and updates the searchable object with argument or else does nothing.
     * Then send PUT-request
     * @param calc object to update current
     */
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
            sendPUTRequest(calc);
        }
    }

    /**
     * Deletes calculation object.
     * @param calc object to delete
     */
    public void deleteEntry(final Calculation calc) {
        this.calculations.remove(calc);
        deleteRequest(calc.getName());
    }

    /**
     * Sends a DELETE request to remove a {@link Calculation} object identified by its name from the specified API endpoint.
     * This method constructs a DELETE request for the specified {@code name} after URL encoding it. The method sends the request to
     * the API URL and then checks the response code. If the deletion is successful (response codes 200, 201, or 204), it removes the
     * calculation from the local data as well. It prints a message to the console indicating the success or failure of the request.
     * @param name The name of the {@link Calculation} object to be deleted. The name is URL encoded before sending the request.
     * @throws URISyntaxException If there is a syntax error in creating the URI.
     * @throws IOException If an I/O error occurs when sending the request.
     * @throws InterruptedException If the operation is interrupted.
     */
    public void deleteRequest(final String name) {
        try {
            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            String apiUrl = API_URL + "/" + encodedName;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();

            if (responseCode == HttpURLConnection.HTTP_OK
                    || responseCode == HttpURLConnection.HTTP_CREATED
                    ||
                    responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                removeCalculationLocally(name);
                System.out.println("Calculation was deleted correctly");
            } else {
                System.out.println("Request was not successful " + responseCode);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a DELETE request to clear all {@link Calculation} objects from the specified API endpoint.
     * This method constructs and sends a DELETE request to the API URL to remove all calculations. It checks the response code
     * to determine if the request was successful. If successful, it prints a message to the console along with the response body.
     * Otherwise, it prints an error message with the response code.
     * @throws URISyntaxException If there is a syntax error in creating the URI.
     * @throws IOException If an I/O error occurs when sending the request.
     * @throws InterruptedException If the operation is interrupted.
     */
    public void sendClearRequest() {
        try {
            String apiURL = "http://localhost:8080/budget";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URL(apiURL).toURI())
                    .DELETE()
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();
            String responseBody = response.body();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Clear request was successful!");
                System.out.println("Response Body: " + responseBody);
            } else {
                System.out.println("Clear request was not successful: " + responseCode);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Removes calculations locally in list by using iterator.
     * @param name name of object to be removed
     */
    private void removeCalculationLocally(final String name) {
        Iterator<Calculation> calculationIterator = getCalculations().iterator();
        while (calculationIterator.hasNext()) {
            Calculation next = calculationIterator.next();
            if (next.getName().equals(name)) {
                calculationIterator.remove();
                break;
            }
        }
    }
}
