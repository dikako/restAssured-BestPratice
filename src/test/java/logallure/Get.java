package logallure;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Get {
	
	RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setContentType(ContentType.JSON)
            .setBasePath("/api/users")
            .build();
	
	@BeforeTest
    public void setFilter(){
        RestAssured.filters(new AllureRestAssured());
    }
	
	@Test
    public void bodyTest() {    			
    			
    	given()
	    	.spec(requestSpec)
	    	.param("page", 2)
	    	.when()
	    	.get()
	    	.then()
	    	.log().body()
	    	.body(matchesJsonSchemaInClasspath("usersResponseSchema.json"));
	}
}
