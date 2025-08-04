package apiRequests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import utils.ConfigReader;
import utils.jsonReader;

import java.util.Optional;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import apiEndPoints.ApiEndpoints;
import baseAPI.StoreIDs;
import pojo.DieticianData;
import pojo.tokenManager;

public class DieticianLogic {

	static Response response;

	private static String replaceDynamicData(String input, String dieticianId, String invalidId, String dieticianToken,
			String adminToken, String patientToken) {
		if (input == null)
			return null;
		return input.replace("{dieticianId}", dieticianId).replace("{invalidId}", invalidId)
				.replace("{dieticianToken}", dieticianToken != null ? dieticianToken : "")
				.replace("{patientToken}", patientToken != null ? patientToken : "")
				.replace("{adminToken}", adminToken != null ? adminToken : "");
	}

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

			lastResponse = RestAssured.given().header("Authorization", "Bearer " + admintoken)
					.contentType(dietician.getContentType()).body(dietician)
					.post(ApiEndpoints.CreateDietician.getResources());

			System.out.println("Status Code: " + lastResponse.getStatusCode());
			System.out.println("Response Body:\n" + lastResponse.asPrettyString());
			System.out.println(
					" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

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

	/////////////////////////////// Negative - Create Dietician with No
	/////////////////////////////// Auth///////////////////////////////////////////////////////////////////////
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
			System.out.println(
					" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

			int expectedCode = dietician.getExpectedStatusCode();
			if (lastResponse.getStatusCode() != expectedCode) {
				throw new RuntimeException("Failed to create dietician: " + dietician.getFirstname());
			}
		}

		return lastResponse;
	}

///////////////////////////////Negative - Create Dietician with Dietician Token///////////////////////////////////////////////////////////////////////
	public static Response createDietician_DieticianToken(String scenarioName) {
		//String dieticianToken = tokenManager.getAdminToken();
		String dieticianToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjd2VoZWIudHRlcHJmckB0ZXN0LmNvbSIsImlhdCI6MTc1NDMxNTYxOCwiZXhwIjoxNzU0MzQ0NDE4fQ.RpJ6nVtRqreoi9ppbT7tYvnqen1eGgq1aKFBhffg9QcbGL_9ZzdWpyrbGCZDFxaM0mEqqvOrGopdbbx44_jOBA";
		String filePath = ConfigReader.getProperty("JSON_Path");

		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);
		Optional<DieticianData> matchedDietician = allDieticians.stream()
				.filter(d -> scenarioName.equalsIgnoreCase(d.getScenario())).findFirst();

		if (!matchedDietician.isPresent()) {
			throw new RuntimeException("Scenario not found in JSON: " + scenarioName);
		}

		DieticianData dietician = matchedDietician.get();

		String method = dietician.getMethod();
		String endPoint = dietician.getEndPoint();
		String auth = dietician.getauthType();
		String baseUrl = ConfigReader.getProperty("baseUrl");

		System.out.println("Method: " + method);
		System.out.println("Full Path: " + endPoint);
		System.out.println("Auth type: " + auth);

		RequestSpecification request = given()
										.baseUri(baseUrl)
										.basePath(endPoint)
										.header("Authorization", auth + dieticianToken)
										.contentType(dietician.getContentType());

		Object bodyPayload = dietician;

		Response response;
		switch (method.toUpperCase()) {
		case "GET":
			response = request.get();
			break;
		case "POST":
			response = request.body(bodyPayload).post();
			break;
		case "PUT":
			response = request.body(bodyPayload).put();
			break;
		case "DELETE":
			response = request.delete();
			break;
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + method);
		}

		System.out.println("Response:\n" + response.asPrettyString());
		System.out.println("Response code:\n" + response.getStatusCode());
		return response;
	}
