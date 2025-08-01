package apiRequests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import utils.ConfigReader;
import utils.ExcelReader;
import utils.jsonReader;

import java.util.Map;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import apiEndPoints.ApiEndpoints;
import baseAPI.StoreIDs;
import pojo.DieticianData;
import pojo.tokenManager;

public class CreateDieticianLogic {
	
	static Response response;
	
	
	public static Response createDietician(String sheetName, String testCaseId) {
	    String token = tokenManager.getAdminToken();
	    
	    Map<String, String> rowData = ExcelReader.getTestDataByTestCaseId(sheetName, testCaseId);

	    JSONObject requestBody = new JSONObject();
	    requestBody.put("Firstname", rowData.get("Firstname"));
	    requestBody.put("Lastname", rowData.get("Lastname"));
	    requestBody.put("Email", rowData.get("Email"));
	    requestBody.put("ContactNumber", rowData.get("ContactNumber"));
	    requestBody.put("DateOfBirth", rowData.get("DateOfBirth"));
	    requestBody.put("Education", rowData.get("Education"));
	    requestBody.put("HospitalCity", rowData.get("HospitalCity"));
	    requestBody.put("HospitalName", rowData.get("HospitalName"));
	    requestBody.put("HospitalPincode", rowData.get("HospitalPincode"));
	    requestBody.put("HospitalStreet", rowData.get("HospitalStreet"));

	    Response response = given()
	            .header("Authorization", "Bearer " + token)
	            .contentType("application/json")
	            .body(requestBody.toString())
	            .post(ApiEndpoints.CreateDietician.getResources());

	    StoreIDs.storeCreatedDietician(response);
	    StoreIDs.storeDieticianPassword(response);
	    StoreIDs.storeDieticianEmailAsUserName(response);
	    return response;
	}


	
//	public static Response createDieticianThroughJSONData() {
//        String filePath = ConfigReader.getProperty("JSON_Path");
//        List<DieticianData> dieticians = jsonReader.readJsonList(filePath, DieticianData.class);
//        System.out.println("Using JSON file: " + filePath);
//
//        String token = tokenManager.getAdminToken();
//
//        for (DieticianData dietician : dieticians) {
//            try {
//                ObjectMapper mapper = new ObjectMapper();
//                System.out.println("Sending request for dietician: " + mapper.writeValueAsString(dietician));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            Response response = RestAssured
//                .given()
//                .header("Authorization", "Bearer " + token)
//                .contentType("application/json")
//                .body(dietician)
//                .post(ApiEndpoints.CreateDietician.getResources());
//
//            System.out.println("Status Code: " + response.getStatusCode());
//            System.out.println("Response Body:\n" + response.asPrettyString());
//
//            if (response.getStatusCode() != 201) {
//                throw new RuntimeException("Failed to create dietician: " + dietician.getFirstname());
//            }
//        }
//		return response;
//    }

	public static Response createDieticianThroughJSONData() {
	    String filePath = ConfigReader.getProperty("JSON_Path");
	    List<DieticianData> dieticians = jsonReader.readJsonList(filePath, DieticianData.class);
	    System.out.println("Using JSON file: " + filePath);

	    String token = tokenManager.getAdminToken();

	    Response lastResponse = null;

	    for (DieticianData dietician : dieticians) {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            System.out.println("Sending request for dietician: " + mapper.writeValueAsString(dietician));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        lastResponse = RestAssured
	            .given()
	            .header("Authorization", "Bearer " + token)
	            .contentType("application/json")
	            .body(dietician)
	            .post(ApiEndpoints.CreateDietician.getResources());

	        System.out.println("Status Code: " + lastResponse.getStatusCode());
	        System.out.println("Response Body:\n" + lastResponse.asPrettyString());

	        if (lastResponse.getStatusCode() != 201) {
	            throw new RuntimeException("Failed to create dietician: " + dietician.getFirstname());
	        }
	    }
	    return lastResponse;  // always assigned if list not empty
	}


	
	public static Response createDieticianWithRandomlyGeneratedData() 
	{
		String randomFirstName = generateRandomName(6);  
        String randomLastName = generateRandomName(7);   
        String randomEmail = randomFirstName.toLowerCase() + "." + randomLastName.toLowerCase() + "@test.com";
        String randomContactNumber = "9" + getRandomNumber(9);
        String randomDOB = generateRandomDOB("1975-01-01", "1995-12-31");
		
		JSONObject requestBody = new JSONObject();
		requestBody.put("ContactNumber", randomContactNumber);
		requestBody.put("DateOfBirth",randomDOB );
	    requestBody.put("Education", "MBBS" );
	    requestBody.put("Email",randomEmail );
	    requestBody.put("Firstname", randomFirstName);
	    requestBody.put("Lastname", randomLastName);
	    requestBody.put("HospitalCity", "York" );
	    requestBody.put("HospitalName", "City Hospital" );
	    requestBody.put("HospitalPincode","123456" );
	    requestBody.put("HospitalStreet", "5th Ave" );
		
	    String token = tokenManager.getAdminToken();
		Response response = given()
		    .header("Authorization", "Bearer " + token)
		    .contentType("application/json")
		    .body(requestBody.toString())
		    .post("/dietician");
			
		return response;
		
	}
	
	 // Generate random name with alphabets
    private static String generateRandomName(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    private static String getRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private static String generateRandomDOB(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = formatter.parse(startDateStr);
            Date endDate = formatter.parse(endDateStr);

            long randomMillis = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
            Date randomDate = new Date(randomMillis);

            SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
            return isoFormatter.format(randomDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate random DOB", e);
        }
    }


	
}
