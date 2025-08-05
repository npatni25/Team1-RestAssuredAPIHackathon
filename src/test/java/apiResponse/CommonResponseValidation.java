package apiResponse;

import org.testng.asserts.SoftAssert;

import utils.ConfigReader;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class CommonResponseValidation {
	
	private SoftAssert softAssert = new SoftAssert();
	
	
	 public void validateStatusCode(Response response, int expectedStatusCode) {
		 softAssert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch!");
	    }

	 public void validateStatusLine(Response response, String expectedMessage) {
	        String actualMessage = response.getStatusLine();
	        softAssert.assertEquals(actualMessage, expectedMessage, "Status Line mismatch!"); 
	    } 

	    public void validateStatusMessage(Response response, String expectedMessage) {
	        String actualMessage = response.jsonPath().getString("message");
	        softAssert.assertEquals(actualMessage, expectedMessage, "Status message mismatch!");
	    }

	    public void validateContentType(Response response) {
	    	String expectedMessage = ConfigReader.getProperty("contentType");
	        String actualContentType = response.getHeader("Content-Type");
	        softAssert.assertEquals(actualContentType,  expectedMessage, "Content-Type mismatch!");
	    }

	    public void validateJsonSchema(Response response, String schemaFilePath) {
	    	 try {
	    		 response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaFilePath));
	             softAssert.assertTrue(true, "JSON schema validation passed");

	         } catch (Exception e) {
	             softAssert.fail("JSON schema validation failed: " + e.getMessage());
	         }

	    }

	    public void validateResponseTime(Response response) {
	    	softAssert.assertTrue(response.getTime() < 4000, "Response time exceeds 4000ms!");
	    }
	    public void assertAll() {
	        softAssert.assertAll();
	    }

}
