package stepdefination;

import java.io.FileNotFoundException;

import apiRequests.LogoutRequest;
import apiResponse.CommonResponseValidation;
import context.ScenarioContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class UserLogoutStep {
	
	ScenarioContext context = ScenarioContext.getInstance();
	CommonResponseValidation Validation = new CommonResponseValidation();
	private Response logoutResponse;
	LogoutRequest logout;
	
	public  UserLogoutStep() throws FileNotFoundException {
		logout = new LogoutRequest();
	}
	@Given("Admin sets authorization to Bearer Token after logged in")
	public void admin_sets_authorization_to_bearer_token_after_logged_in() {
		
		//context.get("ADMINLOGIN_TOKEN");
	}

	@Given("Admin creates Logout {string} Request")
	public void admin_creates_logout_request(String requestType) throws Exception {
		logout.setGetLogoutRequest(requestType);
	}

	@When("Admin sends GET HTTPS {string} Logout Request")
	public void admin_sends_get_https_logout_request(String Scenario) {
		logout.GetLogoutRequest(Scenario);
	}

	@Then("Admin receives status {string} for Logout request")
	public void admin_receives_status_for_logout_request(String string) {
	logoutResponse = context.get("logoutResponse", Response.class);
		
		Validation.validateStatusCode(logoutResponse, logout.getStatusCode());
		Validation.validateStatusLine(logoutResponse, logout.getStatusLine());
		Validation.validateResponseTime(logoutResponse);
		Validation.assertAll();
	}



}
