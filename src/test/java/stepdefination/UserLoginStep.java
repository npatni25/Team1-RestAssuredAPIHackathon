package stepdefination;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.testng.Assert;

import apiRequests.LoginRequests;

import apiResponse.CommonResponseValidation;
import utils.ConfigReader;
import context.ScenarioContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import apiRequests.LoginRequests;
import apiRequests.LoginRequests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class UserLoginStep {
	ScenarioContext context = ScenarioContext.getInstance();
	CommonResponseValidation Validation = new CommonResponseValidation();
	LoginRequests login;
	private Response loginResponse;
	String userLoginSchema = ConfigReader.getProperty("userLoginSchema");
	public UserLoginStep() throws FileNotFoundException {

		login = new LoginRequests();
	}

	@Given("Admin creates Login Valid request")
	public void admin_creates_login_valid_request() {
		context.set("ADMINLOGIN_TOKEN",null);
	}
	@Given("Admin creates Login Valid requests.")
	public void admin_creates_login_valid_requests() {
		context.set("ADMINLOGIN_TOKEN",null); 
	}
	@Given("Admin creates invalid Login {string} Request")
	public void admin_creates_invalid_login_request(String requestType) throws Exception {
		login.setLoginRequest(requestType);
	}

	@When("Admin sends invalid {string} HTTPS Login Request")
	public void admin_sends_invalid_https_login_request(String string) throws JsonProcessingException, FileNotFoundException {
	  login.postInvalidLoginRequest();
	}

	@Then("Admin receives status {string} for Login request for failed test case")
	public void admin_receives_status_for_login_request_for_failed_test_case(String Code) {
		//assertEquals(Integer.parseInt(expectedStatusCode), response.getStatusCode());
		//assertEquals(Integer.parseInt(expectedStatusCode), response.getStatusCode());
		
				loginResponse = context.get("loginResponse", Response.class);
				 
				Validation.validateStatusCode(loginResponse, login.getStatusCode());
				Validation.validateStatusLine(loginResponse, login.getStatusLine());
				Validation.validateResponseTime(loginResponse);	
				if(!Code.equals("404"))
					Validation.validateContentType(loginResponse);
			//	Validation.validateJsonSchema(loginResponse, userLoginSchema);
				Validation.assertAll();
				
	}

	@When("Admin sends HTTP Post login request.")
	public void admin_sends_http_post_login_request() throws JsonProcessingException, FileNotFoundException {
		 login.postLoginRequest();
	}
	@Then("Admin receives {int} staus code with auto generated token.")
	public void admin_receives_staus_code_with_auto_generated_token(Integer statuscode) {
		Assert.assertEquals(statuscode, 200);
		  loginResponse = context.get("loginResponse", Response.class);
		  String token = loginResponse.jsonPath().getString("token");
		    if (token == null || token.isEmpty()) {
		        throw new RuntimeException("Token was not found in the login response!");
		    }

		    // Store using ScenarioContext directly
		    context.set("ADMINLOGIN_TOKEN", token);
			Validation.validateStatusCode(loginResponse, 200);
			Validation.validateStatusLine(loginResponse, "HTTP/1.1 200 OK");
			Validation.validateResponseTime(loginResponse);
			Validation.validateJsonSchema(loginResponse, userLoginSchema);
			Validation.assertAll();
	    
	}


}
