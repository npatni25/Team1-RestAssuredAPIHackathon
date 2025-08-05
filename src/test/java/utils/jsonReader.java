//package utils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.InputStream;
//import java.util.List;
//
//public class jsonReader {
//
//	public static <T> List<T> readJsonList(String filePath, Class<T> clazz) {
//	    ObjectMapper mapper = new ObjectMapper();
//	    List<T> list = null;
//
//	 
//	    try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
//
//	        if (is == null) {
//	            throw new RuntimeException("Resource not found: " + filePath);
//	        }
//	        // Create a TypeReference for List<DieticianData>
//	        list = mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        throw new RuntimeException("Failed to read JSON list from resource: " + filePath);
//	    }
//	    return list;
//	}
//	
//	public static class ScenarioContext {
//	    private static ThreadLocal<String> scenarioName = new ThreadLocal<>();
//
//	    public static void setScenarioName(String name) {
//	        scenarioName.set(name);
//	    }
//
//	    public static String getScenarioName() {
//	        return scenarioName.get();
//	    }
//
//	    public void clear() {
//	        scenarioName.remove();
//	    }
//	}
//}


package utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
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
}
//
