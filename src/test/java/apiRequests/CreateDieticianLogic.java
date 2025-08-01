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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import apiEndPoints.ApiEndpoints;
import baseAPI.StoreIDs;
import pojo.DieticianData;
import pojo.tokenManager;

public class CreateDieticianLogic {

	static Response response;

	/////////////////////////////Positive - Create Dietician with valid data reading from Excel data file//////////////////////////	

	//	public static Response createDieticianExcel(String sheetName, String testCaseId) {
	//		String token = tokenManager.getAdminToken();
	//
	//		Map<String, String> rowData = ExcelReader.getTestDataByTestCaseId(sheetName, testCaseId);
	//
	//		JSONObject requestBody = new JSONObject();
	//		requestBody.put("Firstname", rowData.get("Firstname"));
	//		requestBody.put("Lastname", rowData.get("Lastname"));
	//		requestBody.put("Email", rowData.get("Email"));
	//		requestBody.put("ContactNumber", rowData.get("ContactNumber"));
	//		requestBody.put("DateOfBirth", rowData.get("DateOfBirth"));
	//		requestBody.put("Education", rowData.get("Education"));
	//		requestBody.put("HospitalCity", rowData.get("HospitalCity"));
	//		requestBody.put("HospitalName", rowData.get("HospitalName"));
	//		requestBody.put("HospitalPincode", rowData.get("HospitalPincode"));
	//		requestBody.put("HospitalStreet", rowData.get("HospitalStreet"));
	//
	//		Response response = given().header("Authorization", "Bearer " + token).contentType("application/json")
	//				.body(requestBody.toString()).post(ApiEndpoints.CreateDietician.getResources());
	//
	//		StoreIDs.storeCreatedDietician(response);
	//		StoreIDs.storeDieticianPassword(response);
	//		StoreIDs.storeDieticianEmailAsUserName(response);
	//		return response;
	//	}

/////////////////////////////Positive - Create Dietician with valid data reading from JSON data file//////////////////////////	
	public static Response createDieticianThroughJSONData1(String scenarioName) {
		String filePath = ConfigReader.getProperty("JSON_Path");
		String admintoken = tokenManager.getAdminToken();

		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);

		List<DieticianData> filteredDieticians = new ArrayList<>();
		for (DieticianData d : allDieticians) {
			if (scenarioName.equalsIgnoreCase(d.getScenario())) {
				filteredDieticians.add(d);
			}
		}

		if (filteredDieticians.isEmpty()) {
			throw new RuntimeException("No data found for scenario: " + scenarioName);
		}

		Response lastResponse = null;
		for (DieticianData dietician : filteredDieticians) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				System.out.println("Sending request for dietician: " + mapper.writeValueAsString(dietician));
			} catch (Exception e) {
				e.printStackTrace();
			}

			lastResponse = RestAssured.given().header("Authorization", "Bearer " +admintoken)
					.contentType(dietician.getContentType()).body(dietician)
					.post(ApiEndpoints.CreateDietician.getResources());

			System.out.println("Status Code: " + lastResponse.getStatusCode());
			System.out.println("Response Body:\n" + lastResponse.asPrettyString());
			System.out.println(" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

			int expectedCode = dietician.getExpectedStatusCode();
			if (lastResponse.getStatusCode() != expectedCode) {
				throw new RuntimeException("Failed to create dietician: " + dietician.getFirstname());
			}
		}

		return lastResponse;
	}

//////////////////Create Dietician with auto generated unique fields - Not reading from json data file//////////////
	public static Response createDieticianWithRandomlyGeneratedData() {
		String randomFirstName = generateRandomName(6);
		String randomLastName = generateRandomName(7);
		String randomEmail = randomFirstName.toLowerCase() + "." + randomLastName.toLowerCase() + "@test.com";
		String randomContactNumber = "9" + getRandomNumber(9);
		String randomDOB = generateRandomDOB("1975-01-01", "1995-12-31");

		JSONObject requestBody = new JSONObject();
		requestBody.put("ContactNumber", randomContactNumber);
		requestBody.put("DateOfBirth", randomDOB);
		requestBody.put("Education", "MBBS");
		requestBody.put("Email", randomEmail);
		requestBody.put("Firstname", randomFirstName);
		requestBody.put("Lastname", randomLastName);
		requestBody.put("HospitalCity", "York");
		requestBody.put("HospitalName", "City Hospital");
		requestBody.put("HospitalPincode", "123456");
		requestBody.put("HospitalStreet", "5th Ave");

		String token = tokenManager.getAdminToken();
		Response response = given().header("Authorization", "Bearer " + token).contentType("application/json")
				.body(requestBody.toString()).post("/dietician");

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
	// Generate random number
	private static String getRandomNumber(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	// Generate random DOB
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

	///////////////////////////////Negative - Create Dietician with No Auth///////////////////////////////////////////////////////////////////////
	public static Response createDietician_invalidAuth(String scenarioName) {
		String filePath = ConfigReader.getProperty("JSON_Path");

		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);

		List<DieticianData> filteredDieticians = new ArrayList<>();
		for (DieticianData d : allDieticians) {
			if (scenarioName.equalsIgnoreCase(d.getScenario())) {
				filteredDieticians.add(d);
			}
		}

		if (filteredDieticians.isEmpty()) {
			throw new RuntimeException("No data found for scenario: " + scenarioName);
		}

		Response lastResponse = null;
		for (DieticianData dietician : filteredDieticians) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				System.out.println("Sending request for dietician: " + mapper.writeValueAsString(dietician));
			} catch (Exception e) {
				e.printStackTrace();
			}

			lastResponse = RestAssured.given().header("Authorization", dietician.getauthType())
					.contentType(dietician.getContentType()).body(dietician)
					.post(ApiEndpoints.CreateDietician.getResources());

			System.out.println("Status Code: " + lastResponse.getStatusCode());
			System.out.println("Response Body:\n" + lastResponse.asPrettyString());
			System.out.println(" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

			int expectedCode = dietician.getExpectedStatusCode();
			if (lastResponse.getStatusCode() != expectedCode) {
				throw new RuntimeException("Failed to create dietician: " + dietician.getFirstname());
			}
		}

		return lastResponse;
	}
