package stepdefination;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static hooks.Hooks.currentScenario;


import org.testng.Assert;

import apiRequests.PatientService;
import baseAPI.BaseAPI;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import login.TokenManager;
import login.UserLoginManager;
import pojo.UserLoginInfo;

public class PatientStepDefinetions {

	@Given("Dietician is logged in")
	public void dietician_is_logged_in() {

		UserLoginInfo dietician = UserLoginManager.getUserForScenario("VALID_DIETICIAN");
		if (dietician == null || dietician.getUserName() == null || dietician.getUserName().isBlank()
				|| dietician.getPassword() == null || dietician.getPassword().isBlank()) {
			System.out.println("Dietician credentials are not valid or empty");
		}
		Response response = BaseAPI.userLogin(dietician.getUserName(), dietician.getPassword());
		assertEquals(200, response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());

		// store generated dietician token as token in TokenManager
		String dieticianToken = response.jsonPath().getString("token");
		//String dieticianLoginEmail = response.jsonPath().getString("loginUserEmail");
		assertNotNull("Token should not be null", dieticianToken);
		TokenManager.addTokenForUserLoginEmailOrScenario(currentScenario.getName(), dieticianToken);

	}

	@When("Dietician sends POST http request with valid data and token")
	public void dietician_sends_post_http_request_with_valid_data_and_token() {
		PatientService.createPatient(currentScenario.getName());
	}

	@Then("Dietician recieves {int} created and with response body.")
	public void dietician_recieves_created_and_with_response_body(Integer expectedResponseCode) {

		PatientService.validateResponseForScenario(currentScenario.getName());
	}

}
