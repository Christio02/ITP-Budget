package budget.utility;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This class is responsible for JSON operations.
 */
public final class Json {

    /**
     * ObjectMapper for JSON operations.
     */
    private static final ObjectMapper MAPPER = getMapper();

    /**
     * The amount to be added to the category.
     */
    private static final int AMOUNT = 2000;

    /**
     * Private constructor to hide the implicit public one.
     */
    private Json() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets the ObjectMapper.
     *
     * @return The ObjectMapper
     */
    public static ObjectMapper getMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.
                configure(DeserializationFeature.
                        FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;
    }

    /**
     * Parses a JSON string into a JsonNode.
     *
     * @param src The JSON string to parse
     * @return The parsed JsonNode
     * @throws IOException If an input or output exception occurred
     */
    public static JsonNode parse(final String src) throws IOException {
        return MAPPER.readTree(src);
    }

    /**
     * Converts a JsonNode into an object of the specified class.
     *
     * @param node The JsonNode to convert
     * @param aClass The class of the object to return
     * @param <A> The type of the object to return
     * @return The converted object
     * @throws JsonProcessingException If a JSON processing exception occurred
     */
    public static <A> A fromJson(final JsonNode node, final Class<A> aClass)
            throws JsonProcessingException {
        return MAPPER.treeToValue(node, aClass);
    }


    /**
     * Converts an object into a JsonNode.
     *
     * @param aObject The object to convert
     * @return The converted JsonNode
     */
    public static JsonNode toJson(final Object aObject) {
        return MAPPER.valueToTree(aObject);
    }
}
