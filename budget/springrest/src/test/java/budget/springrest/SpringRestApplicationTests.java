package budget.springrest;

import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import budget.core.Calculation;
import budget.springrest.repository.CalculationRepositoryList;
import budget.utility.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.http.HttpClient;



@ContextConfiguration(classes = { Calculation.class, CalculationRepositoryList.class, SpringRestApplication.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc


class SpringRestApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private String API_URL = "http://localhost:8080/budget";


    private static ObjectMapper mapper;


    private static CalculationRepositoryList repositoryList;
    private HttpClient client;


    @BeforeAll
    public static void setUp() throws Exception {
        mapper = Json.getMapper();
        repositoryList = new CalculationRepositoryList();
        Calculation testCalculation = new Calculation("testSetup");
        repositoryList.create(testCalculation); // Directly adding to the repository
        System.out.println(repositoryList.getBudgets());

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
        MvcResult result = mockMvc.perform(get("/budget"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andReturn();
        String actualResponseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + actualResponseBody);

    }

    @Test
    public void testFindByName() throws Exception {
        mockMvc.perform(get("/budget/testSetup"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testSetup"));

    }

    @Test
    public void createBudget() throws Exception {
        Calculation budgetToAdd = new Calculation("newBudget");
        String jsonToAdd =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(budgetToAdd);
        mockMvc.perform(post("/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToAdd))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("newBudget"));
    }
    
    @Test
    public void updateBudget() throws Exception {
        Calculation existingOnServer = repositoryList.findByName("testSetup");
        existingOnServer.addAmountToCategory(existingOnServer.getCategory("Food"), 2000);
        existingOnServer.addAmountToCategory(existingOnServer.getCategory("Transportation"), 1000);
        String jsonToUpdate = mapper.writeValueAsString(existingOnServer);

        mockMvc.perform(put("/budget/testSetup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToUpdate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Use JSONAssert to compare content
        MvcResult result = mockMvc.perform(get("/budget/testSetup"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        // Compare the response content with the expected JSON using JSONAssert
        JSONAssert.assertEquals(jsonToUpdate, responseContent, false);
    }


    @Test
    public void deteteBudget() throws Exception {

        mockMvc.perform(delete("/budget/testSetup"))
                .andExpect(status().isNoContent());
    }



    @AfterAll
    public static void cleanUp() {
        CalculationRepositoryList repositoryList1 = new CalculationRepositoryList();
        repositoryList1.clearBudgets();
    }



}
