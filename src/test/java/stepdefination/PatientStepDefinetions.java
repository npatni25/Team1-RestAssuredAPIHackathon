package stepdefination;

import static hooks.Hooks.currentScenario;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import apiRequests.PatientService;
import baseAPI.BaseAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import login.TokenManager;
import login.UserLoginManager;
import pojo.UserLoginInfo;

public class PatientStepDefinetions {

	Response response;
	static int patientID;

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

		// store generated dietitian token as token in TokenManager
		String dieticianToken = response.jsonPath().getString("token");
		// String dieticianLoginEmail = response.jsonPath().getString("loginUserEmail");
		assertNotNull("Token should not be null", dieticianToken);
		TokenManager.addTokenForUserLoginEmailOrScenario("DIETICIAN_TOKEN", dieticianToken);

	}

	@When("Dietician sends POST http request with valid data and token")
	public void dietician_sends_post_http_request_with_valid_data_and_token() {
		response = PatientService.createPatient(currentScenario.getName());
	}

	@Then("Dietician recieves {int} created and with response body.")
	public void dietician_recieves_created_and_with_response_body(Integer expectedResponseCode) {

		PatientService.validateResponseForScenario(currentScenario.getName());
		patientID = response.jsonPath().getInt("patientId");
		System.out.println("patientId: " + patientID);
	}

	@Given("Dietician creates PUT request by entering valid data \\( Mandatory only) into the form-data key and value fields and valid patient ID")
	public void dietician_creates_put_request_by_entering_valid_data_mandatory_only_into_the_form_data_key_and_value_fields_and_valid_patient_id() {

	}

	@When("Dietician send PUT http request with endpoint")
	public void dietician_send_put_http_request_with_endpoint() {
		PatientService.updatePatient(currentScenario.getName(), patientID);
	}

	@Then("Dietician recieves {int} ok and with updated response body.")
	public void dietician_recieves_ok_and_with_updated_response_body(Integer int1) {
		PatientService.validateResponseForScenario(currentScenario.getName());

	}
	
	@Given("Dietician creates PUT request by entering valid data \\( Additional details only) into the form-data key and value fields and valid patient ID")
	public void dietician_creates_put_request_by_entering_valid_data_additional_details_only_into_the_form_data_key_and_value_fields_and_valid_patient_id() {
	  
	}
		
   @Given("Dietician creates PUT request by entering invalid data \\( Additional details only) into the form-data key and value fields and valid patient ID")
   public void dietician_creates_put_request_by_entering_invalid_data_additional_details_only_into_the_form_data_key_and_value_fields_and_valid_patient_id() {
		   
		}

		@Then("Dietician recieves {int} Bad request")
		public void dietician_recieves_bad_request(Integer int1) {
			PatientService.validateResponseForScenario(currentScenario.getName());
		}

		@Given("Dietician creates PUT request by entering invalid data \\( Additional details only) into the form-data key and value fields and invalid patient ID")
		public void dietician_creates_put_request_by_entering_invalid_data_additional_details_only_into_the_form_data_key_and_value_fields_and_invalid_patient_id() {
		   
		}

		@Then("Dietician recieves {int} not found")
		public void dietician_recieves_not_found(Integer int1) {
			PatientService.validateResponseForScenario(currentScenario.getName());
		}
		
		@Given("Dietician creates POST request by entering valid data into the form-data key and value fields and valid patient ID")
		public void dietician_creates_post_request_by_entering_valid_data_into_the_form_data_key_and_value_fields_and_valid_patient_id() {
		   
		}

		@When("Dietician send POST http request with endpoint")
		public void dietician_send_post_http_request_with_endpoint() {
			PatientService.updatePatientInvalidMethodWithPOST(currentScenario.getName(), patientID);
		}

		@Then("Dietician recieves {int} method not allowed")
		public void dietician_recieves_method_not_allowed(Integer int1) {
			PatientService.validateResponseForScenario(currentScenario.getName());
		}
		
		@When("Dietician sent PUT http request with invalid endpoint")
		public void dietician_sent_put_http_request_with_invalid_endpoint() {
			PatientService.updatePatient(currentScenario.getName(), patientID);
		}

		@Given("Dietician creates PUT request by entering valid data into the form-data key and value fields and valid patient ID with invalid content type")
		public void dietician_creates_put_request_by_entering_valid_data_into_the_form_data_key_and_value_fields_and_valid_patient_id_with_invalid_content_type() {
		   
		}

		@Then("Dietician recieves {int} unsupported media type")
		public void dietician_recieves_unsupported_media_type(Integer int1) {
			PatientService.validateResponseForScenario(currentScenario.getName());
		}



	}

	


	