///////////////////////////////Negative - Create Dietician with Patient Token///////////////////////////////////////////////////////////////////////	

	public static Response createDietician_PatientToken(String scenarioName) {

		String patientToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjd2VoZWIudHRlcHJmckB0ZXN0LmNvbSIsImlhdCI6MTc1NDMxNTYxOCwiZXhwIjoxNzU0MzQ0NDE4fQ.RpJ6nVtRqreoi9ppbT7tYvnqen1eGgq1aKFBhffg9QcbGL_9ZzdWpyrbGCZDFxaM0mEqqvOrGopdbbx44_jOBA";
		String filePath = ConfigReader.getProperty("JSON_Path");

		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);
		Optional<DieticianData> matchedDietician = allDieticians.stream()
				.filter(d -> scenarioName.equalsIgnoreCase(d.getScenario())).findFirst();

		if (!matchedDietician.isPresent()) {
			throw new RuntimeException("Scenario not found in JSON: " + scenarioName);
		}

		DieticianData dietician = matchedDietician.get();

		String method = dietician.getMethod();
		String endPoint = dietician.getEndPoint();
		String auth = dietician.getauthType();
		String baseUrl = ConfigReader.getProperty("baseUrl");

		System.out.println("Method: " + method);
		System.out.println("Full Path: " + endPoint);
		System.out.println("Auth type: " + auth);

		RequestSpecification request = given()
										.baseUri(baseUrl)
										.basePath(endPoint)
										.header("Authorization", auth + patientToken)
										.contentType(dietician.getContentType());

		// System.out.println("!!!!!!!!!1" +request);

		Object bodyPayload = dietician;

		Response response;
		switch (method.toUpperCase()) {
		case "GET":
			response = request.get();
			break;
		case "POST":
			response = request.body(bodyPayload).post();
			break;
		case "PUT":
			response = request.body(bodyPayload).put();
			break;
		case "DELETE":
			response = request.delete();
			break;
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + method);
		}

		System.out.println("Response:\n" + response.asPrettyString());
		System.out.println("Response code:\n" + response.getStatusCode());
		return response;
	}

///////////////createDietician with InvalidAPIReq_PUT/////////////////////////////////////////////
	public static Response createDietician_InvalidAPIReq_PUT(String scenarioName) {
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
			lastResponse = RestAssured.given().header("Authorization", "Bearer " + admintoken)
					.contentType(dietician.getContentType()).body(dietician)
					.put(ApiEndpoints.CreateDietician.getResources());

			System.out.println("Status Code: " + lastResponse.getStatusCode());
			System.out.println("Response Body:\n" + lastResponse.asPrettyString());
			System.out.println(
					" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

			int expectedCode = dietician.getExpectedStatusCode();
			if (lastResponse.getStatusCode() != expectedCode) {
				throw new RuntimeException("Status code is not matching...Instead of " + expectedCode
						+ " it's showing as " + lastResponse.getStatusCode());
			}
		}
		return lastResponse;
	}

///////////////createDietician with valid data but Invalid EndPoint/////////////////////////////////////////////
	public static Response createDietician_InvalidEndPoint(String scenarioName) {
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
			lastResponse = RestAssured.given().header("Authorization", "Bearer " + admintoken)
					.contentType(dietician.getContentType()).body(dietician)
					.post(ApiEndpoints.InvalidDieticianEndPoint.getResources());

			System.out.println("Status Code: " + lastResponse.getStatusCode());
			System.out.println("Response Body:\n" + lastResponse.asPrettyString());
			System.out.println(
					" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

			int expectedCode = dietician.getExpectedStatusCode();
			if (lastResponse.getStatusCode() != expectedCode) {
				throw new RuntimeException("Status code is not matching...Instead of " + expectedCode
						+ " it's showing as " + lastResponse.getStatusCode());
			}
		}
		return lastResponse;
	}
