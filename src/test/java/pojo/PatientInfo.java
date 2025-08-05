package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientInfo {
	
	
	
	/*
	 * public PatientInfo() { super(); } public PatientInfo(PatientTestData
	 * testData) { super(); firstName = testData.getFirstName(); lastName =
	 * testData.getLastName();
	 * 
	 * }
	 */

	//Patient core properties
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
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		this.dateOfBirth = dateOfBirth;
	}

}
