package budget.springrest.controller;

import budget.core.Calculation;
import budget.core.Category;
import budget.springrest.repository.CalculationRepositoryList;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestCalculationControllerTest {


    private final static TestRestTemplate restTemplate = new TestRestTemplate();
    private static CalculationRepositoryList repositoryList = new CalculationRepositoryList();


    @BeforeAll
    public static void setUp() {
        restTemplate.delete("http://localhost:8080/budget/test");
        restTemplate.delete("http://localhost:8080/budget/test2");
        Calculation calculation = new Calculation("test");
        Category food = calculation.getCategory("Food");
        Category transport = calculation.getCategory("Transportation");
        calculation.addAmountToCategory(food, 100);
        calculation.addAmountToCategory(transport, 500);
        restTemplate.postForEntity("http://localhost:8080/budget", calculation, Calculation.class);


    }

    @Test
    @Order(1)
    public void testGetAllCalculations() {
        ResponseEntity<ArrayList<Calculation>> entity = findAllBudgets();
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
        assertEquals(1, entity.getBody().size());
    }

    @Test
    @Order(2)
    public void testGetCalculationByName() {
        Calculation calcFromGet = findAllBudgets().getBody().get(0);
        String name = calcFromGet.getName();
        System.out.println(calcFromGet.toString());
        assertEquals("test", name);
        assertEquals(100, calcFromGet.getCategory("Food").getAmount());
        assertEquals(500, calcFromGet.getCategory("Transportation").getAmount());;

    }

    @Test
    @Order(3)
    public void testCreate() {
        Calculation calculation = new Calculation("test2");
        Category food = calculation.getCategory("Food");
        Category transport = calculation.getCategory("Transportation");
        calculation.addAmountToCategory(food, 100);
        calculation.addAmountToCategory(transport, 500);

        ResponseEntity<Calculation> entity = restTemplate.postForEntity("http://localhost:8080/budget", calculation, Calculation.class);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
        assertEquals("test2", entity.getBody().getName());
        assertEquals(100, entity.getBody().getCategory("Food").getAmount());
        assertEquals(500, entity.getBody().getCategory("Transportation").getAmount());
        assertEquals(600, entity.getBody().getTotalSum());
    }

    @Test
    @Order(4)
    public void testUpdate() {
        Calculation existingOnServer = findAllBudgets().getBody().get(0);
        System.out.println(existingOnServer.toString());
        Calculation newCalc = new Calculation();
        newCalc.setName(existingOnServer.getName());
        newCalc.addAmountToCategory(newCalc.getCategory("Food"), 1000);
        newCalc.addAmountToCategory(newCalc.getCategory("Other"), 3000);
        ResponseEntity<Calculation> entity = restTemplate.exchange("http://localhost:8080/budget/" + existingOnServer.getName(),
                HttpMethod.PUT,
                new HttpEntity<>(newCalc),
                Calculation.class);
        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());


    }

    @Test
    @Order(5)
    public void delete() {
        Calculation existing = findAllBudgets().getBody().get(0);
        ResponseEntity<Calculation> entity = restTemplate.exchange("http://localhost:8080/budget/" + existing.getName(), HttpMethod.DELETE, null, Calculation.class);
        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
        assertEquals(1, findAllBudgets().getBody().size());
    }

    @Test
    @Order(6)
    public void testClearBudgets() {
        restTemplate.delete("http://localhost:8080/budget");
        assertEquals(0, findAllBudgets().getBody().size());
    }







    private ResponseEntity<ArrayList<Calculation>> findAllBudgets() {
        return restTemplate.exchange("http://localhost:8080/budget",
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<ArrayList<Calculation>>() {});
    }

    @AfterAll
    public static void tearDown() {
        restTemplate.delete("http://localhost:8080/budget/test");
        restTemplate.delete("http://localhost:8080/budget/test2");
    }


}
