package budget.utility;

import budget.core.Calculation;
import budget.core.Category;
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
        String path = userDir + "/../utility/src/main/resources/budget/utility/savedBudget.json";
        File file = new File(path);

//        try {
//            Json.getMapper().writerWithDefaultPrettyPrinter().writeValue(file, testMap);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
    @Test
    public void testNameIsIdentical() throws IOException{
        FileUtility.writeToFile(testMap);
        Map<String, Calculation> loadMap = new HashMap<>();
        FileUtility.readFile(loadMap);

        for (Map.Entry<String, Calculation> entry : loadMap.entrySet() ) {
            String name = entry.getKey();
            assertEquals(testName, name);

        }
    }

    @Test
    public void testAmountIsIdentical() throws IOException {
        FileUtility.writeToFile(testMap);
        Map<String, Calculation> loadMap = new HashMap<>();
        FileUtility.readFile(loadMap);

        Calculation calc = loadMap.get("FileUtilityTest");

        for (Map.Entry<String, Calculation> entry: loadMap.entrySet()) {
            String name = entry.getKey();
            Calculation calculation = entry.getValue();
            if (!name.equals("overwrite")) {
                assertEquals(calculation.getCategory("Food").getBudgetHistory(), testCalculation.getCategory("Food").getBudgetHistory());
                assertEquals(calculation.getCategory("Transportation").getBudgetHistory(), testCalculation.getCategory("Transportation").getBudgetHistory());
            }
        }

    }

    @Test
    public void testWriteAndReadFromFile() throws IOException {
        FileUtility.writeToFile(testMap);


        Map<String, Calculation> loadMap = new HashMap<>();
        FileUtility.readFile(loadMap);
        Calculation loadCalculation = loadMap.get("Test1");

        assertEquals(testCalculation.getCategoriesList().size(), loadCalculation.getCategoriesList().size());
        for (int i = 0; i < testCalculation.getCategoriesList().size(); i++) {
            assertEquals(testCalculation.getCategoriesList().get(i).toString(), loadCalculation.getCategoriesList().get(i).toString());
        }
    }

}