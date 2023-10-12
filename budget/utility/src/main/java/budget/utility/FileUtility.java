package budget.utility;

import budget.core.Calculation;
import budget.core.Category;

import java.io.File;
import java.io.IOException;

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
     * @param calc The Calculation object to save
     * @throws IOException If an input or output exception occurred
     */
    public static void writeToFile(final Calculation calc) throws IOException {
        Json.getMapper().
                writerWithDefaultPrettyPrinter().
                writeValue(new File(FILE_PATH), calc);
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
     * @param calc The Calculation object to load
     * @throws IOException If an input or output exception occurred
     */
    public static void readFromFile(final Calculation calc) throws IOException {
        Calculation loadCalc = Json.
                getMapper().
                readValue(new File(FILE_PATH), Calculation.class);

        calc.getCategoriesList().clear();

        for (Category cat : loadCalc.getCategoriesList()) {
            calc.getCategoriesList().add(cat);
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
