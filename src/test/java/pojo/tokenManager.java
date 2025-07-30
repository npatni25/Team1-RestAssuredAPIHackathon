package pojo;

import baseAPI.BaseAPI;

public class tokenManager {

	
	private static String adminToken;
	
	public static void setAdminToken(String token) {
        adminToken = token;
    }

    public static String getAdminToken() {
        adminToken = BaseAPI.storeAdminToken();
    	return adminToken;
    }
}
