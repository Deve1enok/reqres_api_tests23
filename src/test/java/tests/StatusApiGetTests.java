package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import models.UserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static specs.UserSpec.*;

@Tag("all_test")
public class StatusApiGetTests extends BaseTest {

    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("GET запросы")
    @Story("Позитивные тесты")
    @DisplayName("Проверка на существующего пользователя с id=2 и его данными")
    void checkSingleUserId2(){
        SelenideLogger.addListener("allure", new AllureSelenide());

            UserResponseModel response = step("Отправляем GET запрос", () ->
                    given(userRequestSpecification)

                            .when()
                            .get("/users/2")

                            .then()
                            .spec(userSuccessResponseSpecification)
                            .extract().as(UserResponseModel.class));


            step("Проверяем тело-ответа", () -> {
                assertEquals(2, response.getData().getId());
                assertEquals("janet.weaver@reqres.in", response.getData().getEmail());
                assertEquals("Janet", response.getData().getFirst_name());
                assertEquals("Weaver", response.getData().getLast_name());
                assertEquals("https://reqres.in/img/faces/2-image.jpg", response.getData().getAvatar());
            });
        }
    @Test
    @Tag("negative_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("GET запросы")
    @Story("Негативные тесты")
    @DisplayName("Проверка на несуществующего пользователя")
    void checkSingleUserNotFound(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        UserResponseModel response = step("Отправляем GET запрос", () ->
                given(userRequestSpecification)

                        .when()
                        .get("/users/23")

                        .then()
                        .spec(userNotFoundResponseSpecification)
                        .extract().as(UserResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertNull(response.getData());
            assertNull(response.getSupport());
        });
    }
}
