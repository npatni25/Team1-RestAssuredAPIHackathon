package context;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.java.Scenario;

public class ScenarioContext {

	private Map<String, String> scenarioData;
	private Scenario scenario;
	
	/***For accessing the obj and variables globally (Chaining) *****/
	 
	// for globally accessing the same object .Made it as static
	private static ScenarioContext instance;

	// making the constructor private to prevent obj creation from other class
	private ScenarioContext() {
		scenarioData = new HashMap<String, String>();
	}

	//provide a static method to access the  instance
	public static synchronized ScenarioContext getInstance() {
		if (instance == null) {
			instance = new ScenarioContext();
		}
		return instance;
	}
	
	/******* POST and PUT Request *****************/
	private Map<String, Object> context = new HashMap<>();

	public void set(String key, Object value) {
		context.put(key, value);
	}

	public Object get(String key) {
		return context.get(key);
	}
/* it is a generic method that retrieves an object from a map (likely Map<String, Object> context) 
 * and casts it to a specific type.
 */
	public <T> T get(String key, Class<T> clazz) {
		return clazz.cast(context.get(key));
	}


}
