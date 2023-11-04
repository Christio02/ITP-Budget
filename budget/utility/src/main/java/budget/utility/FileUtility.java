package budget.utility;

import budget.core.Calculation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.ArrayList;
import java.io.InputStream;

/**
 * This class is responsible for file operations.
 */
public final class FileUtility {
    private static final String FILE_PATH_TEST = "savedBudget.json";
    private static final Resource resource = new ClassPathResource(FILE_PATH_TEST);

    /**
     * Private constructor to hide the implicit public one.
     */
    private FileUtility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Writes the Calculation object to a file.
     *
     * @param calculations The ArrayList of calculation objects to save
     * @throws IOException If an input or output exception occurred
     */
    public static void writeToFile(final ArrayList<Calculation> calculations) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Read the existing data from the file, if it exists
        ArrayList<Calculation> existingData = new ArrayList<>();
        File file = resource.getFile();

        try (InputStream inputStream = resource.getInputStream()) {
            existingData = objectMapper.readValue(inputStream, new TypeReference<ArrayList<Calculation>>() {});
        }

        // Merge the new calculations with the existing data
        for (Calculation calculation : calculations) {
            if (!existingData.contains(calculation)) {
                existingData.add(calculation);
            }
        }

        // Write the merged data back to the file
        try (OutputStream outputStream = new FileOutputStream(file)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, existingData);
        }
    }


    /**
     * Sets the load status.
     *
     * @param loadStatus The new load status
     */
    public static void setLoad(final boolean loadStatus) {
        // Implement your logic for setting the load status here
    }

    /**
     * Reads the Calculation object from a file.
     *
     * @param calculations The ArrayList of calculations to update
     * @throws IOException If an input or output exception occurred
     */
    public static void readFile(final ArrayList<Calculation> calculations) throws IOException {
        try {
            InputStream inputStream = resource.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            calculations.clear();
            calculations.addAll(objectMapper.readValue(inputStream, new TypeReference<ArrayList<Calculation>>() {}));

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a budget from the file.
     *
     * @param name The name of the budget to delete
     * @param calculations The ArrayList of calculations
     */
    public static void deleteBudget(final String name, final ArrayList<Calculation> calculations) {
        Calculation foundCalculation = null;

        for (Calculation calc : calculations) {
            if (calc.getName().equals(name)) {
                foundCalculation = calc;
                break;
            }
        }

        if (foundCalculation != null) {
            calculations.remove(foundCalculation);
            System.out.println("Deleted " + name + " from the file");

            ObjectMapper objectMapper = new ObjectMapper();

            try (InputStream inputStream = resource.getInputStream()) {
                File file = resource.getFile();
                ArrayList<Calculation> existingData = objectMapper.readValue(inputStream, new TypeReference<ArrayList<Calculation>>() {});
                existingData.remove(foundCalculation);

                try (OutputStream outputStream = new FileOutputStream(file)) {
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, existingData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(name + " not found in the file. Nothing was deleted.");
        }
    }

    /**
     * Gets the load status.
     *
     * @return The load status
     */
    public static boolean getLoad() {
        // Implement your logic for getting the load status here
        return false;
    }
}
