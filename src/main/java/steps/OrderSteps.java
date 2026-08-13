package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderModel;

import static data.OrderData.CREATE_ORDER_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {


    @Step("Successful creation authorized order")
    public static Response successfulAuthorizedOrderCreation(OrderModel order, String accessToken) {
    return  given()
              .header("Authorization", "Bearer " + accessToken)
              .header("Content-Type", "application/json")
              .and()
              .body(order)
              .when()
              .post(CREATE_ORDER_PATH)
              .then()
              .extract().response();
}


    @Step("Successful creation unauthorized order")
    public static Response successfulUnauthorizedOrderCreation(OrderModel order) {
        return  given()
                .header("Content-Type", "application/json")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }


    @Step("Negative creation authorized order without ingredients")
    public static Response errorAuthorizedEmptyOrderCreation(OrderModel order, String accessToken) {
        return  given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }
    @Step("Negative creation authorized order with invalid ingredient Id")
    public static Response errorAuthorizedInvalidIdOrderCreation(OrderModel order, String accessToken) {
        return  given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }


}
