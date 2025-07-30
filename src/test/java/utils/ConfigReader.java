package utils;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    static Properties prop;

    

    
	public static void loadProperties() {
		try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
            prop = new Properties();
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public static String getProperty(String key) {
        if (prop == null) loadProperties();
        return prop.getProperty(key);
    }

}
