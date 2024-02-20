package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import models.CreateUserRequestModel;
import models.CreateUserResponseModel;
import models.RegistrationRequestUser;
import models.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.RegistrationSpec.*;
import static specs.UserSpec.userSuccessResponseSpecification;

@Tag("all_test")
public class RegApiTests extends BaseTest {
    CreateUserRequestModel createData = new CreateUserRequestModel();
    RegistrationRequestUser loginData = new RegistrationRequestUser();

    @Test
    @Story("Позитивные тесты")
    @Tag("positive_test")
    @DisplayName("Успешная регистрация пользователя")
    void successfulCreateUserTest(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        createData.setName("morpheus");
        createData.setJob("leader");

        CreateUserResponseModel response = step("Отправляем POST запрос", ()->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .post("users")

                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });


    }
    @Test
    @Story("Негативные тесты")
    @Tag("negative_test")
    @DisplayName("Регистрация без заполнения \"job\"")
    void unsuccessfulCreateUserTest(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        createData.setName("morpheus");

        CreateUserResponseModel response = step("Отправляем POST запрос", ()->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .post("users")

                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals("morpheus", response.getName());
            assertNotEquals("",response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }
    @Test
    @Story("Позитивные тесты")
    @Tag("positive_test")
    @DisplayName("Изменение имени и место работы пользователя")
    void successfulUpdateUserDataTest(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        createData.setName("NEO");
        createData.setJob("developer");

        CreateUserResponseModel response = step("Отправляем PUT запрос", ()->
                given(createRequestSpec)
                        .body(createData)
                        .when()
                        .put("users/2")

                        .then()
                        .spec(userSuccessResponseSpecification)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals("NEO", response.getName());
            assertEquals("developer", response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }
    @Test
    @Story("Негативные тесты")
    @Tag("negative_test")
    @DisplayName("Регистрация без \"password\"")
    void unsuccessfulLoginUserTest(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        loginData.setEmail("peter@klaven");

        RegistrationResponseModel response = step("Отправляем POST запрос", ()->
                given(createRequestSpec)
                        .body(loginData)
                        .when()
                        .post("/register")

                        .then()
                        .spec(errorResponseSpec)
                        .extract().as(RegistrationResponseModel.class));

        step("Проверяем тело-ответа", ()->{
            assertEquals("Missing password", response.getError());

        });

    }
}
