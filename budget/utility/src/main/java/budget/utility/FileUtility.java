package budget.utility;

import budget.core.Budgets;
import budget.core.Calculation;
import budget.core.Category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * @param budget The Calculation object to save
     * @throws IOException If an input or output exception occurred
     */
    public static void writeToFile(final Budgets budget) throws IOException {
          ArrayList<Calculation> calculationList = budget.retrieveBudgets();
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
     *
     * @throws IOException If an input or output exception occurred
     */
      public static Budgets readFromFile() throws IOException {
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

    public static void main(String[] args) {
        Calculation calc = new Calculation();
        Calculation calc2 = new Calculation();
        calc.setName("Test1");
        calc2.setName("Test2");

        calc.addAmountToCategory(calc.getCategory("Food"), 2000);
        calc2.addAmountToCategory(calc.getCategory("Clothing"), 3000);

        ArrayList<Calculation> calcTest = new ArrayList<>();
        calcTest.add(calc); calcTest.add(calc2);

        Budgets budget = new Budgets(calcTest);
        try{
            FileUtility.writeToFile(budget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Budgets budget1 = FileUtility.readFromFile();


        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
