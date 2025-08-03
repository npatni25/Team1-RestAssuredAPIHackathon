package login;

import java.util.HashMap;
import java.util.Map;

import pojo.UserLoginInfo;

public class UserLoginManager {
	
	private static Map<String, UserLoginInfo> userLogins = new HashMap<String, UserLoginInfo>();
	
	public static UserLoginInfo getUserForScenario(String scenario) {
		return userLogins.get(scenario);
	}

	public static void addUserForScenario(String scenario, UserLoginInfo userLoginInfo) {
		userLogins.put(scenario, userLoginInfo);
	}
}
