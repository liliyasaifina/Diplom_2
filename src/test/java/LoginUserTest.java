import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.UserCreateModel;
import model.UserLoginModel;
import org.junit.Before;
import org.junit.Test;

import static data.UserData.*;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static steps.UserSteps.*;


public class LoginUserTest extends BaseApiTest {

    @Before
    public void preliminaryUserCreation(){
        UserCreateModel user = new UserCreateModel(EMAIL, PASSWORD, NAME);
        Response registrationResponse = createUser(user);
        String accessTokenWithPrefix = registrationResponse.jsonPath().getString("accessToken");
        this.accessToken = accessTokenWithPrefix.replaceFirst("^Bearer\\s+", "");
           }

    @Test
    @DisplayName("Successful User account login")
    @Description("Basic test for /api/auth/login endpoint")
    public void successfulUserLogInTest() {
        UserLoginModel userLogin = new UserLoginModel(EMAIL, PASSWORD);
        logInUser(userLogin)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Negative invalid User data test")
    @Description("Login test the invalid password data")
    public void errorInvalidPasswordTest(){
        UserLoginModel userInvalidPassword = new UserLoginModel(EMAIL, INVALID_PASSWORD);
        invalidPassword(userInvalidPassword)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
         }

    @Test
    @DisplayName("Negative invalid User data test")
    @Description("Login test the invalid Email data")
    public void errorInvalidEmailTest(){
        UserLoginModel userInvalidEmail = new UserLoginModel(INVALID_EMAIL, PASSWORD);
        invalidEmail(userInvalidEmail)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
