package budget.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import budget.core.Calculation;

import java.io.IOException;

public class Json {
    private static ObjectMapper objectMapper = getObjectMapper();

    public static ObjectMapper getObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return  defaultObjectMapper;
    }

    public static JsonNode parse(String src) throws IOException {
            return objectMapper.readTree(src);

    }

    public static <A> A fromJson(JsonNode node, Class<A> aClass) throws JsonProcessingException {
       return objectMapper.treeToValue(node, aClass);
    }

    public static JsonNode toJson(Object aObject) {
        return objectMapper.valueToTree(aObject);
    }


    public static void main(String[] args) {
//
        Calculation calc = new Calculation();

        calc.addAmountToCategory(calc.getCategory("Food"), 2000);
        System.out.println(calc.toString());
        System.out.println(toJson(calc));

    }



}
