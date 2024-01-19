import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;


public class RegApiTests {

    @Test
    @Story("Позитивные тесты")
    @Tag("positive")
    @DisplayName("Успешная регистрация пользователя")
    void successfulCreateUserTest(){
        String regData = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().method()
                .log().uri()
                .log().body()

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"),
                        "job", is("leader"))
                .extract().response();


    }
    @Test
    @Story("Негативные тесты")
    @Tag("negative")
    @DisplayName("Регистрация без заполнения \"job\"")
    void unsuccessfulCreateUserTest(){
        String regData = "{\n" +
                "    \"name\": \"morpheus\",\n}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().method()
                .log().uri()
                .log().body()

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().response();
    }
    @Test
    @Story("Позитивные тесты")
    @Tag("positive")
    @DisplayName("Изменение имени и место работы пользователя")
    void successfulUpdateUserDataTest(){
        String regData = "{\n" +
                "    \"name\": \"NEO\",\n" +
                "    \"job\": \"terminator\"\n" +
                "}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().method()
                .log().uri()
                .log().body()

                .when()
                .put("https://reqres.in/api/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();
    }
    @Test
    @Story("Негативные тесты")
    @Tag("negative")
    @DisplayName("Регистрация без \"password\"")
    void unsuccessfulLoginUserTest(){
        String regData = "{\n"+"\"email\": \"peter@klaven\"}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().method()
                .log().uri()
                .log().body()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error",is("Missing password"))
                .extract().response();
    }
}
