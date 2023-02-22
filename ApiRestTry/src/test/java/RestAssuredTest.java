import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {

    @Test
    public void SingleUserBddTest(){
        when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                //body'de data'nın altındaki email'de "janet.weaver@reqres.in" yazısı var mı kontrol et
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .time(lessThan(1000L));
    }

    @Test
    public void SingleUserTest(){
        RestAssured.baseURI="https://reqres.in";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("/api/users/2");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        System.out.println(bodyAsString);
        Assert.assertTrue(bodyAsString.contains("janet.weaver@reqres.in"), "String did not found");

    }

    @Test
    public void CreateUserTest(){
        String postData = "{\"name\":\"morpheus\",\"job\":\"leader\"}";

        given()
                .contentType(ContentType.JSON)
                .body(postData)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }
}
