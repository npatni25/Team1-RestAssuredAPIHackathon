package apiEndPoints;

public enum ApiEndpoints {
	
	//User Login Endpoints
		APILoginPost("/login"),
		APILogoutGet("/logoutdietician"),
		CreateDietician("/dietician"),

		PATIENT_API_PATH(""),

		InvalidDieticianEndPoint("/diet"),

	    GetAllMorbidities ("/morbidity"),
	    GetMorbidityByTestname("/morbidity/{morbidityName}");
	    
	
	private String resources;
	ApiEndpoints(String resources){
	this.resources = resources;	
	}
	public String getResources()
	{
		return resources;
	}

}
