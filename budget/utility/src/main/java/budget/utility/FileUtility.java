package budget.utility;

import budget.core.Budgets;
import budget.core.Calculation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is responsible for file operations.
 */
public class FileUtility {
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

    private static final String testFilePath = "C:/Users/chris/Documents/Coding - NTNU/ITP/gr2340/budget/utility/src/main/resources/budget/utility/savedBudget.json";
    /**
     * Private constructor to hide the implicit public one.
     */
    private FileUtility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Writes the Calculation object to a file.
     *
    //   * @param budget The Calculation object to save
     * @throws IOException If an input or output exception occurred
     */
    public static void writeFile(Budgets budgets) throws IOException {
        ArrayList<Calculation> calculationList = budgets.retrieveBudgets();
        File file = new File(FILE_PATH);

        ArrayList<Calculation> existingCalculations;

        // Read existing data if any, or create an empty list
        if (file.exists()) {
            existingCalculations = Json.getMapper().readValue(file, Json.getMapper().getTypeFactory()
                    .constructCollectionType(ArrayList.class, Calculation.class));
        } else {
            existingCalculations = new ArrayList<>();
        }

        existingCalculations.addAll(calculationList);

        Json.getMapper().writerWithDefaultPrettyPrinter().writeValue(file, existingCalculations);

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
     * @throws IOException If an input or output exception occurred
     */
      public static Budgets readFile() throws IOException {
          File file = new File(FILE_PATH);
          if (file.exists() && file.length() > 0) {
              ArrayList<Calculation> calculations = Json.getMapper().readValue(file,
                      Json.getMapper().getTypeFactory()
                              .constructCollectionType(ArrayList.class, Calculation.class));

              return new Budgets(calculations);
          } else {
              return new Budgets(new ArrayList<>());
          }
      }
    public static boolean getLoad() {
        return load;
    }
}
