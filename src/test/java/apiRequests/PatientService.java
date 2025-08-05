package apiRequests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import apiEndPoints.ApiEndpoints;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.util.BeanUtil;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import login.TokenManager;
import pojo.PatientInfo;
import pojo.PatientTestData;
import pojo.PatientVitalsInfo;
import pojo.tokenManager;
import utils.ConfigReader;
import utils.jsonReader;

public class PatientService {

	private static List<PatientTestData> allScenariosTestData;
	private static RequestSpecification requestSpecification;
	private static Response response;

	static {
		allScenariosTestData = jsonReader.readJsonList(ConfigReader.getProperty("testDataFilePath"),
				PatientTestData.class);
		// Initialize RequestSpecBuilder
		requestSpecification = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("baseUrl")).build();
	}

	public static Response createPatient(String scenarioName) {

		PatientTestData patientTestData = getTestDataForScenario(scenarioName);

		RequestSpecification updatePatientBaseReq = requestSpecification
				.spec(addAuthentication(scenarioName, patientTestData.getAuthType()))
				.contentType(patientTestData.getContentType());

		response = given().log().all().spec(updatePatientBaseReq).spec(addPatientInfo(scenarioName))
				.spec(addPatientVitalsInfo(scenarioName)).spec(addPatientReportPDF(scenarioName)).when()
				.post(ApiEndpoints.PATIENT_API_PATH.getResources() + patientTestData.getEndPointURL())
				.then().log().all().extract().response();

		return response;
	}

//	////////////////

	public static Response updatePatient(String scenarioName, int patientID) {

		PatientTestData patientTestData = getTestDataForScenario(scenarioName);

		RequestSpecification updatePatientBaseReq = requestSpecification
				.spec(addAuthentication(scenarioName, patientTestData.getAuthType()))
				.contentType(patientTestData.getContentType());

		response = given().log().all().spec(updatePatientBaseReq).spec(addPatientInfo(scenarioName))
				.spec(addPatientVitalsInfo(scenarioName)).spec(addPatientReportPDF(scenarioName)).when()
				.put(replacePatientId(ApiEndpoints.PATIENT_API_PATH.getResources() + patientTestData.getEndPointURL(),
						patientID + ""))
				.then().log().all().extract().response();

		return response;
	}
/////////////

	
	public static Response updatePatientInvalidMethodWithPOST(String scenarioName, int patientID) {

		PatientTestData patientTestData = getTestDataForScenario(scenarioName);

		RequestSpecification updatePatientBaseReq = requestSpecification
				.spec(addAuthentication(scenarioName, patientTestData.getAuthType()))
				.contentType(patientTestData.getContentType());

		response = given().log().all().spec(updatePatientBaseReq).spec(addPatientInfo(scenarioName))
				.spec(addPatientVitalsInfo(scenarioName)).spec(addPatientReportPDF(scenarioName)).when()
				.post(replacePatientId(ApiEndpoints.PATIENT_API_PATH.getResources() + patientTestData.getEndPointURL(),
						patientID + ""))
				.then().log().all().extract().response();

		return response;
	}
