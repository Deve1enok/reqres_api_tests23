import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class StatusApiGetTests {

    @Test
    @DisplayName("Проверка на существующего пользователя с id=2 и его данными")
    void checkSingleUserId2(){
        Integer idUser = 2;
        String emailUser = "janet.weaver@reqres.in";
        String firstName = "Janet";
        String lastName = "Weaver";
        String userAvatar = "https://reqres.in/img/faces/2-image.jpg";
        String urlSupport = "https://reqres.in/#support-heading";
        String textSupport = "To keep ReqRes free, contributions towards server costs are appreciated!";

        get("https://reqres.in/api/users/2")
                .then()
                .log().body()
                .body("data.id", is (idUser))
                .body("data.email", is (emailUser))
                .body("data.first_name", is (firstName))
                .body("data.last_name", is (lastName))
                .body("data.avatar", is (userAvatar))
                .body("support.url", is (urlSupport))
                .body("support.text", is (textSupport))

                .statusCode(200);

    }
    @Test
    @DisplayName("Проверка на несуществующего пользователя")
    void checkSingleUserNotFound(){

        get("https://reqres.in/api/users/23")
                .then()
                .log().body()
                .body(equalTo("{}"))

                .statusCode(404);

    }
}
