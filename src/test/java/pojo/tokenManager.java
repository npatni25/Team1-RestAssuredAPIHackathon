package pojo;

import baseAPI.BaseAPI;

public class tokenManager {

	
	private static String adminToken;
	private static String dieticianUsername;
	private static String dieticianPassword;
	private static String dieticianToken;
	
	public static void setAdminToken(String token) {
        adminToken = token;
    }

    public static String getAdminToken() {
        adminToken = BaseAPI.storeAdminToken();
    	return adminToken;
    }
    
    public static void setDieticianUsername(String username) {
        dieticianUsername = username;
    }

    public static String getDieticianUsername() {
        return dieticianUsername;
    }

    public static void setDieticianPassword(String password) {
        dieticianPassword = password;
    }

    public static String getDieticianPassword() {
        return dieticianPassword;
    }

    public static void setDieticianToken(String token) {
        dieticianToken = token;
    }

    public static String getDieticianToken() {
        return dieticianToken;
    }
}