//	////////////

	private static PatientTestData getTestDataForScenario(String scenarioName) {
		List<PatientTestData> filteredPatients = new ArrayList<>();
		for (PatientTestData d : PatientService.allScenariosTestData) {
			if (scenarioName.equalsIgnoreCase(d.getScenarioName())) {
				filteredPatients.add(d);
			}
		}

		if (filteredPatients.isEmpty()) {
			throw new RuntimeException("No test data found for scenario: " + scenarioName);
		} else if (filteredPatients.size() > 1) {
			throw new RuntimeException("Multiple test data found for scenario: " + scenarioName);
		}
		return filteredPatients.get(0);
	}

	private static RequestSpecification addPatientInfo(String scenarioName) {
		String patientTestDataAsString = null;
		PatientTestData patientTestData = getTestDataForScenario(scenarioName);
		if (!patientTestData.getIncludePatientInfo()) {
			return RestAssured.given();
		}
		try {
			PatientInfo patientInfo = new PatientInfo();
			org.apache.groovy.util.BeanUtils.copyProperties(patientInfo, patientTestData);

			ObjectMapper mapper = new ObjectMapper();
			patientTestDataAsString = mapper.writeValueAsString(patientInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RestAssured.given().multiPart("patientInfo", patientTestDataAsString, "text/plain");
	}

	private static RequestSpecification addPatientVitalsInfo(String scenarioName) {
		String vitalsTestDataAsString = null;
		PatientTestData patientTestData = getTestDataForScenario(scenarioName);

		if (!patientTestData.getIncludePatientVitalsInfo()) {
			return RestAssured.given();
		}
		try {
			PatientVitalsInfo vitalsInfo = new PatientVitalsInfo();
			BeanUtil.copyProperties(vitalsInfo, patientTestData);

			ObjectMapper mapper = new ObjectMapper();
			vitalsTestDataAsString = mapper.writeValueAsString(vitalsInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RestAssured.given().multiPart("vitals", vitalsTestDataAsString, "text/plain");
	}

	private static RequestSpecification addPatientReportPDF(String scenarioName) {
		PatientTestData patientTestData = getTestDataForScenario(scenarioName);

		if (!patientTestData.getIncludePatientReportPDF()) {
			return RestAssured.given();
		}
		File patientReportPDFFile = new File(patientTestData.getPatientReportPDFPath());
		return RestAssured.given().multiPart("file", patientReportPDFFile, "application/pdf");
	}

	private static RequestSpecification validAuthWithDiticianToken(String scenarioName) {
		String dieticianToken = TokenManager.getTokenForUserLoginEmailOrScenario("DIETICIAN_TOKEN");
		System.out.println("Dietician Token: " + dieticianToken);

		return RestAssured.given().spec(requestSpecification).header("Authorization", "Bearer " + dieticianToken);
	}

	// TODO
	private static RequestSpecification validAuthWithAdminToken(String scenarioName) {
		String adminToken = TokenManager.getTokenForUserLoginEmailOrScenario("DIETICIAN_TOKEN");
		System.out.println("Admin Token: " + adminToken);

		return RestAssured.given().spec(requestSpecification).header("Authorization", "Bearer " + adminToken);
	}

	// TODO
	private static RequestSpecification validAuthWithPatientToken(String scenarioName) {
		String patientToken = TokenManager.getTokenForUserLoginEmailOrScenario("DIETICIAN_TOKEN");
		System.out.println("Patient Token: " + patientToken);

		return RestAssured.given().spec(requestSpecification).header("Authorization", "Bearer " + patientToken);
	}

	private static RequestSpecification noAuth() {
		return RestAssured.given().spec(requestSpecification).auth().none();

	}

	private static RequestSpecification invalidAuth() {
		return RestAssured.given().spec(requestSpecification).auth().basic("ABC", "XYZ");
	}

	public RequestSpecification validAuth() {
		return RestAssured.given().spec(requestSpecification).auth().basic(ConfigReader.getProperty("username"),
				ConfigReader.getProperty("password"));
	}

	public static void validateResponseForScenario(String scenarioName) {

		PatientTestData patientTestData = getTestDataForScenario(scenarioName);

		// Validate input response
		if (response == null) {
			System.err.println("Error: Response is null. Cannot validate response for scenario - " + scenarioName);
			return;
		}

		Assert.assertEquals(response.getStatusCode(), patientTestData.getExpectedStatusCode());
		Assert.assertTrue(response.getStatusLine().contains(patientTestData.getExpectedStatusLine()),
				"Expected status line to contain: " + patientTestData.getExpectedStatusLine());
		Assert.assertEquals(response.getContentType(), patientTestData.getExpectedContentType());

		System.out.println(response.getBody().asString());
	}

	private static RequestSpecification addAuthentication(String scenarioName, String authType) {

		// Setup Auth
		RequestSpecification authRequest;
		switch (authType.toUpperCase()) {
		case "VALID_AUTH_DITICIAN_TOKEN":
			authRequest = validAuthWithDiticianToken(scenarioName);
			break;
		case "VALID_AUTH_ADMIN_TOKEN":
			authRequest = validAuthWithAdminToken(scenarioName);
			break;
		case "VALID_AUTH_PATIENT_TOKEN":
			authRequest = validAuthWithPatientToken(scenarioName);
			break;
		case "INVALID_AUTH":
			authRequest = invalidAuth();
			break;
		case "NO_AUTH":
			authRequest = noAuth();
			break;
		default:
			throw new IllegalArgumentException("Invalid Auth Type: " + authType);
		}
		return authRequest;
	}

	public static String replacePatientId(String urlTemplate, String patientId) {
		return urlTemplate.replace("{patientId}", patientId);
	}
       	public static Response createPatientWithInvalidMethod(String scenarioName) {
	    String patientReportPDFPath = ConfigReader.getProperty("patientReportPDFPath");
	    PatientTestData patientTestData = getTestDataForScenario(scenarioName);
	    File patientReportPDFFile = new File(patientReportPDFPath);

	    RequestSpecification createPatientBaseReq = requestSpecification
	            .spec(addAuthentication(scenarioName, patientTestData.getAuthType()))
	            .contentType(ContentType.MULTIPART);

	    RequestSpecification regAddPatient = given().log().all().spec(createPatientBaseReq)
	            .spec(addPatientInfo(scenarioName)).spec(addPatientVitalsInfo(scenarioName))
	            .multiPart("file", patientReportPDFFile, "application/pdf");

	    response = regAddPatient.when().put(ApiEndpoints.PATIENT_API_PATH.getResources()).then().log().all().extract()
	            .response();

	    return response;
	}
	
	public static Response createPatientWithInvalidEndpoint(String scenarioName) {
	    String filePath = ConfigReader.getProperty("JSON_Path");

	    List<PatientTestData> allPatients = jsonReader.readJsonList(filePath, PatientTestData.class);
	    List<PatientTestData> filteredPatients = allPatients.stream()
	            .filter(d -> scenarioName.equalsIgnoreCase(d.getScenarioName()))
	            .collect(Collectors.toList());

	    if (filteredPatients.isEmpty()) {
	        throw new RuntimeException("No data found for scenario: " + scenarioName);
	    }

	    PatientTestData patientData = filteredPatients.get(0);

	    RequestSpecification request = RestAssured.given()
	            .baseUri(ConfigReader.getProperty("BaseURL"))
	            .basePath("/invalid/api/patient") 
	            .contentType(ContentType.JSON)
	            .header("Authorization", "Bearer " + tokenManager.getAdminToken())
	            .body(patientData);

	    //LoggerLoad.info("Sending POST request to invalid endpoint with valid data...");
	    Response response = request.post();

	    //LoggerLoad.info("Response Status Code: " + response.getStatusCode());
	    //LoggerLoad.info("Response Body: " + response.getBody().asPrettyString());

	    return response;
	}

   
	public static Response createPatientWithInvalidContentType(String scenarioName) {
	    String patientReportPDFPath = ConfigReader.getProperty("patientReportPDFPath");
	    PatientTestData patientTestData = getTestDataForScenario(scenarioName);
	    File patientReportPDFFile = new File(patientReportPDFPath);

	    RequestSpecification createPatientBaseReq = requestSpecification
	        .spec(addAuthentication(scenarioName, patientTestData.getAuthType()))
	        .contentType("application/json"); // Invalid for multipart/form-data

	    RequestSpecification regAddPatient = given().log().all().spec(createPatientBaseReq)
	        .spec(addPatientInfo(scenarioName))
	        .spec(addPatientVitalsInfo(scenarioName))
	        .multiPart("file", patientReportPDFFile, "application/pdf");

	    return regAddPatient.when()
	        .post(ApiEndpoints.PATIENT_API_PATH.getResources())
	        .then().log().all().extract().response();
	}


}
