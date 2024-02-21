package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.UserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.DataSingleUserId2;
import utils.DataUnknownSingleUser;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static specs.UserSpec.*;

@Tag("all_test")
@Epic("Rest API tests")
public class StatusApiGetTests extends BaseTest {
    DataUnknownSingleUser dataUnknownSingleUser = new DataUnknownSingleUser();
    DataSingleUserId2 dataSingleUserId2 = new DataSingleUserId2();


    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("GET запросы")
    @Story("Позитивные тесты")
    @DisplayName("Проверка на существующего пользователя с id=2 и его данными")
    void checkSingleUserId2(){

            UserResponseModel response = step("Отправляем GET запрос", () ->
                    given(userRequestSpecification)

                            .when()
                            .get("/users/2")

                            .then()
                            .spec(userSuccessResponseSpecification)
                            .extract().as(UserResponseModel.class));


            step("Проверяем тело-ответа", () -> {
                assertEquals(dataSingleUserId2.getId(), response.getData().getId());
                assertEquals(dataSingleUserId2.getEmail(), response.getData().getEmail());
                assertEquals(dataSingleUserId2.getFirst_name(), response.getData().getFirst_name());
                assertEquals(dataSingleUserId2.getLast_name(), response.getData().getLast_name());
                assertEquals(dataSingleUserId2.getAvatar(), response.getData().getAvatar());
            });
        }
    @Test
    @Tag("negative_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("GET запросы")
    @Story("Негативные тесты")
    @DisplayName("Проверка на несуществующего пользователя")
    void checkSingleUserNotFound(){

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
    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("GET запросы")
    @Story("Позитивные тесты")
    @DisplayName("Проверка неизвестного существующего пользователя с id=2 и его данными")
    void checkUnknownSingleUserId2(){

        UserResponseModel response = step("Отправляем GET запрос", () ->
                given(userRequestSpecification)

                        .when()
                        .get("/unknown/2")

                        .then()
                        .spec(userSuccessResponseSpecification)
                        .extract().as(UserResponseModel.class));


        step("Проверяем тело-ответа", () -> {
            assertEquals(dataUnknownSingleUser.getId(), response.getData().getId());
            assertEquals(dataUnknownSingleUser.getName(), response.getData().getName());
            assertEquals(dataUnknownSingleUser.getYear(), response.getData().getYear());
            assertEquals(dataUnknownSingleUser.getColor(), response.getData().getColor());
            assertEquals(dataUnknownSingleUser.getPantoneValue(), response.getData().getPantone_value());

            assertEquals(dataUnknownSingleUser.getUrl(), response.getSupport().getUrl());
            assertEquals(dataUnknownSingleUser.getText(), response.getSupport().getText());
        });
    }
}
