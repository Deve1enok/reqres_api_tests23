import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


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
                .statusCode(201);
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
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(400);
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
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200);
    }
}
