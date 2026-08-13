package data;

import com.github.javafaker.Faker;

public class UserData {
    public static final String BASE_URI = "https://stellarburgers.education-services.ru";

    static Faker faker = new Faker();
    public static final String EMAIL = faker.internet().emailAddress();
    public static final String PASSWORD = faker.name().firstName() + "123";
    public static final String NAME = faker.name().name();


    public static final String EMPTY_EMAIL = "";
    public static final String EMPTY_PASSWORD = "";
    public static final String EMPTY_NAME = "";
    public static final String INVALID_EMAIL = faker.internet().emailAddress();
    public static final String INVALID_PASSWORD = faker.name().firstName() + System.currentTimeMillis();

    public static final String CREATE_USER_PATH = "/api/auth/register";
    public static final String LOGIN_USER_PATH = "/api/auth/login";
    public static final String DELETE_USER_PATH = "/api/auth/user";
}
