package baseAPI;
import static org.testng.Assert.assertNotNull;

import apiEndPoints.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.tokenManager;
import utils.ConfigReader;

public class BaseAPI {

	static {
        ConfigReader.loadProperties();  
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
    }
	
	public static Response adminLogin()
	{
		String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");
		
        RequestSpecification request = RestAssured
				.given()
				.header("content-type","application/json")
				.body("{\"userLoginEmail\":\"" + username + "\",\"password\":\"" + password + "\"}");
		
        return request.post(ApiEndpoints.APILoginPost.getResources());
		
	}
	
	public static Response userLogin(String username, String password)
	{
        RequestSpecification request = RestAssured
				.given()
				.header("content-type","application/json")
				.body("{\"userLoginEmail\":\"" + username + "\",\"password\":\"" + password + "\"}");
		
        return request.post(ApiEndpoints.APILoginPost.getResources());
		
	}
	
	public static String  storeAdminToken()
	{
		Response response = adminLogin();
		String admin_token = response.jsonPath().getString("token");
        assertNotNull("Token should not be null", admin_token);
        tokenManager.setAdminToken(admin_token); //store genereated token as admin_token in tokenManager 

        System.out.println("Newly generated Admin Token is: " + admin_token);
		return admin_token;
		
	}
	
	
	
	
}
