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

//	@When("post condition with valid data from sheet {string} with test case no {string} is provided")
//	public void post_condition_with_valid_data_from_sheet_with_test_case_no_is_provided(String sheetName,
//			String testCaseId) {
//
//		this.sheet = sheet;
//		this.testCaseId = testCaseId;
//		// response = CreateDieticianLogic.createDieticianExcel(sheetName, testCaseId); // To
//		// create dietician by reading data from excel file
//		// response = CreateDieticianLogic.createDieticianWithRandomlyGeneratedData();
//		// //To create dietician with randomly generated data through code
//		response = CreateDieticianLogic.createDieticianThroughJSONData();
//		StoreIDs.storeCreatedDietician(response);
//		StoreIDs.storeDieticianEmailAsUserName(response);
//		StoreIDs.storeDieticianPassword(response);
//
//		List<String> ids = StoreIDs.getAllIds();
//		System.out.println("All IDs: " + ids);
//
//	}

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

}