///////////////////////////////Negative - Create Dietician with Dietician Token///////////////////////////////////////////////////////////////////////
	public static Response createDietician_DieticianToken(String scenarioName) {
		String filePath = ConfigReader.getProperty("JSON_Path");
	
		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);

		List<DieticianData> filteredDieticians = new ArrayList<>();
		for (DieticianData d : allDieticians) {
			if (scenarioName.equalsIgnoreCase(d.getScenario())) {
				filteredDieticians.add(d);
			}
		}

		if (filteredDieticians.isEmpty()) {
			throw new RuntimeException("No data found for scenario: " + scenarioName);
		}

		Response lastResponse = null;
		for (DieticianData dietician : filteredDieticians) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				System.out.println("Sending request for dietician: " + mapper.writeValueAsString(dietician));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String dieticianToken = "weruyidfhbdjskbfsjdf"; // chnage hard coded value and pass stored dietician token
			lastResponse = RestAssured
					.given()
					.header("Authorization", dietician.getauthType()+ dieticianToken)
					.contentType(dietician.getContentType()).body(dietician)
					.post(ApiEndpoints.CreateDietician.getResources());
			System.out.println("!!!!!!!!!!!!1"+dietician.getauthType()+ dieticianToken);
			System.out.println("Status Code: " + lastResponse.getStatusCode());
			System.out.println("Response Body:\n" + lastResponse.asPrettyString());
			System.out.println(" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

			int expectedCode = dietician.getExpectedStatusCode();
			if (lastResponse.getStatusCode() != expectedCode) {
				throw new RuntimeException("Status code is not matching...Instead of " +expectedCode+ " it's showing as " +lastResponse.getStatusCode());
			}
		}

		return lastResponse;
	}
///////////////////////////////Negative - Create Dietician with Patient Token///////////////////////////////////////////////////////////////////////	

	public static Response createDietician_PatientToken(String scenarioName) {
		String filePath = ConfigReader.getProperty("JSON_Path");
	
		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);

		List<DieticianData> filteredDieticians = new ArrayList<>();
		for (DieticianData d : allDieticians) {
			if (scenarioName.equalsIgnoreCase(d.getScenario())) {
				filteredDieticians.add(d);
			}
		}

		if (filteredDieticians.isEmpty()) {
			throw new RuntimeException("No data found for scenario: " + scenarioName);
		}

		Response lastResponse = null;
		for (DieticianData dietician : filteredDieticians) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				System.out.println("Sending request for dietician: " + mapper.writeValueAsString(dietician));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String PatientToken = "weruyidfhbdjskbfsjdf"; // chnage hard coded value and pass stored patient token
			lastResponse = RestAssured
					.given()
					.header("Authorization", dietician.getauthType()+ PatientToken)
					.contentType(dietician.getContentType()).body(dietician)
					.post(ApiEndpoints.CreateDietician.getResources());
			System.out.println("!!!!!!!!!!!!1"+dietician.getauthType()+ PatientToken);
			System.out.println("Status Code: " + lastResponse.getStatusCode());
			System.out.println("Response Body:\n" + lastResponse.asPrettyString());
			System.out.println(" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

			int expectedCode = dietician.getExpectedStatusCode();
			if (lastResponse.getStatusCode() != expectedCode) {
				throw new RuntimeException("Status code is not matching...Instead of " +expectedCode+ " it's showing as " +lastResponse.getStatusCode());
			}
		}

		return lastResponse;
	}




}
