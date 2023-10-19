package budget.utility;

import budget.core.Calculation;
import budget.core.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilityTest {

    Calculation testCalculation;

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
    public void testWriteAndReadFromFile() throws IOException {
        FileUtility.writeToFile(testCalculation);

        assertTrue(new File(FileUtility.filePath).exists());

        Calculation loadCalculation = new Calculation();
        FileUtility.readFromFile(loadCalculation);

        assertEquals(testCalculation.getCategoriesList().size(), loadCalculation.getCategoriesList().size());
        for (int i = 0; i < testCalculation.getCategoriesList().size(); i++) {
            assertEquals(testCalculation.getCategoriesList().get(i), loadCalculation.getCategoriesList().get(i));
        }
    }

    @AfterEach
    public void tearDown() {
        new File(FileUtility.filePath).delete();
    }
}