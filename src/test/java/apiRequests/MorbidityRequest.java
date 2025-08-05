package apiRequests;

import java.io.FileNotFoundException;

import apiEndPoints.ApiEndpoints;
import utils.SpecificationClass;

import utils.jsonReader;
import context.ScenarioContext;
import context.TextContext;
import com.fasterxml.jackson.databind.JsonNode;
import pojo.MorbidityPojo;
import pojo.MorbidityPojo;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class MorbidityRequest extends SpecificationClass {
	
	Response response;
	String paramForGetEndpoint;
	ScenarioContext context = ScenarioContext.getInstance();

	public MorbidityRequest() throws FileNotFoundException {

	}
	
	public int getMorbidityStatusCode() {
		MorbidityPojo MorbidityStatusCode = (MorbidityPojo) context.get("MorbidityPojo");
		return MorbidityStatusCode.getStatusCode();
	}

	public String getMorbidityStatusLine() {
		MorbidityPojo MorbidityStatusLine = (MorbidityPojo) context.get("MorbidityPojo");
		return MorbidityStatusLine.getStatusText();
	}

	public String getMorbidityTestName() throws IllegalStateException {
		MorbidityPojo testname = (MorbidityPojo) context.get("MorbidityPojo");
		return testname.getmorbidityName();
	}
	
	/****************** GET ALL Morbidity *************************/
	public JsonNode setGetMorbidityRequest(String requestType) throws Exception {
		JsonNode getTestData = jsonReader.loadTestDataForGet(requestType);
		return getTestData;
	}

	public Response sendGetMorbidityReqWithOutBody(String Scenario) {
		String EndPoint = ApiEndpoints.valueOf("GetAllMorbidities").getResources();
		if (Scenario.equals("GetAllMorbidityInValidEP"))
			EndPoint = ApiEndpoints.valueOf("GetAllMorbidities").getResources() + "Invalid";

		Response response = RestAssured.given().spec(requestHeadersWithToken()).get(EndPoint);
		MorbidityPojo morbidity = new MorbidityPojo();
		morbidity.setStatusCode(response.getStatusCode());
		morbidity.setStatusText(response.getStatusLine());
		context.set("MorbidityPojo", morbidity); // Store in context
		context.set("MorbidityResponse", response);
		return response;
	}

	/****************** GET by MorbidityName Request 
	 * @return ****************/

	public Response sendGetMorbidityByNameReqWithOutBody(String Scenario) {
		String morbidityName = TextContext.getMorbidityName();
		String EndPoint = ApiEndpoints.valueOf("GetMorbidityByTestname").getResources();
		if (Scenario.equals("GetMorbidityByNameInValidEP")) {
			EndPoint = EndPoint + "Invalid";
		}

		response = RestAssured.given().spec(requestHeadersWithToken()).pathParam("morbidityName", morbidityName).log().all()
				.get(EndPoint);
		context.set("MorbidityResponse", response);
		MorbidityPojo morbidity = new MorbidityPojo();
		morbidity.setStatusCode(response.getStatusCode());
		morbidity.setStatusText(response.getStatusLine());
		context.set("MorbidityPojo", morbidity);
		TextContext.setMorbidityName(morbidityName);
		return response;

	}
}
