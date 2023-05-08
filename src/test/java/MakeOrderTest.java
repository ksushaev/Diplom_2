import all_for_orders.ChecksForOrders;
import all_for_orders.DifferentIngredients;
import all_for_orders.OrderWithIngredients;
import all_for_user.ChecksForUser;
import all_for_user.CreateUser;
import all_for_user.DataForLoginUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.net.HttpURLConnection.*;

@RunWith(Parameterized.class)
public class MakeOrderTest {
    private ChecksForUser checksForUser = new ChecksForUser();
    private OrderWithIngredients orderWithIngredients;
    private ChecksForOrders checks = new ChecksForOrders();
    CreateUser newUser = CreateUser.getUserWithRandomStringUtils();
    private final DataForLoginUser dataForLoginUser = new DataForLoginUser().from(newUser);
    private int statusCode;
    private String accessToken = "noToken";


    public MakeOrderTest(OrderWithIngredients orderWithIngredients, int statusCode) {
        this.orderWithIngredients = orderWithIngredients;
        this.statusCode = statusCode;
    }

    @Before
    public void setUp() {
        checksForUser = new ChecksForUser();
    }

    @Parameterized.Parameters
    public static Object[][] getDataForOrder() {
        return new Object[][]{
                {DifferentIngredients.getNormalOrder(), HTTP_OK},
                {DifferentIngredients.getOrderWithoutIngredients(), HTTP_BAD_REQUEST},
                {DifferentIngredients.getOrderWithWrongHash(), HTTP_INTERNAL_ERROR},
        };
    }

    @Test
    @DisplayName("Создание заказов с авторизованным пользователем")
    public void MakeOrderByAuthorizedUser() {
        checksForUser.createUserWithOkStatus(newUser);
        ValidatableResponse response = checksForUser.autorizationOfUser(dataForLoginUser)
                .assertThat().statusCode(HTTP_OK);
        accessToken = checksForUser.GoodResponse(response);
        ValidatableResponse responseForCreateOrder = checks.makeOrderForRealUser(orderWithIngredients, accessToken);
        int actualStatusCode = responseForCreateOrder.extract().statusCode();
        Assert.assertEquals(statusCode, actualStatusCode);
    }

    @Test
    @DisplayName("Создать заказ с неавторизованным пользователем")
    public void makeOrderForUnathUser() {
        ValidatableResponse responseForUnathUser = checks.makeOrderForNotRealUser(orderWithIngredients);
        int actualStatusCode = responseForUnathUser.extract().statusCode();
        Assert.assertEquals(statusCode, actualStatusCode);
    }
}
