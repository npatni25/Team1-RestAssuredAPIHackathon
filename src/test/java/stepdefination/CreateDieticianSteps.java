package stepdefination;

import static org.testng.Assert.assertEquals;

import java.util.List;
import org.testng.Assert;

import apiRequests.CreateDieticianLogic;
import baseAPI.BaseAPI;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.jsonReader;
import utils.jsonReader.ScenarioContext;
import baseAPI.StoreIDs;

public class CreateDieticianSteps {

	private Response response; // Shared across steps
	private String sheet;
	private String testCaseId;

	@Given("Admin user loggedin")
	public void admin_user_loggedin() {
		Response response = BaseAPI.adminLogin();
		assertEquals(200, response.getStatusCode());
		System.out.println("Response Body:");
		System.out.println(response.getBody().asString());
		BaseAPI.storeAdminToken();
	}

	@Given("User is creating dietician {string}")
	public void user_is_creating_dietician(String scenarioName) {
		jsonReader.ScenarioContext.setScenarioName(scenarioName);
	}
	
	@When("post condition with valid data from json data file")
	public void post_condition_with_valid_data_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.createDieticianThroughJSONData1(scenarioName);
		StoreIDs.storeCreatedDietician(response);
		StoreIDs.storeDieticianEmailAsUserName(response);
		StoreIDs.storeDieticianPassword(response);
		List<String> ids = StoreIDs.getAllIds();
		System.out.println("All IDs: " + ids);
	}
	
	@When("post condition with auto generate valid data")
	public void post_condition_with_auto_generate_valid_data() {
		response = CreateDieticianLogic.createDieticianWithRandomlyGeneratedData();
		StoreIDs.storeCreatedDietician(response);
		StoreIDs.storeDieticianEmailAsUserName(response);
		StoreIDs.storeDieticianPassword(response);
		List<String> ids = StoreIDs.getAllIds();
		System.out.println("All IDs: " + ids);
	}

	@Then("A new dietician should get created")
	public void a_new_dietician_should_get_created() {
		System.out.println("Response Body:\n" + response.asPrettyString()); // Print response before assertion fails
		Assert.assertEquals(response.getStatusCode(), 201);
		System.out.println(response.getBody().asString());
		System.out.println("Response Code is: " + response.getStatusCode());
	}

	@Given("User is setting auth as {string}")
	public void user_is_setting_auth_as(String scenarioName) {
		jsonReader.ScenarioContext.setScenarioName(scenarioName);
	}

	@When("Post condition to create dietician with No Auth after providing valid data from json data file")
	public void post_condition_to_create_dietician_with_no_auth_after_providing_valid_data_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.createDietician_invalidAuth(scenarioName);
	}

	@Then("User should see Admin recieves {int} unauthorized")
	public void user_should_see_admin_recieves_unauthorized(Integer expectedStatusCode) {
		Assert.assertNotNull(response, "Response is null. API might have failed or not triggered.");
		int ActualstatusCode = response.getStatusCode();
		System.out.println("&&&&&&&&&&&&&&&&&&&&&" + expectedStatusCode);
		System.out.println("!!!!!!!!!!!!!!!sttaus code for no auth" + ActualstatusCode);
		Assert.assertEquals(ActualstatusCode, expectedStatusCode,"Expected 401 Unauthorized but got " + ActualstatusCode);
	}

	@When("Post condition to create dietician with Dietician token after providing valid data from json data file")
	public void post_condition_to_create_dietician_with_dietician_token_after_providing_valid_data_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.createDietician_DieticianToken(scenarioName);
	}

	@Then("User should see Admin recieves {int} forbidden for Dietician bearer token")
	public void user_should_see_admin_recieves_forbidden_for_dietician_bearer_token(Integer expectedStatusCode) {
		Assert.assertNotNull(response, "Response is blank");
		int ActualstatusCode = response.getStatusCode();
		System.out.println("&&&&&&&&&&&&&&&&&&&&&" + expectedStatusCode);
		System.out.println("!!!!!!!!!!!!!!!sttaus code for dietiecian bearer" + ActualstatusCode);
		Assert.assertEquals(ActualstatusCode, expectedStatusCode, "Expected 403 forbidden but got " + ActualstatusCode);
	}

	@When("Post condition to create dietician with Patient token after providing valid data from json data file")
	public void post_condition_to_create_dietician_with_patient_token_after_providing_valid_data_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.createDietician_PatientToken(scenarioName);
	}

	@Then("User should see Admin recieves {int} forbidden for Patient bearer token")
	public void user_should_see_admin_recieves_forbidden_for_patient_bearer_token(Integer expectedStatusCode) {
		Assert.assertNotNull(response, "Response is blank");
		int ActualstatusCode = response.getStatusCode();
		System.out.println("!!!!!!!!!!!!!!!sttaus code for patient bearer" + ActualstatusCode);
		Assert.assertEquals(ActualstatusCode, expectedStatusCode, "Expected 403 forbidden but got " + ActualstatusCode);
	}
	
	@When("Post condition to create dietician with invalid data from json data file")
	public void post_condition_to_create_dietician_with_invalid_data_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.createDieticianThroughJSONData1(scenarioName);
	}

	@Then("User should see Admin recieves {int} Bad Request error")
	public void user_should_see_admin_recieves_bad_request_error(Integer expectedStatusCode) {
		Assert.assertNotNull(response, "Response is blank");
		int ActualstatusCode = response.getStatusCode();
		Assert.assertEquals(ActualstatusCode, expectedStatusCode, "Expected 400 forbidden but got " + ActualstatusCode); 
	}
	
	@When("Post condition to create dietician with invalid PinCode from json data file")
	public void post_condition_to_create_dietician_with_invalid_pin_code_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.createDieticianThroughJSONData1(scenarioName);
	}

	@Then("User should see Admin recieves {string} error")
	public void user_should_see_admin_recieves_error(String expectedError) {
		Assert.assertNotNull(response, "Response is blank");
		boolean ActualError = response.asString().contains(expectedError);
		Assert.assertEquals(ActualError, true);
	}
	
	@Then("User should see Admin recieves same cont & dob error {string} error")
	public void user_should_see_admin_recieves_same_cont_dob_error_error(String expectedError) {
		Assert.assertNotNull(response, "Response is blank");
		boolean ActualError = response.asString().contains(expectedError);
		Assert.assertEquals(ActualError, true);
	}
	
	@When("Post condition to create dietician with invalid API request")
	public void post_condition_to_create_dietician_with_invalid_api_request() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.createDietician_InvalidAPIReq_PUT(scenarioName);
	}

	@Then("User should see Admin recieves {int} Method Not Allowed error")
	public void user_should_see_admin_recieves_method_not_allowed_error(Integer expectedStatusCode) {
		Assert.assertNotNull(response, "Response is blank");
		int ActualstatusCode = response.getStatusCode();
		Assert.assertEquals(ActualstatusCode, expectedStatusCode, "Expected 405 but got " + ActualstatusCode); 
	}
	
	@When("Post condition to create dietician with invalid End Point")
	public void post_condition_to_create_dietician_with_invalid_end_point() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.createDietician_InvalidEndPoint(scenarioName);
	}

	@Then("User should see Admin recieves {int} Not Found error")
	public void user_should_see_admin_recieves_not_found_error(Integer expectedStatusCode) {
		Assert.assertNotNull(response, "Response is blank");
		int ActualstatusCode = response.getStatusCode();
		Assert.assertEquals(ActualstatusCode, expectedStatusCode, "Expected 404 but got " + ActualstatusCode);
	}
	
	@Then("User should see Admin recieves {int} unsupported media type error")
	public void user_should_see_admin_recieves_unsupported_media_type_error(Integer expectedStatusCode) {
		Assert.assertNotNull(response, "Response is blank");
		int ActualstatusCode = response.getStatusCode();
		Assert.assertEquals(ActualstatusCode, expectedStatusCode, "Expected 415 but got " + ActualstatusCode);
	}
