    package budget.utility;

    import budget.core.Calculation;
    import budget.core.Category;
    import com.fasterxml.jackson.core.type.TypeReference;

    import java.io.File;
    import java.io.IOException;
    import java.util.Map;

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
         * @param calculationMap The map of calculation object to save
         * @throws IOException If an input or output exception occurred
         */
        public static void writeToFile(Map<String, Calculation> calculationMap) throws IOException {
            File file = new File(FILE_PATH);
            Json.getMapper()
                    .writerWithDefaultPrettyPrinter().writeValue(file, calculationMap);
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
         * @param calculationMap The map of calculations to update
         * @throws IOException If an input or output exception occurred
         */
        public static void readFromFile(Map<String, Calculation> calculationMap) throws IOException {
         Map<String, Calculation> mapFromFile = Json.
                    getMapper().
                 readValue(new File(FILE_PATH), new TypeReference<Map<String, Calculation>>() {});

            calculationMap.clear();

            for (Map.Entry<String, Calculation> entry : mapFromFile.entrySet()) {
                String name = entry.getKey();
                Calculation calcObject = entry.getValue();
                calculationMap.put(name, calcObject);
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
