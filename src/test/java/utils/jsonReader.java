package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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


	public static String readJsonFileAsString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
	}

   

}

