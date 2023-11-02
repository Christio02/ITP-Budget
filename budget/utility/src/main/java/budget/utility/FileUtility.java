package budget.utility;

import budget.core.Calculation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is responsible for file operations.
 */
public final class FileUtility {
    /**
     * Load status.
     */
    private static boolean load;

    /**
     * Current directory path.
     */
    private static final String CURRENT_DIR = System.getProperty("user.dir");

    /**
     * File path for serialization.
     */
    private static final String FILE_PATH = CURRENT_DIR
            + "/../utility/src/main/resources/budget/utility/savedBudget.json";

    /**
     * Private constructor to hide the implicit public one.
     */
    private FileUtility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Writes the Calculation object to a file.
     *
     * @param calculations The Arraylist of calculation objects to save
     * @param path The path to the file
     * @throws IOException If an input or output exception occurred
     */
    public static void writeToFile(final ArrayList<Calculation> calculations, final String path) throws IOException {
        File file = new File(CURRENT_DIR + path);
        // Check if calcMap is empty
        if (calculations.isEmpty()) {
            // If it's empty, create an empty JSON object and write it to the file
            Json.getMapper().writeValue(file, new ArrayList<>());
        } else {
            ArrayList<Calculation> existingDataList;
            if (file.exists()) {
                existingDataList = Json.getMapper().readValue(file, Json.getMapper()
                        .getTypeFactory().constructCollectionType(ArrayList.class, Calculation.class));
            } else {
                existingDataList = new ArrayList<>();
            }

            for (Calculation calculation : calculations) {
                if (!existingDataList.contains(calculation)) {
                    existingDataList.add(calculation);
                }
            }

            // Write the merged data back to the file
            Json.getMapper().writerWithDefaultPrettyPrinter().writeValue(file, existingDataList);
        }
    }


    /**
     * Sets the load status.
     *
     * @param loadStatus The new load status
     */
    public static void setLoad(final boolean loadStatus) {
        load = loadStatus;
    }

    /**
     * Reads the Calculation object from a file.
     *
     * @param calculations The ArrayList of calculations to update
     * @param path The path to the file
     * @throws IOException If an input or output exception occurred
     */
    public static void readFile(final ArrayList<Calculation> calculations, final String path) throws IOException {
        File file = new File(CURRENT_DIR + path);
        ArrayList<Calculation> calculationArrayList =
                Json.getMapper().readValue(file, new TypeReference
                        <ArrayList<Calculation>>(
                                ) {
                });
        calculations.clear();

        calculations.addAll(calculationArrayList);
    }

    /**
     * Deletes a budget from the file.
     * @param name The name of the budget to delete
     * @param calculations The ArrayList of calculations
     */
    public static void deleteBudget(final String name, final ArrayList<Calculation> calculations) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            readFile(calculations, "/../utility/src/main/resources/budget/utility/savedBudget.json");

            for (Calculation calc : calculations) {
                if (calc.getName().equals(name)) {
                    calculations.remove(calc);
                    System.out.println("Deleted " + name + " from file");
                    break;
            } else {
                System.out.println(name + " not found in the file. Nothing was deleted.");
            }
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), calculations);
        }
    } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Gets the load status.
     *
     * @return The load status
     */
    public static boolean getLoad() {
        return load;
    }


}
