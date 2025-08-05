package pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class MorbidityPojo  {
	
    @JsonIgnore 
    private int statusCode;

    @JsonIgnore 
   	private String statusText, scenario;
    private String morbidityName;
    
    public String getScenario() {
		return scenario;
	}
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	
    public String getmorbidityName() {
		return morbidityName;
	}
	public void setmorbidityName(String morbidityName) {
		this.morbidityName = morbidityName;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	
}
