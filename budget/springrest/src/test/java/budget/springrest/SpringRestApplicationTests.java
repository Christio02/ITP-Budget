package budget.springrest;

import budget.core.Calculation;
import budget.core.Category;
import budget.springrest.repository.CalculationRepositoryList;
import budget.utility.FileUtility;
import budget.utility.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Fail;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;


@ContextConfiguration(classes = { Calculation.class, CalculationRepositoryList.class, SpringRestApplication.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SpringRestApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private String API_URL = "http://localhost:8080/budget";


    private ObjectMapper mapper;
    CalculationRepositoryList repositoryList;
    private HttpClient client;


    @BeforeEach
    public void setUp() throws Exception {
        mapper = Json.getMapper();
        repositoryList = new CalculationRepositoryList();
        repositoryList.create(new Calculation("test"));

    }

    private String budgetUrl(String... segments) {
        String url = "/budget";
        for (String segment : segments) {
            url = url + "/" + segment;
        }
        return url;
    }
    
    @Test
    public void testGet_budget() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(budgetUrl())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();


        System.out.println("Response Headers: " + result.getResponse().getHeaderNames());
        System.out.println("Response Content: " + result.getResponse().getContentAsString());



//        try {
//            System.out.println("Response content " + result.getResponse().getContentAsString());
//            ArrayList<Calculation> calculations = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ArrayList<Calculation>>() {
//            });
//
//            System.out.println(calculations.size());
//        } catch (JsonProcessingException jpe) {
//            fail(jpe.getMessage());
//        }
    }

    @AfterAll
    public static void cleanUp() {
        CalculationRepositoryList repositoryList1 = new CalculationRepositoryList();
        repositoryList1.clearBudgets();
    }



}
