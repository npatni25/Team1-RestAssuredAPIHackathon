package utils;

public class ReplaceDynamicData {
	
	private static String replaceDynamicData(String input, String dieticianId, String dieticianToken, String adminToken) {
	    if (input == null) return null;
	    return input
	        .replace("{dieticianId}", dieticianId)
	        .replace("{dieticianToken}", dieticianToken != null ? dieticianToken : "")
	        .replace("{adminToken}", adminToken != null ? adminToken : "");
	}
}
