import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import model.UserCreateModel;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static data.OrderData.*;
import static data.UserData.*;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.OrderSteps.*;
import static steps.UserSteps.createUser;


public class CreateOrderTest extends BaseApiTest {

    @Before
    public void preliminaryUserCreation(){
        UserCreateModel user = new UserCreateModel(EMAIL, PASSWORD, NAME);
        Response registrationResponse = createUser(user);
        String accessTokenWithPrefix = registrationResponse.jsonPath().getString("accessToken");
        this.accessToken = accessTokenWithPrefix.replaceFirst("^Bearer\\s+", "");
    }

    @Test
    @DisplayName("Successful order creation by authorized user")
    @Description("Basic test for /api/orders endpoint")
        public void successfulAuthorizedUserOrderCreationTest(){
            OrderModel authorizedOrder = new OrderModel(Arrays.asList(INGREDIENT_FIRST, INGREDIENT_SECOND));
            successfulAuthorizedOrderCreation(authorizedOrder, accessToken)
                    .then()
                    .statusCode(SC_OK)
                    .body("success", equalTo(true))
                    .body("order.owner.name", equalTo(UserData.NAME));
    }

    @Test
    @DisplayName("Successful order creation by unauthorized user")
    @Description("Basic test for /api/orders endpoint")
        public void successfulUnauthorizedUserOrderCreationTest(){
            OrderModel unauthorizedOrder = new OrderModel(Arrays.asList(INGREDIENT_FIRST, INGREDIENT_SECOND));
            successfulUnauthorizedOrderCreation(unauthorizedOrder)
                    .then()
                    .body("success", equalTo(true))
                    .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Negative order creation by authorized user without ingredients")
    @Description("Creation empty order by authorized user")
        public void errorAuthorizedUserEmptyOrderCreationTest(){
            OrderModel authorizedEmptyOrder = new OrderModel(Arrays.asList());
            errorAuthorizedEmptyOrderCreation(authorizedEmptyOrder, accessToken)
                    .then()
                    .statusCode(SC_BAD_REQUEST)
                    .body("success", equalTo(false))
                    .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Negative order creation by authorized user with invalid ingredient Id")
    @Description("Creation order by with invalid ingredient Id authorized user")
        public void errorAuthorizedUserInvalidIdOrderCreationTest() {
            OrderModel authorizedEmptyOrder = new OrderModel(Arrays.asList(INGREDIENT_FIRST, INVALID_INGREDIENT_ID));
            errorAuthorizedInvalidIdOrderCreation(authorizedEmptyOrder, accessToken)
                    .then()
                    .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}

