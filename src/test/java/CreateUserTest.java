import all_for_user.ChecksForUser;
import all_for_user.CreateUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.*;

public class CreateUserTest {

    CreateUser user;
    private ChecksForUser checksForUser = new ChecksForUser();
    private String accessToken;

    @Before
    public void setUp() {
        checksForUser = new ChecksForUser();
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createOfUser() {
        user = CreateUser.getUserWithRandomStringUtils();
        ValidatableResponse response = checksForUser.createUserWithOkStatus(user)
                .assertThat().statusCode(SC_OK)
                .and()
                .assertThat().body("accessToken", notNullValue());
        accessToken = checksForUser.GoodResponse(response);
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    public void createOfExistUser() {
        user = CreateUser.getNotRandomUser();
        ValidatableResponse response = checksForUser.createUserWithOkStatus(user)
                .assertThat().statusCode(SC_OK);
        checksForUser.createUserWithOkStatus(user)
                .assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(HTTP_FORBIDDEN);
        accessToken = checksForUser.GoodResponse(response);
    }

    @Test
    @DisplayName(" Создание пользователя без одного поля")
    public void createOfUserWithoutName() {
        user = CreateUser.getUserWithRandomStringUtils();
        user.setName(null);
        checksForUser.createUserWithOkStatus(user)
                .assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(HTTP_FORBIDDEN);
        accessToken = "";
    }

    @After
    public void delete() {
        checksForUser.deleteUser(accessToken);
    }
}

