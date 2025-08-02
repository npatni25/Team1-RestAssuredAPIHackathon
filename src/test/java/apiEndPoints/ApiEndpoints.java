package apiEndPoints;

public enum ApiEndpoints {
	
	//User Login Endpoints
		APILoginPost("/login"),
		APILogoutGet("/logoutdietician"),
		CreateDietician("/dietician"),
	    GetAllMorbidities ("/morbidity"),
	    GetMorbidityByTestname("/morbidity/{morbidityName}"),
	    CreatePatientPost("/patient");
	
	
	private String resources;
	ApiEndpoints(String resources){
	this.resources = resources;	
	}
	public String getResources()
	{
		return resources;
	}

}
