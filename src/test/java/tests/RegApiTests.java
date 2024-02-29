package tests;

import data.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.CreateUserRequestModel;
import models.CreateUserResponseModel;
import models.LoginUserRequestModel;
import models.RegistrationAndLoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.UserSpecification.*;

@Tag("all_test")
@Epic("Rest API tests")
public class RegApiTests extends BaseTest {
    CreateUserRequestModel createData = new CreateUserRequestModel();
    LoginUserRequestModel loginData = new LoginUserRequestModel();
    DataPostCreateUser dataCreateUser = new DataPostCreateUser();
    DataPutUpdateUser dataUpdateUser = new DataPutUpdateUser();
    DataRegistrationPeterUser dataUserPeter = new DataRegistrationPeterUser();
    DataMissingPasswordUser dataMissPassword = new DataMissingPasswordUser();
    DataLoginEveUser dataUserEve = new DataLoginEveUser();

    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Позитивные тесты")
    @DisplayName("Успешная регистрация пользователя")
    void successfulCreateUserTest() {

        createData.setName(dataCreateUser.getName());
        createData.setJob(dataCreateUser.getJob());

        CreateUserResponseModel response = step("Отправляем POST запрос", () ->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .post("/users")

                        .then()
                        .spec(createResponseSpec201)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(dataCreateUser.getName()).isEqualTo(response.getName());
            assertThat(dataCreateUser.getJob()).isEqualTo(response.getJob());
            assertThat(response.getId()).isNotNull()
                    .isNotEmpty();
            assertThat(response.getCreatedAt()).isNotNull()
                    .isNotEmpty();
        });
    }

    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Позитивные тесты")
    @DisplayName("Регистрация без заполнения \"job\"")
    void successfulCreateUserWithoutDataJobTest() {

        createData.setName(dataCreateUser.getName());

        CreateUserResponseModel response = step("Отправляем POST запрос", () ->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .post("/users")

                        .then()
                        .spec(createResponseSpec201)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(dataCreateUser.getName()).isEqualTo(response.getName());
            assertThat(response.getJob()).isNull();
            assertThat(response.getId()).isNotNull()
                    .isNotEmpty();
            assertThat(response.getCreatedAt()).isNotNull()
                    .isNotEmpty();
        });
    }

    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("PUT запросы")
    @Story("Позитивные тесты")
    @DisplayName("Изменение имени и место работы пользователя")
    void successfulUpdateUserDataTest() {

        createData.setName(dataUpdateUser.getName());
        createData.setJob(dataUpdateUser.getJob());

        CreateUserResponseModel response = step("Отправляем PUT запрос", () ->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .put("/users/2")

                        .then()
                        .spec(userSuccessResponseSpec200)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(dataUpdateUser.getName()).isEqualTo(response.getName());
            assertThat(dataUpdateUser.getJob()).isEqualTo(response.getJob());
            assertThat(response.getUpdatedAt()).isNotNull()
                    .isNotEmpty();
        });
    }

    @Test
    @Tag("negative_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Негативные тесты")
    @DisplayName("Регистрация без \"password\"")
    void unsuccessfulRegistrationUserWithoutPasswordTest() {

        loginData.setEmail(dataUserPeter.getEmail());

        RegistrationAndLoginResponseModel response = step("Отправляем POST запрос", () ->
                given(createRequestSpec)
                        .body(loginData)
                        .when()
                        .post("/register")

                        .then()
                        .spec(errorResponseSpec400)
                        .extract().as(RegistrationAndLoginResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(dataMissPassword.getErrorPassword()).isEqualTo(response.getError());
        });
    }

    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Позитивные тесты")
    @DisplayName("Успешная авторизация пользователя")
    void successfulLoginUserTest() {

        loginData.setEmail(dataUserEve.getEmail());
        loginData.setPassword(dataUserEve.getPassword());

        CreateUserResponseModel response = step("Отправляем POST запрос", () ->
                given(createRequestSpec)
                        .body(loginData)
                        .when()
                        .post("/login")

                        .then()
                        .spec(createLoginResponseSpec200)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(response.getToken()).isNotNull()
                    .isNotEmpty();
        });
    }

    @Test
    @Tag("negative_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Негативные тесты")
    @DisplayName("Авторизация без \"password\"")
    void unsuccessfulLoginUserWithoutPasswordTest() {

        loginData.setEmail(dataUserPeter.getEmail());

        RegistrationAndLoginResponseModel response = step("Отправляем POST запрос", () ->
                given(createRequestSpec)
                        .body(loginData)
                        .when()
                        .post("/login")

                        .then()
                        .spec(errorResponseSpec400)
                        .extract().as(RegistrationAndLoginResponseModel.class));

        step("Проверяем тело-ответа", () -> {
            assertThat(dataMissPassword.getErrorPassword()).isEqualTo(response.getError());
        });
    }
}
