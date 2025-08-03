package baseAPI;

import io.restassured.response.Response;
import pojo.tokenManager;

import java.util.ArrayList;
import java.util.List;

public class StoreIDs {

    private static List<String> ids = new ArrayList<>();
    private static List<String> usernames = new ArrayList<>();
    private static List<String> passwords = new ArrayList<>();
    private static List<String> dieticianTokens = new ArrayList<>();
    private static List<String> patientids = new ArrayList<>();



    public static void addId(String id) {
        ids.add(id);
    }

    public static void addUsername(String username) {
        usernames.add(username);
    }

    public static void addPassword(String password) {
        passwords.add(password);
    }
    
    public static void addDieticianToken(String dieticianToken) {
    	dieticianTokens.add(dieticianToken);
    }
    
    public static void addPatientId(String patientid) {
        ids.add(patientid);
    }
    
    public static List<String> getAllIds() {
        return ids;
    }

    public static List<String> getAllUsernames() {
        return usernames;
    }

    public static List<String> getAllPasswords() {
        return passwords;
    }
    
    public static List<String> getAllDieticianTokens(){
    	return dieticianTokens;
    }
 
    public static List<String> getAllPatientIds() {
        return patientids;
    }

//    public static void clearAll() {
//        ids.clear();
//        usernames.clear();
//        passwords.clear();
//    } 						//----Use it at the end thorugh After hooks while cleanup

    public static void storeCreatedDietician(Response response) {
        String dieticianId = response.jsonPath().getString("id");
        if (dieticianId != null && !dieticianId.isEmpty()) {
            addId(dieticianId);
            System.out.println("Dietician Id stored is: " + dieticianId);
        }
    }

    public static String storeDieticianPassword(Response response) {
        String dieticianPassword = response.jsonPath().getString("loginPassword");
        if (dieticianPassword != null && !dieticianPassword.isEmpty()) {
            addPassword(dieticianPassword);
            System.out.println("Dietician Password stored is: " + dieticianPassword);
        }
		return dieticianPassword;
    }

    public static String storeDieticianEmailAsUserName(Response response) {
        String dieticianUsername = response.jsonPath().getString("Email");
        if (dieticianUsername != null && !dieticianUsername.isEmpty()) {
            addUsername(dieticianUsername);
            System.out.println("Dietician Username stored is: " + dieticianUsername);
        }
		return dieticianUsername;
    }

    
    public static void storeDieticianToken(Response response) {
    String dieticianToken = response.jsonPath().getString("token");
    if (dieticianToken != null && !dieticianToken.isEmpty()) {
        tokenManager.setDieticianToken(dieticianToken);
        System.out.println("Stored dietician token: " + dieticianToken);
    }

    }
    
    public static void storeCreatedPatient(Response response) {
        String patientId = response.jsonPath().getString("id");
        if (patientId != null && !patientId.isEmpty()) {
            addId(patientId);
            System.out.println("Dietician Id stored is: " + patientId);
        }
    }

	
 public static String getLatestStoredDieticianID()
    {
    	 if (!ids.isEmpty()) {
    	        return ids.get(ids.size() - 1);
    	    }
    	    return null;
    }


}

