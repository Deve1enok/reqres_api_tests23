package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;


public class UserSpecification {
    public static RequestSpecification createRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .log().uri()
            .log().headers()
            .log().body();
    public static RequestSpecification userRequestSpecification = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers();
    public static ResponseSpecification createResponseSpec201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();
    public static ResponseSpecification errorResponseSpec400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(BODY)
            .build();
    public static ResponseSpecification createLoginResponseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();
    public static ResponseSpecification userSuccessResponseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();
    public static ResponseSpecification userNotFoundResponseSpec404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();
}