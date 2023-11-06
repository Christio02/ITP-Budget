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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilityTest {

    private Calculation testCalculation;

    private String testName = "Test1";

    private static final String TEST_FILE_PATH = "/../utility/src/main/resources/budget/utility/testBudget.json";



    @BeforeEach
    public void setUp() {
        testCalculation = new Calculation();
        Category food = testCalculation.getCategory("Food");
        Category transport = testCalculation.getCategory("Transportation");

        testCalculation.addAmountToCategory(food, 200);
        testCalculation.addAmountToCategory(food, 300);
        testCalculation.addAmountToCategory(transport, 500);
        testCalculation.addAmountToCategory(transport, 2000);
    }

    @Test
    public void testNameIsIdentical() throws IOException {
        ArrayList<Calculation> testList = new ArrayList<>();
        testCalculation.setName("Test1");
        testList.add(testCalculation);

        FileUtility.writeToFile(testList, TEST_FILE_PATH); // Use the test file path

        ArrayList<Calculation> loadList = new ArrayList<>();
        FileUtility.readFile(loadList, TEST_FILE_PATH); // Use the test file path

        assertEquals(1, loadList.size());

        Calculation loadedCalculation = loadList.get(0);
        assertEquals(testName, loadedCalculation.getName());
    }

    @Test
    public void testAmountIsIdentical() throws IOException {
        ArrayList<Calculation> testList = new ArrayList<>();
        testList.add(testCalculation);

        FileUtility.writeToFile(testList, TEST_FILE_PATH); // Use the test file path
        ArrayList<Calculation> loadList = new ArrayList<>();
        FileUtility.readFile(loadList, TEST_FILE_PATH); // Use the test file path

        assertEquals(1, loadList.size());

        Calculation loadedCalculation = loadList.get(0);
        assertEquals(testCalculation.getCategory("Food").getBudgetHistory(), loadedCalculation.getCategory("Food").getBudgetHistory());
        assertEquals(testCalculation.getCategory("Transportation").getBudgetHistory(), loadedCalculation.getCategory("Transportation").getBudgetHistory());
    }

    @Test
    public void testWriteAndReadFromFile() throws IOException {
        ArrayList<Calculation> testList = new ArrayList<>();
        testList.add(testCalculation);

        FileUtility.writeToFile(testList, TEST_FILE_PATH); // Use the test file path
        ArrayList<Calculation> loadList = new ArrayList<>();
        FileUtility.readFile(loadList, TEST_FILE_PATH); // Use the test file path

        assertEquals(1, loadList.size());

        Calculation loadedCalculation = loadList.get(0);

        assertEquals(testCalculation.getCategoriesList().size(), loadedCalculation.getCategoriesList().size());

        for (int i = 0; i < testCalculation.getCategoriesList().size(); i++) {
            assertEquals(testCalculation.getCategoriesList().get(i).toString(), loadedCalculation.getCategoriesList().get(i).toString());
        }
    }

    @AfterAll
    public static void end() {
        // Write a dummy calculation object to the test file
        ArrayList<Calculation> dummyList = new ArrayList<>();
        Calculation dummyCalculation = new Calculation();
        dummyList.add(dummyCalculation);
        try {
            FileUtility.writeToFile(dummyList, TEST_FILE_PATH); // Use the test file path
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}