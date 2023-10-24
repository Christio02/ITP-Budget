package budget.utility;

import budget.core.Calculation;
import budget.core.Category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilityTest {

    private Calculation testCalculation;
    private Map<String, Calculation> testMap;

    private String testName = "Test1";



    @BeforeEach
    public void setUp() {
        testCalculation = new Calculation();
        testMap = new HashMap<>();
        Category food = testCalculation.getCategory("Food");
        Category transport = testCalculation.getCategory("Transportation");

        testCalculation.addAmountToCategory(food, 200);
        testCalculation.addAmountToCategory(food, 300);
        testCalculation.addAmountToCategory(transport, 500);
        testCalculation.addAmountToCategory(transport, 2000);
        testMap.put(testName, testCalculation);
        String userDir = System.getProperty("user.dir");



    }
    @Test
    public void testNameIsIdentical() throws IOException{
        FileUtility.writeToFile(testMap, "/../utility/src/main/resources/budget/utility/testBudget.json");
        Map<String, Calculation> loadMap = new HashMap<>();
        FileUtility.readFile(loadMap, "/../utility/src/main/resources/budget/utility/testBudget.json");

        for (Map.Entry<String, Calculation> entry : loadMap.entrySet() ) {
            String name = entry.getKey();
            if (!name.equals("overwrite")) {
                assertEquals(testName, name);
            }


        }
    }

    @Test
    public void testAmountIsIdentical() throws IOException {
        FileUtility.writeToFile(testMap, "/../utility/src/main/resources/budget/utility/testBudget.json");
        Map<String, Calculation> loadMap = new HashMap<>();
        FileUtility.readFile(loadMap, "/../utility/src/main/resources/budget/utility/testBudget.json");

        for (Map.Entry<String, Calculation> entry: loadMap.entrySet()) {
            String name = entry.getKey();
            Calculation calculation = entry.getValue();
            if (!name.equals("overwrite")) {
                assertEquals( testCalculation.getCategory("Food").getBudgetHistory(), calculation.getCategory("Food").getBudgetHistory());
                assertEquals(testCalculation.getCategory("Transportation").getBudgetHistory(), calculation.getCategory("Transportation").getBudgetHistory());
            }
        }

    }

    @Test
    public void testWriteAndReadFromFile() throws IOException {
        FileUtility.writeToFile(testMap, "/../utility/src/main/resources/budget/utility/testBudget.json");


        Map<String, Calculation> loadMap = new HashMap<>();
        FileUtility.readFile(loadMap, "/../utility/src/main/resources/budget/utility/testBudget.json");
        Calculation loadCalculation = loadMap.get("Test1");

        assertEquals(testCalculation.getCategoriesList().size(), loadCalculation.getCategoriesList().size());
        for (int i = 0; i < testCalculation.getCategoriesList().size(); i++) {
            assertEquals(testCalculation.getCategoriesList().get(i).toString(), loadCalculation.getCategoriesList().get(i).toString());
        }
    }

    @AfterAll
    public static void end() {
        Map<String, Calculation> fileMap = new HashMap<>();
        Calculation overWriteCalc = new Calculation();
        fileMap.put("overwrite", overWriteCalc);
        ObjectMapper mapper = new ObjectMapper();
        String userDir = System.getProperty("user.dir");
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(userDir + "/../utility/src/main/resources/budget/utility/testBudget.json"), fileMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}