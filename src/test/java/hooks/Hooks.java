package hooks;

import io.cucumber.java.After;
import baseAPI.StoreIDs;

import static io.restassured.RestAssured.given;

import pojo.tokenManager;

public class Hooks {
	
	//For CLEAN UP
	//To delete stored dietician ID... Run this code at the end for the cleanup
//	@After 
//	public void cleanup() {
//        String token = tokenManager.getAdminToken(); // or however you get your token
//
//        for (String id : StoreIDs.getAll()) {
//            given()
//                .header("Authorization", "Bearer " + token)
//                .delete("/dieticians/" + id)
//                .then()
//                .log().ifError(); // Log only if deletion fails
//        }
//
//        StoreIDs.clear();
//    }
	
//	@After
//	public void clean ()
//	{
//		StoreIDs.clearAll();
//	}
}
