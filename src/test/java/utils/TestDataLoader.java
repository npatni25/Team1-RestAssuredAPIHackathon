package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class TestDataLoader {

    private static final String testDataFile = ConfigReader.getProperty("testDataFilePath");

    public static <T> T loadTestData(String requestType, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(testDataFile));
            JsonNode requests = rootNode.get("requests");

            for (JsonNode requestNode : requests) {
                Iterator<Map.Entry<String, JsonNode>> fields = requestNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    if (field.getKey().equalsIgnoreCase(requestType)) {
                        return objectMapper.treeToValue(field.getValue(), clazz);
                    }
                }
            }

            throw new RuntimeException("Request type not found: " + requestType);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data for: " + requestType, e);
        }
    }

    public static JsonNode loadTestDataAsJsonNode(String requestType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(testDataFile));
            JsonNode requests = rootNode.get("requests");

            for (JsonNode requestNode : requests) {
                Iterator<Map.Entry<String, JsonNode>> fields = requestNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    if (field.getKey().equalsIgnoreCase(requestType)) {
                        return field.getValue();
                    }
                }
            }

            throw new RuntimeException("Request type not found: " + requestType);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data as JsonNode for: " + requestType, e);
        }
    }
}