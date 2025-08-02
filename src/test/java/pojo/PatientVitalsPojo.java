package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatientVitalsPojo {
	
	@JsonProperty("Weight")
	private float weight;
	@JsonProperty("Height")
	private float height;
	@JsonProperty("Temperature")
	private float temperature;
	@JsonProperty("SP")
	private float sp;
	@JsonProperty("DP")
	private float dp;
	
	
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getHeight() {
		return height;
	}
	public void setPatientHeight(float height) {
		this.height = height;
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	public float getSP() {
		return sp;
	}
	public void setSP(float sp) {
		this.sp = sp;
	}
	public float getDP() {
		return dp;
	}
	public void setDP(float dp) {
		this.dp = dp;
	}
	
	
	

}