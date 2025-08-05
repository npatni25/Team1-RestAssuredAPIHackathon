package utils;


	import io.restassured.builder.RequestSpecBuilder;
	import io.restassured.builder.ResponseSpecBuilder;
	import io.restassured.http.ContentType;
	import io.restassured.filter.log.RequestLoggingFilter;
	import io.restassured.filter.log.ResponseLoggingFilter;
	import io.restassured.specification.RequestSpecification;
	import io.restassured.specification.ResponseSpecification;

	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.PrintStream;

	public class SpecificationClass { 

	    private static final String BASE_URL = ConfigReader.getProperty("baseUrl");
	    private static final String LOG_PATH = ConfigReader.getProperty("LogFilePath");

	    private static PrintStream getLogger() throws FileNotFoundException {
	        return new PrintStream(new FileOutputStream(LOG_PATH, true));
	    }

	    public static RequestSpecification requestSpecWithToken(String token) throws FileNotFoundException {
	        return new RequestSpecBuilder()
	                .setBaseUri(BASE_URL)
	                .addHeader("Authorization", "Bearer " + token)
	                .addFilter(RequestLoggingFilter.logRequestTo(getLogger()))
	                .addFilter(ResponseLoggingFilter.logResponseTo(getLogger()))
	                .setContentType(ContentType.JSON)
	                .build();
	    }

	    public static RequestSpecification requestSpecWithoutToken() throws FileNotFoundException {
	        return new RequestSpecBuilder()
	                .setBaseUri(BASE_URL)
	                .addFilter(RequestLoggingFilter.logRequestTo(getLogger()))
	                .addFilter(ResponseLoggingFilter.logResponseTo(getLogger()))
	                .setContentType(ContentType.JSON)
	                .build();
	    }

	    public static ResponseSpecification responseSpecOK() {
	        return new ResponseSpecBuilder()
	                .expectStatusCode(200)
	                .expectContentType(ContentType.JSON)
	                .build();
	    }

	    public static ResponseSpecification responseSpecCreated() {
	        return new ResponseSpecBuilder()
	                .expectStatusCode(201)
	                .expectContentType(ContentType.JSON)
	                .build();
	    }
	}