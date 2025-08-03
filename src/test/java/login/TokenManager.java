package login;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {

	
	private static Map<String, String> tokens = new HashMap<String, String>();
	
	public static String getTokenForUserLoginEmailOrScenario(String userLoginEmail) {
		return tokens.get(userLoginEmail);
	}

	public static void addTokenForUserLoginEmailOrScenario(String userLoginEmailOrScenario, String token) {
		tokens.put(userLoginEmailOrScenario, token);
	}
}