//////////////////////////--------Retrieve All Dietician---------/////////////////////////////////////////////////////////////////

	@When("Retrieving all dietician with no Auth {string}")
	public void retrieving_all_dietician_with_no_auth(String scenarioName) {
		
		response = CreateDieticianLogic.getAllDietician();
		System.out.println("Response is" +response.asPrettyString());		
	}

	@When("Retrieving all dietician")
	public void retrieving_all_dietician() {
		response = CreateDieticianLogic.getAllDietician();
		System.out.println("Response is" +response.asPrettyString());
	}
	
	@When("Retrieving all dietician reading from JSON data file")
	public void retrieving_all_dietician_reading_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.getAllDieticianJSON(scenarioName);
		System.out.println("Response is: " + response.asPrettyString());
	}

	@Then("User should see Admin recieves {int}")
	public void user_should_see_admin_recieves(Integer expectedStatusCode) {
		Assert.assertNotNull(response, "Response is blank");
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode.intValue());
	}
//////////////////////////--------Retrieve Dietician by ID---------/////////////////////////////////////////////////////////////////	
	
	@Given("Dietician already created")
	public void dietician_already_created() {
		response = CreateDieticianLogic.createDieticianWithRandomlyGeneratedData();
		StoreIDs.storeCreatedDietician(response);
		StoreIDs.storeDieticianEmailAsUserName(response);
		StoreIDs.storeDieticianPassword(response);
		List<String> ids = StoreIDs.getAllIds();
		System.out.println("All IDs: " + ids); 
	}
	
	@When("Retrieving dietician by ID reading from JSON data file")
	public void retrieving_dietician_by_id_reading_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.getDieticianById(scenarioName);
		System.out.println("Response is: " + response.asPrettyString());
	}
	
	@When("Retrieving dietician by ID reading invalid ID from JSON data file")
	public void retrieving_dietician_by_id_reading_invalid_id_from_json_data_file() {
		String scenarioName = ScenarioContext.getScenarioName();
		response = CreateDieticianLogic.getDieticianByInvaliId(scenarioName);
		System.out.println("Response is: " + response.asPrettyString()); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
