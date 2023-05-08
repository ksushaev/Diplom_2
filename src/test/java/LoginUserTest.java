import all_for_user.ChecksForUser;
import all_for_user.CreateUser;
import all_for_user.DataForLoginUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class LoginUserTest {
    private ChecksForUser checksForUser = new ChecksForUser();
    CreateUser newUser = CreateUser.getUserWithRandomStringUtils();
    private final DataForLoginUser dataForLoginUser = new DataForLoginUser().from(newUser);
    private String accessToken = "";


    @Before
    public void setUp() {
        checksForUser = new ChecksForUser();
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginAsRealUser() {
        checksForUser.createUserWithOkStatus(newUser);
        ValidatableResponse response = checksForUser.autorizationOfUser(dataForLoginUser);
        System.out.println(accessToken);
        response.assertThat().statusCode(HTTP_OK);
        accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void loginWithWrongPassword() {
        dataForLoginUser.setPassword("123fkfkf");
        checksForUser.autorizationOfUser(dataForLoginUser)
                .assertThat().statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Логин с неверным email")
    public void loginWithWrongLogin() {
        dataForLoginUser.setEmail("privet123@yandex.ru");
        checksForUser.autorizationOfUser(dataForLoginUser)
                .assertThat().statusCode(HTTP_UNAUTHORIZED);
    }

    @After
    public void delete() {
        checksForUser.deleteUser(accessToken);
    }
}
