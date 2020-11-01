package logallure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostUser {
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
    	
    	User user = User.builder()
    			.name("Dikakoko")
    			.job("Automation")
    			.build();
    			
    	given()
	    	.spec(requestSpec)
	        .body(user)
	        .when()
	        .post()
	        .then()
	        .log().body()
	        .body(matchesJsonSchemaInClasspath("userPostResponse.json"))
	        .body("name", equalTo("Dikakoko"))
	        .and()
	        .body("job", equalTo("Automation"));
        
    }
}