import all_for_orders.ChecksForOrders;
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

public class GetOrdersTest {
    private ChecksForUser checksForUser = new ChecksForUser();
    private ChecksForOrders checks = new ChecksForOrders();
    CreateUser newUser = CreateUser.getUserWithRandomStringUtils();
    private final DataForLoginUser dataForLoginUser = new DataForLoginUser().from(newUser);
    private String accessToken = "";

    @Before
    public void setUp() {
        checksForUser = new ChecksForUser();
    }

    @Test
    @DisplayName("Заказы авторизованного пользователя")
    public void getOrdersForAuthUser() {
        checksForUser.createUserWithOkStatus(newUser);
        ValidatableResponse response = checksForUser.autorizationOfUser(dataForLoginUser);
        accessToken = checksForUser.GoodResponse(response);
        checks.getOrderWithAuth(accessToken)
                .assertThat().statusCode(HTTP_OK);
    }


    @Test
    @DisplayName("Заказы неавторизованного пользователя")
    public void getOrdersOForUnauthUser() {
        checks.getOrderWithUhAuth()
                .assertThat().statusCode(HTTP_UNAUTHORIZED);
    }

    @After
    public void delete() {
        checksForUser.deleteUser(accessToken);
    }
}
