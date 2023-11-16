package budget.core;
import budget.core.Calculation;
import budget.core.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculationTest {

    private Calculation calculation;

    @BeforeEach
    public void setUp() {
        calculation = new Calculation();
    }

    @Test
    public void testCalculationObjectIsCreatedCorrectly() {
        assertNull(calculation.getName());
        assertEquals(5, calculation.getCategoriesList().size());
        Calculation calculation1 = new Calculation("test");
        assertEquals("test", calculation1.getName());
        for (Category cat : calculation1.getCategoriesList()) {
            assertNotNull(cat);
            assertNotNull(cat.getCategoryName());
        }
    }
    @Test
    public void testCheckSetNameIsCorrect() {
        calculation.setName("test1");
        assertEquals("test1", calculation.getName());
    }

    @Test
    public void testEquals() {
        assertTrue(calculation.equals(calculation));
        Calculation nullCalc = null;
        assertFalse(calculation.equals(nullCalc));
        Category cat = new Category("Food");
        assertFalse(calculation.equals(cat));
        Calculation sameNameCalc = new Calculation("test");
        calculation.setName("test");
        assertTrue(calculation.equals(sameNameCalc));

    }

    @Test
    public void testAddAmountToCategory() {
        Category food = calculation.getCategory("Food");
        calculation.addAmountToCategory(food, 934);
        assertEquals(934, calculation.getSum(food));
    }

    @Test
    public void testThrowsForNegativeAmount() {
        Category food = calculation.getCategory("Food");
        assertThrows(IllegalArgumentException.class, () -> calculation.addAmountToCategory(food, -10));
    }

    @Test
    public void testGetCategory() {
        Category food = calculation.getCategory("Food");
        assertNotNull(food);
        assertEquals("Food", food.getCategoryName());
    }

    @Test
    public void testGetNonExistentCategory() {
        Category nonexistent = calculation.getCategory("Nonexistent");
        assertNull(nonexistent);
    }

    @Test
    public void testGetTotalSum() {
        Category food = calculation.getCategory("Food");
        Category clothing = calculation.getCategory("Clothing");

        calculation.addAmountToCategory(food, 84);
        calculation.addAmountToCategory(clothing, 22);

        assertEquals(106, calculation.getTotalSum());
    }
@Test
    public void testToString() {
        Calculation calc = new Calculation();
        calc.setName("Test Calculation");

        calc.addAmountToCategory(calc.getCategory("Food"), 70);
        calc.addAmountToCategory(calc.getCategory("Transportation"), 30);
        calc.addAmountToCategory(calc.getCategory("Entertainment"), 20);
        calc.addAmountToCategory(calc.getCategory("Clothing"), 10);
        calc.addAmountToCategory(calc.getCategory("Other"), 100);

        String expected = "Calculation Name: Test Calculation\n" +
                "Total Sum: 230\n" +
                "Categories:\n" +
                "Food: 70\n" +
                "Transportation: 30\n" +
                "Entertainment: 20\n" +
                "Clothing: 10\n" +
                "Other: 100\n";

        assertEquals(expected, calc.toString());
    }

    @Test
    public void testGetCategoriesList() {
        assertEquals(5, calculation.getCategoriesList().size());
    }


}
