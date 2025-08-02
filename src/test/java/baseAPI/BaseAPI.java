package baseAPI;
import static org.testng.Assert.assertNotNull;

import java.util.Map;

import apiEndPoints.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.tokenManager;
import utils.ConfigReader;
import utils.jsonReader;

public class BaseAPI {
	
	static Response response;
	public static String token;

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
	
	public static String  storeAdminToken()
	{
		Response response = adminLogin();
		String admin_token = response.jsonPath().getString("token");
        assertNotNull("Token should not be null", admin_token);
        tokenManager.setAdminToken(admin_token); //store genereated token as admin_token in tokenManager 

        System.out.println("Newly generated Admin Token is: " + admin_token);
		return admin_token;
		
	}
	
	
//	public static Response dieticianlogin()
//	{
//	    String username = StoreIDs.storeDieticianEmailAsUserName(response);
//	    String password = StoreIDs.storeDieticianPassword(response);
//
//	    RequestSpecification request = RestAssured
//	            .given()
//	            .header("content-type", "application/json")
//	            .body("{\"userLoginEmail\":\"" + username + "\",\"password\":\"" + password + "\"}");
//
//	    Response response = request.post(ApiEndpoints.APILoginPost.getResources());
//
//	    System.out.println(response.asPrettyString()); // ✅ This will now run
//
//	    return response;
//	}

  public static Response dieticianlogin()
{
    String loginEmail = "smitha@example.com";
    String loginPassword = "Tapi83";

    RequestSpecification request = RestAssured
            .given()
            .header("content-type", "application/json")
            .body("{\"userLoginEmail\":\"" + loginEmail + "\",\"password\":\"" + loginPassword + "\"}");

    Response response = request.post(ApiEndpoints.APILoginPost.getResources());

    System.out.println(response.asPrettyString());


    // Now store values from the response
    
    StoreIDs.storeDieticianToken(response);

    return response;
}


	public static String  storeDieticianToken()
	{
		Response response = dieticianlogin();
		String dieticiantoken = response.jsonPath().getString("token");
        assertNotNull("Token should not be null", dieticiantoken);
        tokenManager.setDieticianToken(dieticiantoken); //store genereated token as admin_token in tokenManager 

        System.out.println("Newly generated Admin Token is: " + dieticiantoken);
		return dieticiantoken;
		
	}
	
	

//	public static String storeDieticianToken() {
//        String email = StoreIDs.getAllUsernames().get(0);  // Most recent email
//        String password = StoreIDs.getAllPasswords().get(0);
//
//        Response loginResponse = RestAssured
//                .given()
//                .contentType("application/json")
//                //.body("{\"userLoginEmail\":\"" + email + "\",\"password\":\"" + password + "\"}")
//                .body("{ \"username\": \"dieticianUser\", \"password\": \"dieticianPass\" }")
//                .post("/login");
//		//return request.post(ApiEndpoints.APILoginPost.getResources());
//
//        String dieticianToken = loginResponse.jsonPath().getString("token");
//        tokenManager.setDieticianToken(dieticianToken);
//        System.out.println("Stored DieticianToken: " + dieticianToken);
//        System.out.println(loginResponse);
//        return dieticianToken;
//    }
//
//	
	 public static Response createpatient(Map<String, Object> patientData, String token) {
		    RequestSpecification request = RestAssured
		        .given()
		        .baseUri(ConfigReader.getProperty("BaseURL"))
		        .basePath(ApiEndpoints.CreatePatientPost.getResources())
		        .header("Authorization", "Bearer " + token)
		        .contentType("multipart/form-data");

		    for (Map.Entry<String, Object> entry : patientData.entrySet()) {
		        request.multiPart(entry.getKey(), String.valueOf(entry.getValue()));
		    }
          Response response = request.post(ApiEndpoints.CreateDietician.getResources());

    System.out.println("Create dietician response: " + response.asPrettyString());

    return response;
//		   // return request.post();
//		}

	
//   public Response sendPostRequestWithoutAuth(String endpoint, Object body) {
//        return RestAssured.given()
//            .contentType("application/json")
//            .body(body)
//            .log().all()
//        .when()
//            .post(ConfigReader.getProperty("Base_URL") + endpoint)
//        .then()
//            .log().all()
//            .extract().response();
//        }
	
	
	 }

}