package tests;

import data.DataSingleUserId2;
import data.DataUnknownSingleUser;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.UserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.UserSpecification.*;

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
    void checkSingleUserId2() {

        UserResponseModel response = step("Отправляем GET запрос", () ->
                given(requestSpecification)

                        .when()
                        .get("/users/2")

                        .then()
                        .spec(successResponseSpec200)
                        .extract().as(UserResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(dataSingleUserId2.getId()).isEqualTo(response.getData().getId());
            assertThat(dataSingleUserId2.getEmail()).isEqualTo(response.getData().getEmail());
            assertThat(dataSingleUserId2.getFirstName()).isEqualTo(response.getData().getFirstName());
            assertThat(dataSingleUserId2.getLastName()).isEqualTo(response.getData().getLastName());
            assertThat(dataSingleUserId2.getAvatar()).isEqualTo(response.getData().getAvatar());
        });
    }

    @Test
    @Tag("negative_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("GET запросы")
    @Story("Негативные тесты")
    @DisplayName("Проверка на несуществующего пользователя")
    void checkSingleUserNotFound() {

        UserResponseModel response = step("Отправляем GET запрос", () ->
                given(requestSpecification)

                        .when()
                        .get("/users/23")

                        .then()
                        .spec(userNotFoundResponseSpec404)
                        .extract().as(UserResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(response.getData()).isNull();
            assertThat(response.getSupport()).isNull();
        });
    }

    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("GET запросы")
    @Story("Позитивные тесты")
    @DisplayName("Проверка неизвестного существующего пользователя с id=2 и его данными")
    void checkUnknownSingleUserId2() {

        UserResponseModel response = step("Отправляем GET запрос", () ->
                given(requestSpecification)

                        .when()
                        .get("/unknown/2")

                        .then()
                        .spec(successResponseSpec200)
                        .extract().as(UserResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(dataUnknownSingleUser.getId()).isEqualTo(response.getData().getId());
            assertThat(dataUnknownSingleUser.getName()).isEqualTo(response.getData().getName());
            assertThat(dataUnknownSingleUser.getYear()).isEqualTo(response.getData().getYear());
            assertThat(dataUnknownSingleUser.getColor()).isEqualTo(response.getData().getColor());
            assertThat(dataUnknownSingleUser.getPantoneValue()).isEqualTo(response.getData().getPantone_value());

            assertThat(dataUnknownSingleUser.getUrl()).isEqualTo(response.getSupport().getUrl());
            assertThat(dataUnknownSingleUser.getText()).isEqualTo(response.getSupport().getText());
        });
    }
}
