package budget.utility;

import budget.core.Calculation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.InputStream;

/**
 * This class is responsible for file operations.
 */
public final class FileUtility {
    /**
     * Current directory path.
     */
    private static final String CURRENT_DIR = System.getProperty("user.dir");

    /**
     * Load boolean that tells controllers if they should load new or exisiting budget.
     */
    private static boolean load;

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
     * @param path         The path to the file
     * @throws IOException If an input or output exception occurred
     */
    public static void writeToFile(final ArrayList<Calculation> calculations, final String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String finalPath = CURRENT_DIR + path;
        File file = new File(finalPath);

        // Write the updated data directly to the file, overwriting the existing content
        try (OutputStream outputStream = new FileOutputStream(file)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, calculations);
        }
    }


    /**
     * Sets the load status.
     *
     * @param loadStatus The new load status
     */
    public static void setLoad(final boolean loadStatus) {
        FileUtility.load = loadStatus;
    }

    /**
     * Reads the Calculation object from a file.
     *
     * @param calculations The ArrayList of calculations to update
     * @param path         The path to the file
     * @throws IOException If an input or output exception occurred
     */
    public static void readFile(final ArrayList<Calculation> calculations, final String path) throws IOException {
        String finalPath = CURRENT_DIR + path;
        File file = new File(finalPath);

        // Using try-with-resources to ensure the inputStream is closed properly
        try (InputStream inputStream = new FileInputStream(file)) {
            ObjectMapper objectMapper = new ObjectMapper();
            calculations.clear();
            calculations.addAll(objectMapper.readValue(inputStream, new TypeReference<ArrayList<Calculation>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Gets the load status.
     *
     * @return The load status
     */
    public static boolean getLoad() {
        return FileUtility.load;
    }
}
