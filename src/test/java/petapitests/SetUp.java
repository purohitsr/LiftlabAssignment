package petapitests;
import static org.hamcrest.Matchers.lessThan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
public class SetUp {
	   public static final Logger LOGGER = LogManager.getLogger();
	    @BeforeClass
	    public static void setup () {
          
	        RequestSpecification requestSpecification = new RequestSpecBuilder ().setBaseUri (
	                "https://petstore.swagger.io/v2")
	            .addHeader ("Content-Type", "application/json")
	            .addHeader ("Accept", "application/json")
	            .addFilter (new RequestLoggingFilter ())
	            .addFilter (new ResponseLoggingFilter ())
	            .build ();

	        ResponseSpecification responseSpecification = new ResponseSpecBuilder ().expectResponseTime (lessThan (20000L))
	            .build ();
	        RestAssured.requestSpecification = requestSpecification;
	        RestAssured.responseSpecification = responseSpecification;
	
}
	 
}
