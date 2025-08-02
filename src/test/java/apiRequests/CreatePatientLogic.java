package apiRequests;


	
	import pojo.DieticianData;
     import pojo.PatientPojo;
	import pojo.PatientVitalsPojo;
import pojo.tokenManager;
import utils.ConfigReader;
     import utils.jsonReader;

  import java.util.List;
  import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import apiEndPoints.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

	public class CreatePatientLogic {

		public static RequestSpecification convertPojoToMultipart(PatientPojo patient) {
		    return RestAssured
		        .given()
		        .contentType("multipart/form-data")
		        .accept(ContentType.JSON)
		        .multiPart("FirstName", patient.getFirstName())
		        .multiPart("LastName", patient.getLastName())
		        .multiPart("ContactNumber", patient.getContactNumber())
		        .multiPart("Email", patient.getEmail())
		        .multiPart("DateOfBirth", patient.getDateOfBirth())
		        .multiPart("Allergy", patient.getAllergy())
		        .multiPart("FoodPreference", patient.getFoodPreference())
		        .multiPart("CuisineCategory", patient.getCuisineCategory());
//		        .multiPart("Vitals.Weight", String.valueOf(patient.getVitals().getWeight()))
//		        .multiPart("Vitals.Height", String.valueOf(patient.getVitals().getHeight()))
//		        .multiPart("Vitals.Temperature", String.valueOf(patient.getVitals().getTemperature()))
//		        .multiPart("Vitals.SP", String.valueOf(patient.getVitals().getSP()))
//		        .multiPart("Vitals.DP", String.valueOf(patient.getVitals().getDP()));
		}
		
		public static Response createPatientThroughJSONData() {
			String JSON_Path= "src/test/resources/TestData/PatientData.json";
		    //String filePath = ConfigReader.getProperty("JSON_Path");
		    List<PatientPojo> patientdata = jsonReader.readJsonList(JSON_Path, PatientPojo.class);
		    System.out.println("Using JSON file: " + JSON_Path);

		    String token = tokenManager.getDieticianToken();

		    Response lastResponse = null;

		    for (PatientPojo patient : patientdata) {
		        try {
		            ObjectMapper mapper = new ObjectMapper();
		            System.out.println("Sending request for patient: " + mapper.writeValueAsString(patient));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        lastResponse = RestAssured
		            .given()
		            .baseUri(ConfigReader.getProperty("baseURL"))
		            .header("Authorization", "Bearer " + token)
		           // .header("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbWl0aGFAZXhhbXBsZS5jb20iLCJpYXQiOjE3NTQwOTM0MDcsImV4cCI6MTc1NDEyMjIwN30.QFkcMy-hXDg4fRquf-_fRG5Wk4sjq3D3j3Atr-Z2PNB9dI0HP7aUuK01KdHhoQCdYKDu7dW27XWWJ818zi753g")
		            .contentType("application/json")
		            .body(patient)
		            .post(ApiEndpoints.CreatePatientPost.getResources());

		        System.out.println("Status Code: " + lastResponse.getStatusCode());
		        System.out.println("Response Body:\n" + lastResponse.asPrettyString());

		        if (lastResponse.getStatusCode() != 201) {
		            throw new RuntimeException("Failed to create dietician: " + patient.getFirstName());
		        }
		    }
		    return lastResponse;  // always assigned if list not empty
		}


	}



