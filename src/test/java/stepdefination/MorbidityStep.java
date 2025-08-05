package stepdefination;

import java.io.FileNotFoundException;

import org.testng.asserts.SoftAssert;
import apiRequests.MorbidityRequest;
import apiResponse.CommonResponseValidation;

import apiRequests.LoginRequests;
import context.ScenarioContext;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class MorbidityStep {
	ScenarioContext context = ScenarioContext.getInstance();
	private Response MorbidityResponse;
	LoginRequests logRequest;
	MorbidityRequest morbidityRequest;
	SoftAssert softAssert;
	Response getProgramFilterResponse;
	CommonResponseValidation ResponseValidation = new CommonResponseValidation();
	

	public MorbidityStep() throws FileNotFoundException {
		logRequest = new LoginRequests();
		morbidityRequest = new MorbidityRequest();
	}
	
	@Given("Admin sets Authorization to Bearer Token for morbidity")
	public void admin_sets_authorization_to_bearer_token_for_morbidity() throws JsonProcessingException, FileNotFoundException {
		logRequest.postLoginRequest();
		
	}

	@Given("Admin creates GETAll request {string} for Morbidity Module")
	public void admin_creates_get_all_request_for_morbidity_module(String requestType) throws Exception {
		morbidityRequest.setGetMorbidityRequest(requestType);
	}

	@When("Admin sends HTTPS Request with endpoint GETAll for Morbidity Module {string}")
	public void admin_sends_https_request_with_endpoint_get_all_for_morbidity_module(String requestType) {
		Response response=morbidityRequest.sendGetMorbidityReqWithOutBody(requestType);
		context.set("morbidityResponse", response);
	}

	@Then("Admin receives statuscode  {string} for Morbidity Module get all")
	public void admin_receives_statuscode_for_morbidity_module_get_all(String StatusCode) {
		MorbidityResponse = context.get("morbidityResponse", Response.class);
		ResponseValidation.validateStatusCode(MorbidityResponse, morbidityRequest.getMorbidityStatusCode());
		ResponseValidation.validateStatusLine(MorbidityResponse, morbidityRequest.getMorbidityStatusLine());
		ResponseValidation.validateResponseTime(MorbidityResponse);

		if (!StatusCode.equals("404"))
			ResponseValidation.validateContentType(MorbidityResponse);
		ResponseValidation.assertAll();
	}

	@Given("Admin creates GET Request by {string} for Morbidity module")
	public void admin_creates_get_request_by_for_morbidity_module(String requestType) throws Exception {
		morbidityRequest.setGetMorbidityRequest(requestType);
	}

	@When("Admin sends GET Request by  {string} MorbidityName for Morbidity Module")
	public void admin_sends_get_request_by_morbidity_name_for_morbidity_module(String requestType) throws Exception {
		
		Response response=morbidityRequest.sendGetMorbidityReqWithOutBody(requestType);
		 context.set("morbidityResponse", response);
	}

	@Then("Admin gets the Morbidity details of that MorbidityName with status  {string}")
	public void admin_gets_the_morbidity_details_of_that_morbidity_name_with_status(String StatusCode) {
		
		MorbidityResponse = context.get("morbidityResponse", Response.class);
		ResponseValidation.validateStatusCode(MorbidityResponse, morbidityRequest.getMorbidityStatusCode());
		ResponseValidation.validateStatusLine(MorbidityResponse, morbidityRequest.getMorbidityStatusLine());
		ResponseValidation.validateResponseTime(MorbidityResponse);

		if (!StatusCode.equals("404"))
			ResponseValidation.validateContentType(MorbidityResponse);
		ResponseValidation.assertAll();
	}


}
