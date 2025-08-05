package stepdefination;

import static hooks.Hooks.currentScenario;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.Assert;

import apiRequests.PatientService;
import baseAPI.BaseAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import login.TokenManager;
import login.UserLoginManager;
import pojo.PatientTestData;
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
		
		///2scenario
		@Given("Dietician creates POST request only with valid additional details in the form-data fields")
		public void dietician_creates_post_request_only_with_valid_additional_details_in_the_form_data_fields() {
			PatientTestData testData = new PatientTestData();
	        testData.setScenarioName("Dietician_OnlyAdditionalDetails");
	        testData.setAuthType("VALID_AUTH_DITICIAN_TOKEN");

	        testData.setHeight("180");
	        testData.setWeight("75");
	        testData.setTemperature("98.6");
	        testData.setFoodPreference("Vegan");
	        testData.setAllergy("Nuts");
	        testData.setCuisineCategory("Indian");
	        testData.setExpectedStatusCode(400);
		}

		@When("Dietician sends POST http request with endpoint")
		public void dietician_sends_post_http_request_with_endpoint() {
			String scenarioName = "Dietician_OnlyAdditionalDetails";
			response = PatientService.createPatient("Dietician_OnlyAdditionalDetails");
	 }

		@Then("Dietician receives {int} Bad Request")
		public void dietician_receives_bad_request(Integer int1) {
			PatientService.validateResponseForScenario(currentScenario.getName());

		}

	//noauth
		@Given("Dietician creates POST request by entering valid data into the form-data key and value fields.")
		public void dietician_creates_post_request_by_entering_valid_data_into_the_form_data_key_and_value_fields() {
			String scenarioName = "Check dietician able to create patient with valid data";
	        PatientService.createPatient(scenarioName);
		}

		//@When("Dietician send POST http request with endpoint")
		//public void dietician_send_post_http_request_with_endpoint() {
			 //response = PatientService.createPatient("Patient_NoAuth");
			//response = PatientService.createPatient_noAuth("NoAuth");

		//}

		@Then("Dietician receives {int} unauthorized")
		public void dietician_receives_unauthorized(Integer int1) {
			 //Assert.assertEquals(response.statusCode(), 401, "Expected status code 401 but got " + response.statusCode());
			PatientService.validateResponseForScenario(currentScenario.getName());
		}

	//patienttoken
	   @Given("Admin creates POST request by entering valid data into the form-data key and values")
	    public void admin_creates_post_request_by_entering_valid_data_into_the_form_data_key_and_values() {
		   //response = PatientService.createPatient("Admin_CreatePatient_Valid");
	   }

	   @When("Admin send POST http request with endpoint")
	   public void admin_send_post_http_request_with_endpoint() {
		   String scenarioName = "Check admin able to create patient with valid data and admin token";
		 response = PatientService.createPatient(scenarioName);
	  }

	   @Then("Admin receives {int} Forbidden")
	   public void admin_receives_forbidden(Integer expectedStatusCode) {
	       PatientService.validateResponseForScenario(currentScenario.getName());
	   }

	  
	  //adminbearer
	   @Given("Admin creates POST request by entering valid data into the form-data key and value fields.")
	   public void admin_creates_post_request_by_entering_valid_data_into_the_form_data_key_and_value_fields() {
		   response = PatientService.createPatient("scenarioName");
	   }


	   @Then("Admin recieves {int} forbidden")
	   public void admin_recieves_forbidden(Integer int1) {
		   PatientService.validateResponseForScenario(currentScenario.getName());
	   }
	   

	   @Given("Dietician creates POST request only by invalid mandatory details into the form-data key and value fields")
	   public void dietician_creates_post_request_only_by_invalid_mandatory_details_into_the_form_data_key_and_value_fields() {
		   String scenarioName = "Invalid_MandatoryDetails";
		   response = PatientService.createPatient("Invalid_MandatoryDetails");
	}
	   
	   @Given("Dietician creates POST request only by invalid additional details into the form-data key and value fields")
	   public void dietician_creates_post_request_only_by_invalid_additional_details_into_the_form_data_key_and_value_fields() {
		   response = PatientService.createPatient("InvalidAdditionalDetails");
	   }

	   
	   @Given("Dietician creates Post request by entering valid data into the form-data key and value fields.")
	   public void dietician_creates_put_request_by_entering_valid_data_into_the_form_data_key_and_value_fields() {
		   String scenarioName = "ValidData_InvalidMethod";
	       response = PatientService.createPatientWithInvalidMethod(scenarioName);
	   }

	   @When("Dietician sends Post http request with endpoint")
	   public void dietician_sends_put_http_request_with_endpoint() {
	       
	   }

	   @Then("Dietician receives {int} Method Not Allowed")
	   public void dietician_receives_method_not_allowed(Integer int1) {
		   Assert.assertEquals(response.getStatusCode(), 405, "Expected 405 Method Not Allowed"); 
	   }


	  @When("Dietician sent POST http request with invalid endpoint")
	  public void dietician_sent_post_http_request_with_invalid_endpoint() {
		  String scenarioName = "Dietician_CreatePatient_ValidData";
		  response=PatientService.createPatientWithInvalidEndpoint(scenarioName);
		  }

	  @Then("Dietician receives {int} not found")
	   public void dietician_receives_not_found(Integer int1) {
		  Assert.assertEquals(response.statusCode(), 404, "Expected 404 Not Found");
	 }

	  @Given("Dietician creates POST request by entering valid data into the form-data key and value fields and invalid content type")
	  public void dietician_creates_post_request_by_entering_valid_data_into_the_form_data_key_and_value_fields_and_invalid_content_type() {
		  response = PatientService.createPatientWithInvalidContentType("InvalidContentType");
	  }

	  //@Then("Dietician recieves {int} unsupported media type")
	  //public void dietician_recieves_unsupported_media_type(Integer int1) {
	//	  assertEquals(response.getStatusCode(), 415);
	 // }


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

	


	