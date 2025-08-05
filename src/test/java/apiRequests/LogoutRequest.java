package apiRequests;

import java.io.FileNotFoundException;

import apiEndPoints.ApiEndpoints;
import utils.SpecificationClass;
//import com.commonUtils.TestDataLoader;
import utils.jsonReader;
import context.ScenarioContext;
import com.fasterxml.jackson.databind.JsonNode;
import pojo.LoginPojo;
import pojo.LogoutPojo;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LogoutRequest  extends SpecificationClass{

	Response response;
	ScenarioContext context =ScenarioContext.getInstance();
	
	public LogoutRequest() throws FileNotFoundException {
		
	}
	
	public int getStatusCode() {
		LogoutPojo logout = context.get("LogoutPojo", LogoutPojo.class);
		return logout.getStatusCode();
	}
	public String getStatusLine() {
		LogoutPojo logout = context.get("LogoutPojo", LogoutPojo.class);
		return logout.getStatusText();
	}
	
	public void setGetLogoutRequest(String requestType) throws Exception 
	{
		LogoutPojo logout = jsonReader.loadTestDataForPostPut(requestType, LogoutPojo.class);
		context.set("LogoutPojo", logout);
	}
	
	public void GetLogoutRequest(String Scenario)
	{
		String EndPoint = ApiEndpoints.valueOf("APILogoutGet").getResources();
		if (Scenario.equals("LogoutInvalidEP")) 
			EndPoint = ApiEndpoints.valueOf("APILogoutGet").getResources() + "Invalid";

		response = RestAssured.given().spec(requestHeadersWithToken())
				.log().all()
				.get(EndPoint);       
		context.set("logoutResponse", response); 
	}
	
	public void NoAuthGetLogoutRequest()
	{
		response = RestAssured.given().spec(requestHeadersWithoutToken())
				.log().all()
				.get(ApiEndpoints.valueOf("APILogoutGet").getResources());       
		context.set("logoutResponse", response); 
	}
	
}
