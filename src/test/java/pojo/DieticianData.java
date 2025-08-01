package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class DieticianData {

    @JsonProperty("ContactNumber")
    private String ContactNumber;

    @JsonProperty("DateOfBirth")
    private String DateOfBirth;

    @JsonProperty("Education")
    private String Education;

    @JsonProperty("Email")
    private String Email;

    @JsonProperty("Firstname")
    private String Firstname;

    @JsonProperty("HospitalCity")
    private String HospitalCity;

    @JsonProperty("HospitalName")
    private String HospitalName;

    @JsonProperty("HospitalPincode")
    private String HospitalPincode;

    @JsonProperty("HospitalStreet")
    private String HospitalStreet;

    @JsonProperty("Lastname")
    private String Lastname;

    // Optional field
    @JsonProperty("TestCaseId")
    private String TestCaseId;

    // Getters and Setters
    public String getContactNumber() { return ContactNumber; }
    public void setContactNumber(String contactNumber) { ContactNumber = contactNumber; }

    public String getDateOfBirth() { return DateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { DateOfBirth = dateOfBirth; }

    public String getEducation() { return Education; }
    public void setEducation(String education) { Education = education; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }

    public String getFirstname() { return Firstname; }
    public void setFirstname(String firstname) { Firstname = firstname; }

    public String getHospitalCity() { return HospitalCity; }
    public void setHospitalCity(String hospitalCity) { HospitalCity = hospitalCity; }

    public String getHospitalName() { return HospitalName; }
    public void setHospitalName(String hospitalName) { HospitalName = hospitalName; }

    public String getHospitalPincode() { return HospitalPincode; }
    public void setHospitalPincode(String hospitalPincode) { HospitalPincode = hospitalPincode; }

    public String getHospitalStreet() { return HospitalStreet; }
    public void setHospitalStreet(String hospitalStreet) { HospitalStreet = hospitalStreet; }

    public String getLastname() { return Lastname; }
    public void setLastname(String lastname) { Lastname = lastname; }

    public String getTestCaseId() { return TestCaseId; }
    public void setTestCaseId(String testCaseId) { TestCaseId = testCaseId; }
}