///////////////---------------End of createDietician  - Start of Get Dietician---------------------- /////////////////////////////////////////////

	public static Response RetrieveDietician_NoAuth(String scenarioName) {
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
			RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + admintoken)
					.contentType(dietician.getContentType());

			lastResponse = request.request(Method.valueOf(dietician.getMethod().toUpperCase()),
					dietician.getEndPoint());

			System.out.println("Status Code: " + lastResponse.getStatusCode());
			System.out.println("Response Body:\n" + lastResponse.asPrettyString());
			System.out.println(
					" ****Found " + filteredDieticians.size() + " set(s) of data for scenario: " + scenarioName);

			int expectedCode = dietician.getExpectedStatusCode();
			if (lastResponse.getStatusCode() != expectedCode) {
				throw new RuntimeException("Status code is not matching...Instead of " + expectedCode
						+ " it's showing as " + lastResponse.getStatusCode());
			}
		}
		return lastResponse;
	}

	//////////////////// Getting all dietician all hard coded
	public static Response getAllDietician() {
		String admintoken = tokenManager.getAdminToken();
		Response response = given().header("Authorization", "Bearer " + admintoken).contentType("application/json")
				.baseUri("https://dietician-july-api-hackathon-80f2590665cc.herokuapp.com/dietician").get("/dietician");
		return response;
	}

	//////////////////// -----Retrieving All
	//////////////////// dietician--------///////////////////////////////////////////
	public static Response getAllDieticianJSON(String scenarioName) {
		String filePath = ConfigReader.getProperty("JSON_Path");
		String admintoken = tokenManager.getAdminToken();

		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);

		Optional<DieticianData> matchedDietician = allDieticians.stream()
				.filter(d -> scenarioName.equalsIgnoreCase(d.getScenario())).findFirst();

		DieticianData dietician = matchedDietician.get();
		String method = dietician.getMethod();
		String endpoint = dietician.getEndPoint();
		String auth = dietician.getauthType();

		System.out.println("Method: " + dietician.getMethod());
		System.out.println("Endpoint: " + dietician.getEndPoint());

		RequestSpecification request = given().baseUri(ConfigReader.getProperty("baseUrl"))
				.contentType(dietician.getContentType()).header("Authorization", auth + admintoken);

		Response response = null;
		switch (method.toUpperCase()) {
		case "GET":
			response = request.get(endpoint);
			break;
		case "POST":
			response = request.body(dietician).post(endpoint);
			break;
		case "PUT":
			response = request.body(dietician).put(endpoint);
			break;
		case "DELETE":
			response = request.delete(endpoint);
			break;
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + method);
		}

		return response;
	}

////////////////////-----Retrieving dietician By Valid ID--------///////////////////////////////////////////  

	public static Response getDieticianById(String scenarioName) {
		String dieticianId = StoreIDs.getLatestStoredDieticianID();
		String filePath = ConfigReader.getProperty("JSON_Path");
		String admintoken = tokenManager.getAdminToken();

		if (dieticianId == null || dieticianId.isEmpty()) {
			throw new RuntimeException("No stored dietician ID found");
		}

		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);
		Optional<DieticianData> matchedDietician = allDieticians.stream()
				.filter(d -> scenarioName.equalsIgnoreCase(d.getScenario())).findFirst();

		if (!matchedDietician.isPresent()) {
			throw new RuntimeException("Scenario not found in JSON: " + scenarioName);
		}

		DieticianData dietician = matchedDietician.get();
		String method = dietician.getMethod();
		String endpoint = dietician.getEndPoint();
		String auth = dietician.getauthType();
		String baseUrl = ConfigReader.getProperty("baseUrl");

		// Build full endpoint with ID
		String fullPath = endpoint.endsWith("/") ? endpoint + dieticianId : endpoint + "/" + dieticianId;

		System.out.println("Method: " + method);
		System.out.println("Full Path: " + fullPath);

		RequestSpecification request = given().baseUri(baseUrl).basePath(fullPath)
				.header("Authorization", auth + admintoken).contentType(dietician.getContentType());

		Response response;

		switch (method.toUpperCase()) {
		case "GET":
			response = request.get();
			break;
		case "POST":
			response = request.body(dietician).post();
			break;
		case "PUT":
			response = request.body(dietician).put();
			break;
		case "DELETE":
			response = request.delete();
			break;
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + method);
		}

		System.out.println("Dietician by ID response:\n" + response.asPrettyString());
		return response;
	}

