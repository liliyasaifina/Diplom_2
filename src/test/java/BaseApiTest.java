import data.UserData;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static steps.UserSteps.userRemoval;


public class BaseApiTest {
   String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = UserData.BASE_URI;
    }

    @After
    public void cleanUp () {
        if (accessToken != null && !accessToken.isEmpty()) {
            userRemoval(accessToken);
        } else {
            System.out.println("Токен не найден");
        }
    }

}
