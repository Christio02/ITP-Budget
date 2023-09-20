package core;

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
    public void testGetCategoriesList() {
        assertEquals(5, calculation.getCategoriesList().size());
    }
}