////////////////////-----Retrieving dietician By inValid ID--------///////////////////////////////////////////  
	public static Response getDieticianByInvaliId(String scenarioName) {
		String filePath = ConfigReader.getProperty("JSON_Path");
		String baseUrl = ConfigReader.getProperty("baseUrl");
		String adminToken = tokenManager.getAdminToken();
		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);

		Optional<DieticianData> matched = allDieticians.stream()
				.filter(d -> scenarioName.equalsIgnoreCase(d.getScenario())).findFirst();

		if (!matched.isPresent()) {
			throw new RuntimeException("No data found in JSON for scenario: " + scenarioName);
		}
		DieticianData dietician = matched.get();
		String method = dietician.getMethod();
		String endpoint = dietician.getEndPoint();
		String invalidId = dietician.getInvalidID();
		String auth = dietician.getauthType();

		if (invalidId == null || invalidId.trim().isEmpty() || "null".equalsIgnoreCase(invalidId)) {
			throw new RuntimeException("Invalid ID is missing or not set correctly in JSON.");
		}

		String fullPath = endpoint.endsWith("/") ? endpoint + invalidId : endpoint + "/" + invalidId;

		System.out.println("Final URL: " + baseUrl + fullPath);
		System.out.println("Method: " + method);

		RequestSpecification request = given().baseUri(baseUrl).header("Authorization", auth + adminToken)
				.contentType(dietician.getContentType());

		Response response;

		switch (method.toUpperCase()) {
		case "GET":
			response = request.get(fullPath);
			break;
		case "POST":
			response = request.body(dietician).post(fullPath);
			break;
		case "PUT":
			response = request.body(dietician).put(fullPath);
			break;
		case "DELETE":
			response = request.delete(fullPath);
			break;
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + method);
		}

		System.out.println("Response:\n" + response.asPrettyString());
		return response;
	}

	//////////////// --------------InValidAuth - NoAuth/PATIENT TOKEN/DIETICIAN
	//////////////// TOKEN---------------///////////////////////////

	public static Response updateDietician(String scenarioName) {
		String dieticianId = StoreIDs.getLatestStoredDieticianID();
		String invalidId = "a123";
		String adminToken = tokenManager.getAdminToken();
		// String dieticianToken = tokenManager.getDieticianToken(); 
		String dieticianToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjd2VoZWIudHRlcHJmckB0ZXN0LmNvbSIsImlhdCI6MTc1NDMxNTYxOCwiZXhwIjoxNzU0MzQ0NDE4fQ.RpJ6nVtRqreoi9ppbT7tYvnqen1eGgq1aKFBhffg9QcbGL_9ZzdWpyrbGCZDFxaM0mEqqvOrGopdbbx44_jOBA";
		String patientToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjd2VoZWIudHRlcHJmckB0ZXN0LmNvbSIsImlhdCI6MTc1NDMxNTYxOCwiZXhwIjoxNzU0MzQ0NDE4fQ.RpJ6nVtRqreoi9ppbT7tYvnqen1eGgq1aKFBhffg9QcbGL_9ZzdWpyrbGCZDFxaM0mEqqvOrGopdbbx44_jOBA";
		String filePath = ConfigReader.getProperty("JSON_Path");

		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);
		Optional<DieticianData> matchedDietician = allDieticians.stream()
				.filter(d -> scenarioName.equalsIgnoreCase(d.getScenario())).findFirst();

		if (!matchedDietician.isPresent()) {
			throw new RuntimeException("Scenario not found in JSON: " + scenarioName);
		}

		DieticianData dietician = matchedDietician.get();

		String method = dietician.getMethod();
		String endpoint = replaceDynamicData(dietician.getEndPoint(), dieticianId, invalidId, dieticianToken,
				adminToken, patientToken); // it will take any one of this whichever is given in json data file
		String auth = replaceDynamicData(dietician.getauthType(), dieticianId, invalidId, dieticianToken, adminToken,
				patientToken); // it will take any one of this whichever is given in json data
		String baseUrl = ConfigReader.getProperty("baseUrl");

		System.out.println("Method: " + method);
		System.out.println("Full Path: " + endpoint);
		System.out.println("Auth type: " + auth);

		RequestSpecification request = given()
				.baseUri(baseUrl)
				.basePath(endpoint)
				.header("Authorization", auth)
				.contentType(dietician.getContentType());

		Object bodyPayload = dietician;

		Response response;
		switch (method.toUpperCase()) {
		case "GET":
			response = request.get();
			break;
		case "POST":
			response = request.body(bodyPayload).post();
			break;
		case "PUT":
			response = request.body(bodyPayload).put();
			break;
		case "DELETE":
			response = request.delete();
			break;
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + method);
		}

		System.out.println("Response:\n" + response.asPrettyString());
		System.out.println("Response code:\n" + response.getStatusCode());
		return response;
	}

	//////////////// ---------------DELETE DIETICIAN--------------///////////////////////////

	public static Response deleteDietician(String scenarioName) {
		String dieticianId = StoreIDs.getLatestStoredDieticianID();
		String invalidId = "9999";
		String adminToken = tokenManager.getAdminToken();
		// String dieticianToken = tokenManager.getDieticianToken(); 
		String dieticianToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjd2VoZWIudHRlcHJmckB0ZXN0LmNvbSIsImlhdCI6MTc1NDMxNTYxOCwiZXhwIjoxNzU0MzQ0NDE4fQ.RpJ6nVtRqreoi9ppbT7tYvnqen1eGgq1aKFBhffg9QcbGL_9ZzdWpyrbGCZDFxaM0mEqqvOrGopdbbx44_jOBA";
		String patientToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjd2VoZWIudHRlcHJmckB0ZXN0LmNvbSIsImlhdCI6MTc1NDMxNTYxOCwiZXhwIjoxNzU0MzQ0NDE4fQ.RpJ6nVtRqreoi9ppbT7tYvnqen1eGgq1aKFBhffg9QcbGL_9ZzdWpyrbGCZDFxaM0mEqqvOrGopdbbx44_jOBA";
		String filePath = ConfigReader.getProperty("JSON_Path");

		List<DieticianData> allDieticians = jsonReader.readJsonList(filePath, DieticianData.class);
		Optional<DieticianData> matchedDietician = allDieticians.stream()
				.filter(d -> scenarioName.equalsIgnoreCase(d.getScenario())).findFirst();

		if (!matchedDietician.isPresent()) {
			throw new RuntimeException("Scenario not found in JSON: " + scenarioName);
		}

		DieticianData dietician = matchedDietician.get();

		String method = dietician.getMethod();
		String endpoint = replaceDynamicData(dietician.getEndPoint(), dieticianId, invalidId, dieticianToken,
				adminToken, patientToken); // it will take any one of this whichever is given in json data file
		String auth = replaceDynamicData(dietician.getauthType(), dieticianId, invalidId, dieticianToken, adminToken,
				patientToken); // it will take any one of this whichever is given in json data
		String baseUrl = ConfigReader.getProperty("baseUrl");

		System.out.println("Method: " + method);
		System.out.println("Full Path: " + endpoint);
		System.out.println("Auth type: " + auth);

		RequestSpecification request = given()
				.baseUri(baseUrl)
				.basePath(endpoint)
				.header("Authorization", auth);

		Object bodyPayload = dietician;

		Response response;
		switch (method.toUpperCase()) {
		case "GET":
			response = request.get();
			break;
		case "POST":
			response = request.body(bodyPayload).post();
			break;
		case "PUT":
			response = request.body(bodyPayload).put();
			break;
		case "DELETE":
			response = request.delete();
			break;
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + method);
		}

		System.out.println("Response:\n" + response.asPrettyString());
		System.out.println("Response code:\n" + response.getStatusCode());
		return response;
	}
	
}