package baseAPI;

import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;

public class StoreIDs {

    private static List<String> ids = new ArrayList<>();
    private static List<String> usernames = new ArrayList<>();
    private static List<String> passwords = new ArrayList<>();

    public static void addId(String id) {
        ids.add(id);
    }

    public static void addUsername(String username) {
        usernames.add(username);
    }

    public static void addPassword(String password) {
        passwords.add(password);
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

    public static void storeDieticianPassword(Response response) {
        String dieticianPassword = response.jsonPath().getString("loginPassword");
        if (dieticianPassword != null && !dieticianPassword.isEmpty()) {
            addPassword(dieticianPassword);
            System.out.println("Dietician Password stored is: " + dieticianPassword);
        }
    }

    public static void storeDieticianEmailAsUserName(Response response) {
        String dieticianUsername = response.jsonPath().getString("Email");
        if (dieticianUsername != null && !dieticianUsername.isEmpty()) {
            addUsername(dieticianUsername);
            System.out.println("Dietician Username stored is: " + dieticianUsername);
        }
    }
}



