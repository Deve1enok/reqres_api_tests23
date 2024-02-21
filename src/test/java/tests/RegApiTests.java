package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.RegistrationSpec.*;
import static specs.UserSpec.userSuccessResponseSpecification;

@Tag("all_test")
@Epic("Rest API tests")
public class RegApiTests extends BaseTest {
    CreateUserRequestModel createData = new CreateUserRequestModel();
    RegistrationAndLoginRequestUser loginData = new RegistrationAndLoginRequestUser();
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
    void successfulCreateUserTest(){

        createData.setName(dataCreateUser.getName());
        createData.setJob(dataCreateUser.getJob());

        CreateUserResponseModel response = step("Отправляем POST запрос", ()->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .post("/users")

                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals(dataCreateUser.getName(), response.getName());
            assertEquals(dataCreateUser.getJob(), response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }
    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Позитивные тесты")
    @DisplayName("Регистрация без заполнения \"job\"")
    void successfulCreateUserWithoutDataJobTest(){

        createData.setName(dataCreateUser.getName());

        CreateUserResponseModel response = step("Отправляем POST запрос", ()->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .post("/users")

                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals(dataCreateUser.getName(), response.getName());
            assertNull(response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }
    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("PUT запросы")
    @Story("Позитивные тесты")
    @DisplayName("Изменение имени и место работы пользователя")
    void successfulUpdateUserDataTest(){

        createData.setName(dataUpdateUser.getName());
        createData.setJob(dataUpdateUser.getJob());

        CreateUserResponseModel response = step("Отправляем PUT запрос", ()->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .put("/users/2")

                        .then()
                        .spec(userSuccessResponseSpecification)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals(dataUpdateUser.getName(), response.getName());
            assertEquals(dataUpdateUser.getJob(), response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }
    @Test
    @Tag("negative_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Негативные тесты")
    @DisplayName("Регистрация без \"password\"")
    void unsuccessfulRegistrationUserWithoutPasswordTest(){

        loginData.setEmail(dataUserPeter.getEmail());

        RegistrationAndLoginResponseModel response = step("Отправляем POST запрос", ()->
                given(createRequestSpec)
                        .body(loginData)
                        .when()
                        .post("/register")

                        .then()
                        .spec(errorResponseSpec)
                        .extract().as(RegistrationAndLoginResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals(dataMissPassword.getErrorPassword(), response.getError());

        });
    }
    @Test
    @Tag("positive_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Позитивные тесты")
    @DisplayName("Успешная авторизация пользователя")
    void successfulLoginUserTest(){

        loginData.setEmail(dataUserEve.getEmail());
        loginData.setPassword(dataUserEve.getPassword());

        CreateUserResponseModel response = step("Отправляем POST запрос", ()->
                given(createRequestSpec)
                        .body(loginData)
                        .when()
                        .post("/login")

                        .then()
                        .spec(createLoginResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertNotNull(response.getToken());
        });
    }
    @Test
    @Tag("negative_test")
    @Owner("Fazlyakhemtov D.A.")
    @Feature("POST запросы")
    @Story("Негативные тесты")
    @DisplayName("Авторизация без \"password\"")
    void unsuccessfulLoginUserWithoutPasswordTest(){

        loginData.setEmail(dataUserPeter.getEmail());

        RegistrationAndLoginResponseModel response = step("Отправляем POST запрос", ()->
                given(createRequestSpec)
                        .body(loginData)
                        .when()
                        .post("/login")

                        .then()
                        .spec(errorResponseSpec)
                        .extract().as(RegistrationAndLoginResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals(dataMissPassword.getErrorPassword(), response.getError());

        });
    }
}
