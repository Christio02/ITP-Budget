package budget.springrest;

import budget.core.Calculation;
import budget.springrest.repository.CalculationRepositoryList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ServerRepositoryTest {
    private CalculationRepositoryList repositoryList = new CalculationRepositoryList();
    private Calculation calculation = new Calculation("test");

    @BeforeEach
    public void setUp() {
        repositoryList.create(calculation);
    }

    @Test
    public void testFindAll() {
        ArrayList<Calculation> tempCalculations = repositoryList.findAll();
        assertFalse(tempCalculations.isEmpty());
        assertEquals(1, tempCalculations.size());
    }

    @Test
    public void testfindByName() {
        Calculation tempCalculation = repositoryList.findByName("test");
        assertEquals("test", tempCalculation.getName());
        assertThrows(IllegalArgumentException.class, () -> {
            repositoryList.findByName("test2");
        });

    }

    @Test
    public void testCreat() {
        assertThrows(IllegalArgumentException.class, () -> repositoryList.create(new Calculation("null")));
        repositoryList.create(new Calculation("test2"));
        assertEquals(2, repositoryList.getBudgets().size());
        assertEquals("test2", repositoryList.create(new Calculation("test2")).getName());
    }

    @Test
    public void testUpdate() {
        Calculation existing = repositoryList.findByName("test");
        existing.addAmountToCategory(existing.getCategory("Food"), 2000);
        repositoryList.update(existing);
        assertEquals(2000, repositoryList.findByName("test").getCategory("Food").getAmount());
        Calculation existing2 = repositoryList.findByName("test");
        existing2.addAmountToCategory(existing2.getCategory("Transportation"), 1000);
        repositoryList.update(existing2);
        assertEquals(1000, repositoryList.findByName("test").getCategory("Transportation").getAmount());
        assertEquals(3000, repositoryList.findByName("test").getTotalSum());
    }

    @Test
    public void testDelete() {
        repositoryList.delete("test");
        assertTrue(repositoryList.getBudgets().isEmpty());
    }

    @AfterEach
    public void tearDown() {
        repositoryList.clearBudgets();
    }
}
