package stepdefination;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.testng.Assert;

import apiRequests.LoginRequests;
import com.fasterxml.jackson.core.JsonProcessingException;
import apiRequests.LoginRequests;
import apiRequests.LoginRequests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class UserLoginStep {
	LoginRequests login;
	private Response response;
	public UserLoginStep() throws FileNotFoundException {

		login = new LoginRequests();
	}
	

	@Given("Admin creates Login Valid request")
	public void admin_creates_login_valid_request() {
	    
	}

	@When("Admin sends HTTP Post login request")
	public void admin_sends_http_post_login_request() throws FileNotFoundException, JsonProcessingException {
	    login.postLoginRequest();
	}

	@Then("Admin receives {int} staus code with auto generated token")
	public void admin_receives_staus_code_with_auto_generated_token(Integer statuscode) {
	  Assert.assertEquals(statuscode, 200);
	}
	
	@Given("Admin creates invalid Login {string} Request")
	public void admin_creates_invalid_login_request(String string) {
	    
	}

	@When("Admin sends invalid {string} HTTPS Login Request")
	public void admin_sends_invalid_https_login_request(String string) throws JsonProcessingException, FileNotFoundException {
	  login.postInvalidLoginRequest();
	}

	@Then("Admin receives status {string} for Login request for failed test case")
	public void admin_receives_status_for_login_request_for_failed_test_case(String expectedStatusCode) {
		//assertEquals(Integer.parseInt(expectedStatusCode), response.getStatusCode());
	}



}