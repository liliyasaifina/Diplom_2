package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.UserCreateModel;
import model.UserLoginModel;

import static data.UserData.*;
import static io.restassured.RestAssured.given;
import static org.apache.hc.core5.http.HttpStatus.*;


public class UserSteps {

  @Step("Creation User account")

    public static Response createUser(UserCreateModel user){
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(user)
                .when()
                .post(CREATE_USER_PATH)
                .then()
                .extract().response();
    }


    @Step("Creation User account with existent User account data")
    public static Response createExistentUser(UserCreateModel user) {
      return given()
              .header("Content-Type", "application/json")
              .and()
              .body(user)
              .when()
              .post(CREATE_USER_PATH)
              .then()
              .extract().response();
    }


    @Step("Creation User account with empty required field (absence Email)")
    public static Response emptyRequiredFieldUser(UserCreateModel user) {
      return given()
                .header("Content-Type", "application/json")
                .and()
                .body(user)
                .when()
                .post(CREATE_USER_PATH)
                .then()
                .extract().response();
    }


    @Step("Removal existent User account")
    public static Response userRemoval(String accessToken){
      return  given()
              .header("Authorization", "Bearer " + accessToken)
              .header("Content-Type", "application/json")
              .when()
              .delete(DELETE_USER_PATH)
              .then()
              .statusCode(SC_ACCEPTED)
              .extract().response();
    }


    @Step("Login User account with valid data")
    public static Response logInUser(UserLoginModel user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_PATH).then()
                .extract().response();
    }


    @Step("Login User account with invalid password")
    public static Response invalidPassword(UserLoginModel user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_PATH).then()
                .extract().response();
    }


    @Step("Login User account with invalid Email")
    public static Response invalidEmail(UserLoginModel user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_PATH).then()
                .extract().response();
    }
}
