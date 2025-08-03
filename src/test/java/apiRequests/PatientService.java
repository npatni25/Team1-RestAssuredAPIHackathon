package apiRequests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import apiEndPoints.ApiEndpoints;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import login.TokenManager;
import pojo.PatientInfo;
import pojo.PatientTestData;
import pojo.PatientVitalsInfo;
import utils.ConfigReader;
import utils.jsonReader;

public class PatientService {

	private static List<PatientTestData> allScenariosTestData;
	static {
		allScenariosTestData = jsonReader.readJsonList(ConfigReader.getProperty("testDataFilePath"),
				PatientTestData.class);
		// Initialize RequestSpecBuilder
		requestSpecification = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("baseUrl")).build();
	}

	private static RequestSpecification requestSpecification;
	private static Response response;

	public static Response createPatient(String scenarioName) {

		String patientReportPDFPath = ConfigReader.getProperty("patientReportPDFPath");
		PatientTestData patientTestData = null;
		try {
			patientTestData = getTestDataForScenario(scenarioName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		File patientReportPDFFile = new File(patientReportPDFPath);

		RequestSpecification createPatientBaseReq = requestSpecification
				.spec(addAuthentication(scenarioName, patientTestData.getAuthType()))
				.contentType(ContentType.MULTIPART);

		RequestSpecification regAddPatient = given().log().all().spec(createPatientBaseReq)
				.spec(addPatientInfo(scenarioName)).spec(addPatientVitalsInfo(scenarioName))
				.multiPart("file", patientReportPDFFile, "application/pdf");

		response = regAddPatient.when().post(ApiEndpoints.CreatePatient.getResources()).then().log().all().extract()
				.response();

		return response;

	}

	private static PatientTestData getTestDataForScenario(String scenarioName) {
		List<PatientTestData> filteredPatients = new ArrayList<>();
		for (PatientTestData d : PatientService.allScenariosTestData) {
			if (scenarioName.equalsIgnoreCase(d.getScenario())) {
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
		PatientTestData patientTestData = null;
		try {
			patientTestData = getTestDataForScenario(scenarioName);
			PatientInfo patientInfo = new PatientInfo();
			BeanUtils.copyProperties(patientInfo, patientTestData);

			ObjectMapper mapper = new ObjectMapper();
			patientTestDataAsString = mapper.writeValueAsString(patientInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RestAssured.given().spec(requestSpecification).multiPart("patientInfo", patientTestDataAsString,
				"text/plain");
	}

	private static RequestSpecification addPatientVitalsInfo(String scenarioName) {
		String vitalsTestDataAsString = null;
		PatientTestData patientTestData = null;
		try {
			patientTestData = getTestDataForScenario(scenarioName);
			PatientVitalsInfo vitalsInfo = new PatientVitalsInfo();
			BeanUtils.copyProperties(vitalsInfo, patientTestData);

			ObjectMapper mapper = new ObjectMapper();
			vitalsTestDataAsString = mapper.writeValueAsString(vitalsInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RestAssured.given().spec(requestSpecification).multiPart("vitals", vitalsTestDataAsString, "text/plain");
	}

	private static RequestSpecification validAuthWithDiticianToken(String scenarioName) {
		String dieticiantoken = TokenManager.getTokenForUserLoginEmailOrScenario(scenarioName);
		System.out.println("Dietician Token: " + dieticiantoken);

		return RestAssured.given().spec(requestSpecification).header("Authorization", "Bearer " + dieticiantoken);
	}

	// TODO
	private static RequestSpecification validAuthWithAdminToken(String scenarioName) {
		String dieticiantoken = TokenManager.getTokenForUserLoginEmailOrScenario(scenarioName);
		System.out.println("Dietician Token: " + dieticiantoken);

		return RestAssured.given().spec(requestSpecification).header("Authorization", "Bearer " + dieticiantoken);
	}

	// TODO
	private static RequestSpecification validAuthWithPatientToken(String scenarioName) {
		String dieticiantoken = TokenManager.getTokenForUserLoginEmailOrScenario(scenarioName);
		System.out.println("Dietician Token: " + dieticiantoken);

		return RestAssured.given().spec(requestSpecification).header("Authorization", "Bearer " + dieticiantoken);
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

		// Validate input response
		if (response == null) {
			System.err.println("Error: Response is null. Cannot validate response for scenario - " + scenarioName);
			return;
		}

		Assert.assertEquals(response.getStatusCode(), 201);

		System.out.println(response.getBody().asString());
		System.out.println("Response Code is: " + response.getStatusCode());

	}

	private static RequestSpecification addAuthentication(String scenarioName, String authType) {

		// Setup auth
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
			throw new IllegalArgumentException("Invalid auth type: " + authType);
		}
		return authRequest;
	}

}
