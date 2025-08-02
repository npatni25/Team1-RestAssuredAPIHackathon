package pojo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.restassured.response.Response;

public class PatientPojo {
	
	   @JsonProperty("FirstName")
	    private String firstname;
	   @JsonProperty("LastName")
	    private String lastname;
	   @JsonProperty("ContactNumber")
	    private String contactnumber;
	   @JsonProperty("Email")
	    private String email;
	   @JsonProperty("DateOfBirth")
	    private String dateofbirth;
	   @JsonProperty("Allergy")
	    private String allergy;
	   @JsonProperty("FoodPreference")
	    private String foodpreference;
	   @JsonProperty("CuisineCategory")
	    private String cuisinecategory;
        
//	    private PatientVitalsPojo vitals;
//	    
//	    public PatientVitalsPojo getVitals() {
//			return vitals;
//		}
//		public void setVitals(PatientVitalsPojo vitals) {
//			this.vitals = vitals;
//		}
		public String getFirstName() {
			return firstname;
		}
		public void setFirstName(String firstname) {
			this.firstname = firstname;
		}
		public String getLastName() {
			return lastname;
		}
		public void setLastName(String lastname) {
			this.lastname = lastname;
		}
		public String getContactNumber() {
			return contactnumber;
		}
		public void setContactNumber(String contactnumber) {
			this.contactnumber = contactnumber;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getDateOfBirth() {
			return dateofbirth;
		}
		public void setDateOfBirth(String dateofbirth) {
			this.dateofbirth = dateofbirth;
		}
		public String getAllergy() {
			return allergy;
		}
		public void setAllergy(String allergy) {
			this.allergy = allergy;
		}
		public String getFoodPreference() {
			return foodpreference;
		}
		public void setFoodPreference(String foodpreference) {
			this.foodpreference = foodpreference;
		}
		public String getCuisineCategory() {
			return cuisinecategory;
		}
		public void setCuisineCategory(String cuisinecategory) {
			this.cuisinecategory = cuisinecategory;
		}
		
		
      
}