package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import utils.RandomGenerator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientTestData {

	@JsonProperty("scenarioName")
	private String scenarioName;

	@JsonProperty("type")
	private String type;

	@JsonProperty("endPointURL")
	private String endPointURL;

	@JsonProperty("requestMethod")
	private String requestMethod;

	@JsonProperty("authType")
	private String authType;

	@JsonProperty("contentType")
	private String contentType;

	@JsonProperty("expectedStatusCode")
	private int expectedStatusCode;

	@JsonProperty("expectedStatusLine")
	private String expectedStatusLine;

	@JsonProperty("expectedContentType")
	private String expectedContentType;
	
	
	//Request Data flags
	@JsonProperty("includePatientInfo")
	private Boolean includePatientInfo;
	
	@JsonProperty("includePatientVitalsInfo")
	private Boolean includePatientVitalsInfo;
	
	@JsonProperty("includePatientReportPDF")
	private Boolean includePatientReportPDF;
	

	// Patient core properties
	@JsonProperty("FirstName")
	private String firstName;

	@JsonProperty("LastName")
	private String lastName;

	@JsonProperty("ContactNumber")
	private String contactNumber;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("Allergy")
	private String allergy;

	@JsonProperty("FoodPreference")
	private String foodPreference;

	@JsonProperty("CuisineCategory")
	private String cuisineCategory;

	@JsonProperty("DateOfBirth")
	private String dateOfBirth;

	//Patient Vitals
	@JsonProperty("Weight")
	private String weight;

	@JsonProperty("Height")
	private String height;

	@JsonProperty("Temperature")
	private String temperature;
	
	//Patient Reports
	@JsonProperty("patientReportPDFPath")
	private String patientReportPDFPath;

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenario) {
		this.scenarioName = scenario;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEndPointURL() {
		return endPointURL;
	}

	public void setEndPointURL(String endPointURL) {
		this.endPointURL = endPointURL;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getExpectedStatusCode() {
		return expectedStatusCode;
	}

	public void setExpectedStatusCode(int expectedStatusCode) {
		this.expectedStatusCode = expectedStatusCode;
	}

	public String getExpectedStatusLine() {
		return expectedStatusLine;
	}

	public void setExpectedStatusLine(String expectedStatusLine) {
		this.expectedStatusLine = expectedStatusLine;
	}

	public String getExpectedContentType() {
		return expectedContentType;
	}

	public void setExpectedContentType(String expectedContentType) {
		this.expectedContentType = expectedContentType;
	}
	

	public Boolean getIncludePatientInfo() {
		return includePatientInfo;
	}

	public void setIncludePatientInfo(Boolean includePatientInfo) {
		this.includePatientInfo = includePatientInfo;
	}

	public Boolean getIncludePatientVitalsInfo() {
		return includePatientVitalsInfo;
	}

	public void setIncludePatientVitalsInfo(Boolean includePatientVitalsInfo) {
		this.includePatientVitalsInfo = includePatientVitalsInfo;
	}

	public Boolean getIncludePatientReportPDF() {
		return includePatientReportPDF;
	}

	public void setIncludePatientReportPDF(Boolean includePatientReportPDF) {
		this.includePatientReportPDF = includePatientReportPDF;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		if ("RANDOM_CONTACT_NUMBER".equalsIgnoreCase(contactNumber)) {
			this.contactNumber = RandomGenerator.generateRandomNumber(10);
		} else {
			this.contactNumber = contactNumber;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if ("RANDOM_EMAIL".equalsIgnoreCase(email)) {
			this.email = RandomGenerator.generateRandomEmail();
		} else {
			this.email = email;
		}
	}

	public String getAllergy() {
		return allergy;
	}

	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}

	public String getFoodPreference() {
		return foodPreference;
	}

	public void setFoodPreference(String foodPreference) {
		this.foodPreference = foodPreference;
	}

	public String getCuisineCategory() {
		return cuisineCategory;
	}

	public void setCuisineCategory(String cuisineCategory) {
		this.cuisineCategory = cuisineCategory;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		if ("RANDOM_DOB".equalsIgnoreCase(dateOfBirth)) {
			this.dateOfBirth = RandomGenerator.generateRandomDOBInYYYYMMDD();
		} else {
			this.dateOfBirth = dateOfBirth;
		}
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getPatientReportPDFPath() {
		return patientReportPDFPath;
	}

	public void setPatientReportPDFPath(String patientReportPDFPath) {
		this.patientReportPDFPath = patientReportPDFPath;
	}
	
}
