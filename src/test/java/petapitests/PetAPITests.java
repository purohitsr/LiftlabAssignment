package petapitests;
import static io.restassured.RestAssured.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;


import io.restassured.filter.Filter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matcher.*;

import java.io.InputStream;


@Epic("PetAPIs")
@Feature("GetUpdate Pets")
public class PetAPITests extends SetUp {
	
	
	@Test(priority = 1)
	@Description("Example test for fetching avaiable pet lists")
    @Severity(SeverityLevel.CRITICAL)
    @Story("End to End tests using rest-assured")
    @Step("Fetch List of avaiable Pets")
	public void testGetAllAvaiablePets(ITestContext context) {
		
		try {
			
	     InputStream petlistjsonschema1 = getClass ().getClassLoader ()
			  .getResourceAsStream ("petlistjsonschema.json");
	     
		 Response response = given ()
		 .when ()
         .accept("application/json")
         .get ("/pet/findByStatus?status=available")
         .then ().assertThat()
         .body (JsonSchemaValidator.matchesJsonSchema(petlistjsonschema1))		 
         .assertThat().statusCode(200)
         .extract().response();
		 
		 String sresponse = response.getBody().asString();
		 System.out.println("******"+sresponse);
		 
		 
		  JsonPath j = new JsonPath(response.asString());
		  
		  String petId = j.getString("id[0]");
	      
	      context.setAttribute("id", petId);
		}catch(Exception e) {
			e.printStackTrace();
		}
         
	}
	
	
	

	
	  @Test(priority = 2)
	  public void testUpdatePet(ITestContext context) {
		  String id = (String) context.getAttribute("id");
		  String sbody = "{\n"
		  		+ "  \"id\": "+id+",\n"
		  		+ "  \"category\": {\n"
		  		+ "    \"id\": 0,\n"
		  		+ "    \"name\": \"string\"\n"
		  		+ "  },\n"
		  		+ "  \"name\": \"Moti\",\n"
		  		+ "  \"photoUrls\": [\n"
		  		+ "    \"string\"\n"
		  		+ "  ],\n"
		  		+ "  \"tags\": [\n"
		  		+ "    {\n"
		  		+ "      \"id\": 0,\n"
		  		+ "      \"name\": \"string\"\n"
		  		+ "    }\n"
		  		+ "  ],\n"
		  		+ "  \"status\": \"available\"\n"
		  		+ "}";
		   
		  Response response = given ()
		         .accept("application/json")
		         .contentType("application/json")
		         .body(sbody)
		         .when ()
		         .put ("/pet")
		         .then ()
		         .assertThat().statusCode(200)
		         .extract().response();
				  
				 JsonPath j = new JsonPath(response.asString());
				  
				 String petId = j.getString("id");
				 
				 String name = j.getString("id");
				  
                  Assert.assertEquals(j.getString("name"), "Moti");
                  
			      context.setAttribute("id", petId);
				 
					
	}
	
	
	@Test(priority = 3)
	public void testGetPetDetails(ITestContext context) {
		
		String petid = (String) context.getAttribute("id");
		
		 Response response = given ()
		         .accept("application/json")
		         .contentType("application/json")
		         .when ()
		         .get ("/pet/"+petid)
		         .then ()
		         .assertThat().statusCode(200)
		         .extract().response();
		 
		 String sresponse = response.getBody().asString();
		 System.out.println("***********************"+sresponse);
	}
}
