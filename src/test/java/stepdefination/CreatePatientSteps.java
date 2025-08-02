//package stepdefination;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotNull;
//
//import java.util.List;
//import java.util.Map;
//
//import org.bson.json.JsonReader;
//
//import baseAPI.BaseAPI;
//import baseAPI.StoreIDs;
//import apiEndPoints.ApiEndpoints;
//import io.cucumber.java.en.*;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import pojo.PatientPojo;
//import pojo.PatientVitalsPojo;
//import pojo.tokenManager;
//import utils.ConfigReader;
//import utils.jsonReader;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class CreatePatientSteps {
//
//    private Response response;
//    private String token;
//    private RequestSpecification request;
//
//    @Given("Dietician sets the bearer token and creates POST request with valid form-data \\(mandatory and additional details)")
//    public void dietician_sets_token_and_creates_post_request_with_form_data() throws Exception {
//        // Login and retrieve token
//        Response loginResponse = BaseAPI.dieticianlogin();
//        token = tokenManager.getDieticianToken(); // Token stored during login
//        
//
////        // Step 2: Read JSON directly into PatientPojo list
////        List<PatientPojo> testDataList = jsonReader.readJsonList("TestData/PatientData.json", PatientPojo.class);
////        PatientPojo patient = testDataList.get(0); // Pick first patient record
////
////        // Step 3: Prepare the request
////        request = RestAssured
////                .given()
////                .header("Authorization", "Bearer " + token)
////                .contentType("application/json")
////                .body(patient); // No manual setters neede
//        
//        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
//       
//                String filePath = "src/test/resources/TestData/PatientData.json";
//                System.out.println("Reading patient request data from: " + filePath);
//                String requestBody = jsonReader.readJsonFileAsString(filePath);
//                System.out.println("printing request data from: " + requestBody);
//                
//                ObjectMapper mapper = new ObjectMapper();
//                Map<String, Object> patientDataMap = mapper.readValue(requestBody, Map.class);
//        
//                RequestSpecification reqSpec = RestAssured
//                    .given()
//                        .header("Authorization", "Bearer " + tokenManager.getDieticianToken())  // ✅ Use the token dynamically
//                        .contentType("multipart/form-data")
//                        .accept(ContentType.JSON);
//        
//                for (Map.Entry<String, Object> entry : patientDataMap.entrySet()) {
//                	// if (!entry.getKey().equals("Vitals")) {                   
//                		 reqSpec.multiPart(entry.getKey(), entry.getValue().toString());
//                }
//        
//                	 
//                	// Add "Vitals" as JSON string
//                	// Object vitalsObj = patientDataMap.get("Vitals");
//                	// String vitalsJson = new ObjectMapper().writeValueAsString(vitalsObj);
//                	// request.multiPart("PatientVitals", vitalsJson);
//                response = reqSpec.when().post("/patient");
//                //System.out.println("Create Patient Response:\n" + response.asPrettyString());
//        
//                //StoreIDs.storeCreatedPatient(response);
//                List<String> ids = StoreIDs.getAllIds();
//        	    System.out.println("All IDs: " + ids);
//        
//    }
//
//    @When("Dietician sends POST HTTP request to the create patient endpoint")
//    public void dietician_sends_post_http_request() {
//        //response = request.post(ApiEndpoints.CreatePatientPost.getResources());
//    	response = request.when().post("/patient");
//    }
//
//    @Then("Dietician receives {int} Created status and response body with auto-generated patient ID and login password")
//    public void dietician_receives_created_status_and_response_body_with_auto_generated_patient_id_and_login_password(Integer int1) {
//        assertEquals(response.statusCode(), 201, "Expected status code 201 Created");
//
//        String responseBody = response.getBody().asString();
//        System.out.println("Response Body:\n" + responseBody);
//
//        // Extract and store Patient ID and Password if present
//        String patientId = response.jsonPath().getString("patientId");
//        String password = response.jsonPath().getString("password");
//
//        assertNotNull(patientId, "Patient ID should not be null");
//        assertNotNull(password, "Password should not be null");
//
//       // StoreIDs.storeCreatedPatient("PatientID", patientId);
//        //StoreIDs.storeValue("PatientPassword", password);
//    }
//}
//
//






























package stepdefination;

import baseAPI.BaseAPI;
import baseAPI.StoreIDs;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.PatientPojo;
import pojo.PatientVitalsPojo;
import pojo.tokenManager;
import utils.ConfigReader;
import utils.LoggerLoad;
import utils.jsonReader;


import java.io.IOException;

import java.util.Map;

import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apiRequests.CreatePatientLogic;


public class CreatePatientSteps {

	//List<PatientPojo> patientDataList;
	private Map<String, Object> requestData;
    Response response;
    private String token;
    PatientPojo patient;
    

    @Given("Dietician is loggedin")
    public void dietician_is_loggedin() {
		//BaseAPI.dieticianlogin();
		 Response loginResponse = BaseAPI.dieticianlogin();
         token = tokenManager.getDieticianToken(); // Token stored during login
       
    }

    @When("Dietician send POST http request with endpoint")
    public void dietician_send_post_http_request_with_endpoint() throws JsonProcessingException {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
        String filePath = ConfigReader.getProperty("JSON_Path");

        //String filePath = "src/test/resources/TestData/PatientData.json";
        System.out.println("Reading patient request data from: " + filePath);
        String requestBody = jsonReader.readJsonFileAsString(filePath);//this line read entire json file from give filepath
        System.out.println("printing request data from: " + requestBody);
        
        ObjectMapper mapper = new ObjectMapper();
        patient=new PatientPojo();
        // patient = mapper.readValue(requestBody, PatientPojo.class);
      patient = mapper.readValue(requestBody, PatientPojo.class);
 
       System.out.println(patient);
        RequestSpecification reqSpec = 
             CreatePatientLogic.convertPojoToMultipart(patient)
        		   
                .header("Authorization", "Bearer " + tokenManager.getDieticianToken());   // ✅ Use the token dynamically
                
                //.contentType("multipart/form-data");
                //.contentType(ContentType.JSON)
                //.accept(ContentType.JSON);
     


//        for (Map.Entry<String, Object> entry : patientDataMap.entrySet()) {
//            reqSpec.multiPart(entry.getKey(), entry.getValue().toString());
//        }

       response = reqSpec.when().post("/patient");
        System.out.println("Create Patient Response:\n" + response.asPrettyString());

        StoreIDs.storeCreatedPatient(response);
    }

    
    @Then("Dietician recieves {int}")
    public void dietician_recieves(Integer expectedstatusCode) {
    	response.then().log().all().statusCode(expectedstatusCode);
    	
	    }
}


