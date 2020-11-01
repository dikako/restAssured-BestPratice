package contract;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ContractTest {
	
	@Test
	public void restGet() {
		given()
			.baseUri("https://reqres.in")
			.contentType(ContentType.JSON)
			.basePath("/api/users")
			.param("page", 2)
			.when()
			.get()
			.then()
			.log()
			.body()
			.body(matchesJsonSchemaInClasspath("usersResponseSchema.json"));
	}

}
