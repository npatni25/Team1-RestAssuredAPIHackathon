package apiRequests;
import apiEndPoints.ApiEndpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.SpecificationClass;
import utils.TestDataLoader;
import payload.LoginPayload;
import pojo.LoginPojo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.io.FileNotFoundException;

public class LoginRequests extends SpecificationClass{
	Response response;
	LoginPayload loginPayload = new LoginPayload();
	String token;
	
/*	String loginBody ="{\r\n"
			+ "  \"password\": \"test\",\r\n"
			+ "  \"userLoginEmail\": \"Team701@gmail.com\"\r\n"
			+ "}\r\n"
			+ "";
*/

	public LoginRequests() throws FileNotFoundException
	{
		
	}

	
	//Reading data from config.reader
	public void postLoginRequest() throws FileNotFoundException, JsonProcessingException {
	    // Prepare login payload
		

		

	    LoginPojo loginPojo = loginPayload.postLoginPayload();
	    
	 // Verify values
	    System.out.println("EMAIL: " + loginPojo.getuserLoginEmail());
	    System.out.println("PASSWORD: " + loginPojo.getPassword());
	    
	    ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(loginPojo);
		System.out.println("Final JSON payload: " + jsonBody);

	    // Send login request using specification without token
	    response = given()
	            .spec(SpecificationClass.requestSpecWithoutToken())
	            .header("Content-Type", "application/json")
	            .body(jsonBody)
	            //.body(loginBody)
	    .when()
	            .post(ApiEndpoints.APILoginPost.getResources())
	    .then()
	            .log().all()
	            .extract().response();
	    // Extract and store token 
	  token = response.jsonPath().getString("token");
	  
	  System.out.println(token);
	}
	
	public void postInvalidLoginRequest() throws FileNotFoundException, JsonProcessingException {
//For invalid login scenario:
		 LoginPojo loginPojo = loginPayload.postLoginPayload();
	  loginPojo = TestDataLoader.loadTestData("LoginInvalidCreds", LoginPojo.class);
	  ObjectMapper mapper = new ObjectMapper();
      String requestBody = mapper.writeValueAsString(loginPojo);
      System.out.println("Final JSON payload: " + requestBody);
      response = given()
          .spec(SpecificationClass.requestSpecWithoutToken())
          .body(requestBody)
          .when()
          .post(ApiEndpoints.APILoginPost.getResources());
      System.out.println(response);
	}
	
	
	
}