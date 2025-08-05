package payload;

import utils.ConfigReader;
import pojo.LoginPojo;

public class LoginPayload {
	
	LoginPojo LPojo = new LoginPojo(); 
	
	
	public LoginPojo postLoginPayload()
	{
		LPojo.setUserLoginEmail(ConfigReader.getProperty("username"));
		LPojo.setPassword(ConfigReader.getProperty("password"));
		return LPojo;
	}
	
	

}