import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.UserCreateModel;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static data.UserData.*;
import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static steps.UserSteps.*;


public class CreateUserTest extends BaseApiTest {

    @Test
    @DisplayName("Successful User account creation")
    @Description("Basic test for /api/auth/register endpoint")
    public void successfulUserCreationTest() {
        UserCreateModel user = new UserCreateModel(EMAIL, PASSWORD, NAME);
        Response registrationResponse = createUser(user);
        registrationResponse
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        String accessTokenWithPrefix = registrationResponse.jsonPath().getString("accessToken");
        this.accessToken = accessTokenWithPrefix.replaceFirst("^Bearer\\s+", "");
    }

    @Test
    @DisplayName("Negative duplicate User data test")
    @Description("Test the prohibition of creating two identical users")
    public void errorExistingUserCreationTest() {
        UserCreateModel user = new UserCreateModel(EMAIL, PASSWORD, NAME);
        Response registrationResponse = createUser(user);
        registrationResponse
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        String accessTokenWithPrefix = registrationResponse.jsonPath().getString("accessToken");
        this.accessToken = accessTokenWithPrefix.replaceFirst("^Bearer\\s+", "");
        createExistentUser(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("User already exists"));
    }

    @Test
    @DisplayName("Negative invalid User data test")
    @Description("Creation user test with absence email data field")
    public void emptyRequiredFieldUserCreation() {
        UserCreateModel user = new UserCreateModel(EMPTY_EMAIL, PASSWORD, NAME);
        emptyRequiredFieldUser(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}




