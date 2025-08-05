
package utils;
import utils.ConfigReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import java.util.Map;


public class jsonReader {
	public static <T> List<T> readJsonList(String filePath, Class<T> clazz) {
	    ObjectMapper mapper = new ObjectMapper();
	    List<T> list = null;
	    try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
	        if (is == null) {
	            throw new RuntimeException("Resource not found: " + filePath);
	        }
	        // Create a TypeReference for List<DieticianData>
	        list = mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to read JSON list from resource: " + filePath);
	    }
	    return list;
	}
	
	public static class ScenarioContext {
	    private static ThreadLocal<String> scenarioName = new ThreadLocal<>();
	    public static void setScenarioName(String name) {
	        scenarioName.set(name);
	    }
	    public static String getScenarioName() {
	        return scenarioName.get();
	    }
	    public void clear() {
	        scenarioName.remove();
	    }
	}


	
	//Code from Subhadra
	private static final String testDataFile = ConfigReader.getProperty("testDataFilePath");

    public static <T> T loadTestDataForPostPut(String requestType, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(testDataFile));
            JsonNode requests = rootNode.get("requests");

            for (JsonNode requestNode : requests) {
                @SuppressWarnings("deprecation")
				Iterator<Map.Entry<String, JsonNode>> fields = requestNode.fields();

                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();

                    if (field.getKey().equalsIgnoreCase(requestType)) {
                        T pojo = objectMapper.treeToValue(field.getValue(), clazz);

                        setFieldIfExists(pojo, "statusCode", field.getValue(), int.class);
                        setFieldIfExists(pojo, "statusText", field.getValue(), String.class);
                        setFieldIfExists(pojo, "message", field.getValue(), String.class);
                        setFieldIfExists(pojo, "id", field.getValue(), String.class);
                        setFieldIfExists(pojo, "name", field.getValue(), String.class);
                        setFieldIfExists(pojo, "type", field.getValue(), String.class);

                        return pojo;
                    }
                }
            }

            throw new RuntimeException("Request type not found: " + requestType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load POST/PUT test data for: " + requestType, e);
        }
    }

    public static JsonNode loadTestDataForGet(String requestType) {
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
            throw new RuntimeException("Failed to load GET test data for: " + requestType, e);
        }
    }

 
    private static <T> void setFieldIfExists(T pojo, String fieldName, JsonNode node, Class<?> fieldType) {
        if (!node.has(fieldName)) return;

        try {
            Method setter = pojo.getClass().getMethod("set" + capitalize(fieldName), fieldType);

            if (fieldType == int.class) {
                setter.invoke(pojo, node.get(fieldName).asInt());
            } else if (fieldType == String.class) {
                setter.invoke(pojo, node.get(fieldName).asText());
            }
        } catch (NoSuchMethodException e) {
            // Field not present in POJO — skip
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field: " + fieldName + " on " + pojo.getClass().getSimpleName(), e);
        }
    }

  
    private static String capitalize(String str) {
        return (str == null || str.isEmpty()) ? str
                : str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
	


