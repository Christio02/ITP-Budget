package budget.springrest;

import budget.core.Calculation;
import budget.core.Category;
import budget.springrest.repository.CalculationRepositoryList;
import budget.utility.FileUtility;
import budget.utility.Json;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;



@ContextConfiguration(classes = { Calculation.class, CalculationRepositoryList.class, SpringRestApplication.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SpringRestApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private String API_URL = "http://localhost:8080/budget";


    private ObjectMapper mapper;

    @Autowired
    CalculationRepositoryList repositoryList;
    private HttpClient client;
//    private Calculation calculation = new Calculation("test");


    @BeforeEach
    public void setUp() throws Exception {
        mapper = Json.getMapper();
        Calculation testCalculation = new Calculation("test-setup");
        repositoryList.create(testCalculation); // Directly adding to the repository

    }

    private String budgetUrl(String... segments) {
        String url = "/budget";
        for (String segment : segments) {
            url = url + "/" + segment;
        }
        return url;
    }
    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/budget"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1))); // Assuming there's one calculation in the setup

    }

    @Test
    public void testFindByName() throws Exception {
        MvcResult result = mockMvc.perform(get("/budget/test"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        result.getClass().getName().equals("Calculation.class");
    }

    @AfterAll
    public static void cleanUp() {
        CalculationRepositoryList repositoryList1 = new CalculationRepositoryList();
        repositoryList1.clearBudgets();
    }



}
