package stepdefination;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;

import apiRequests.DieticianLogic;
import baseAPI.BaseAPI;
import baseAPI.StoreIDs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import login.UserLoginManager;
import pojo.UserLoginInfo;

public class CreateDieticianSteps {
	
	private Response response;  // Shared across steps
    private String sheet;
    private String testCaseId;
	
	
	@SuppressWarnings("unused")
	@Given("Admin user loggedin1")
	public void admin_user_loggedin1() {
		Response response = BaseAPI.adminLogin();
		assertEquals(200, response.getStatusCode());
		System.out.println("Response Body:");
		System.out.println(response.getBody().asString());
		BaseAPI.storeAdminToken();
	}

	@When("post condition with valid data from sheet {string} with test case no {string} is provided1")
	public void post_condition_with_valid_data_from_sheet_with_test_case_no_is_provided1(String sheetName, String testCaseId) {
		
		this.sheet = sheet;
        this.testCaseId = testCaseId;
        //response = CreateDieticianLogic.createDietician(sheetName, testCaseId); // To create dietician by reading data from excel file
        response = DieticianLogic.createDieticianWithRandomlyGeneratedData(); //To create dietician with randomly generated data through code
        StoreIDs.storeCreatedDietician(response);
	    StoreIDs.storeDieticianEmailAsUserName(response);
	    StoreIDs.storeDieticianPassword(response);
	    
	    List<String> ids = StoreIDs.getAllIds();
	    System.out.println("All IDs: " + ids);
	    
	    //Storing Ditiecian credentials in UserLoginManager
	    String dieticianUsername = response.jsonPath().getString("Email");
		String dieticianPassword = response.jsonPath().getString("loginPassword");
		if (dieticianUsername != null && !dieticianUsername.isEmpty() && dieticianPassword != null
				&& !dieticianPassword.isEmpty()) {
			UserLoginInfo dieticianLoginInfo = new UserLoginInfo(dieticianUsername, dieticianPassword);
			UserLoginManager.addUserForScenario("VALID_DIETICIAN", dieticianLoginInfo);
		}
	
	}

	@Then("A new dietician should get created1")
	public void a_new_dietician_should_get_created1() {
	   
	    Assert.assertEquals(201,response.getStatusCode());
	    
		System.out.println(response.getBody().asString());
		System.out.println("Response Code is: " + response.getStatusCode());
	    
		
		
	    
	    
	}
	
}
