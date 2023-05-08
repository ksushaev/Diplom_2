package all_for_orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ChecksForOrders {

    private static final String PATH_ORDERS = "/api/orders";
    @Step("Заказ для неавторизованного пользователя")
    public ValidatableResponse makeOrderForNotRealUser(OrderWithIngredients order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(PATH_ORDERS)
                .then().log().all();
    }

    @Step("Заказ для авторизованного пользователя")
    public ValidatableResponse makeOrderForRealUser(OrderWithIngredients order, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(PATH_ORDERS)
                .then().log().all();
    }

    @Step("Получить заказы авторизованного пользователя")
    public ValidatableResponse getOrderWithAuth(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .get(PATH_ORDERS)
                .then().log().all();
    }

    @Step("Получить заказы авторизованного пользователя")
    public ValidatableResponse getOrderWithUhAuth() {
        return given()
                .when()
                .get(PATH_ORDERS)
                .then().log().all();
    }
}
