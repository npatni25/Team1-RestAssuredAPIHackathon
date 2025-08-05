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

import context.ScenarioContext;

	public class SpecificationClass { 
		
		RequestSpecification reqSpec;
       
	    private static final String BASE_URL = ConfigReader.getProperty("baseUrl");
	    private static final String LOG_PATH = ConfigReader.getProperty("LogFilePath");
	    ScenarioContext context= ScenarioContext.getInstance();
	    
	    ResponseSpecification responseSpec;
		PrintStream log;
		
		public SpecificationClass() throws FileNotFoundException
		{
			log = new PrintStream(new FileOutputStream(LOG_PATH, true));
		}
	    
	    private static PrintStream getLogger() throws FileNotFoundException {
	        return new PrintStream(new FileOutputStream(LOG_PATH, true));
	    }

	/*    public static RequestSpecification requestSpecWithToken() throws FileNotFoundException {
	        return new RequestSpecBuilder()
	                .setBaseUri(BASE_URL)
	                //.addHeader("Authorization", "Bearer " + token)
	                .addHeader("Authorization","Bearer "+ context.get("ADMINLOGIN_TOKEN") )
	                .addFilter(RequestLoggingFilter.logRequestTo(getLogger()))
	                .addFilter(ResponseLoggingFilter.logResponseTo(getLogger()))
	                .setContentType(ContentType.JSON)
	                .build();
	    }
	 */   
		public RequestSpecification requestHeadersWithToken()
		{
			System.out.println("Token....."+context.get("ADMINLOGIN_TOKEN"));
			reqSpec = new RequestSpecBuilder()
					.addHeader("Authorization","Bearer "+context.get("ADMINLOGIN_TOKEN") ).setBaseUri(BASE_URL)
					 .addFilter(RequestLoggingFilter.logRequestTo(log))
					 .addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
			return reqSpec;
		}
		
		public RequestSpecification requestHeadersWithoutToken()
		{
			reqSpec = new RequestSpecBuilder().setBaseUri(BASE_URL)
					 .addFilter(RequestLoggingFilter.logRequestTo(log))
					 .addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
			return reqSpec;
		}
		

	    public static RequestSpecification requestSpecWithoutToken() throws FileNotFoundException {
	        return new RequestSpecBuilder()
	                .setBaseUri(BASE_URL)
	                .addFilter(RequestLoggingFilter.logRequestTo(getLogger()))
	                .addFilter(ResponseLoggingFilter.logResponseTo(getLogger()))
	                .setContentType(ContentType.JSON)
	                .build();
	    }

	    public  ResponseSpecification responseSpecOK() {
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
	    
	    public ResponseSpecification responseSpecBuilder()
		{
			responseSpec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
			return responseSpec;
		}
	}



