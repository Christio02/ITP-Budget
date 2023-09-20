package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category food;

    @BeforeEach
    public void setUp() {
        food = new Category("Food");
    }

    @Test
    public void testAddAmount() {
        food.addAmount(73);
        assertEquals(73, food.getAmount());

        food.addAmount(1);
        assertEquals(74, food.getAmount());
    }

    @Test
    public void testGetBudgetHistory() {
        food.addAmount(10);
        food.addAmount(5);
        assertEquals(2, food.getBudgetHistory().size());
        assertEquals(10, food.getBudgetHistory().get(0));
        assertEquals(5, food.getBudgetHistory().get(1));
    }

}