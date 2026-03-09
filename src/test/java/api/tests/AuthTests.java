package api.tests;

import api.base.BaseTests;
import apis.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import utils.AllureUtils;
import utils.RetryAnalyzer;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

public class AuthTests extends BaseTests {
    private static final Logger log = LoggerFactory.getLogger(AuthTests.class);

    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class,
            description = "User should be able to log in successfully")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    public void validLogin(){
        JsonNode validUser = testDataFile.path("users").path("valid").get(0);
        Response response = authService.login(validUser);
        log.info("Time : {}",response.getTime());
        JsonNode resJson = Utils.readAsJson(response.then().extract().asString());


//        User user = Utils.deserialize(resJson, User.class);

        User user = response.as(User.class);

        log.info("Access Token {}", user.getAccessToken());
        response .then()
                .statusCode(200)
                .time(lessThan(1500L))
                .body(matchesJsonSchemaInClasspath("schemas/login-schema.json"));

        Assert.assertEquals(user.getUsername(), validUser.get("username").asText());
        Assert.assertNotNull(user.getAccessToken());
        Assert.assertNotNull(user.getRefreshToken());

        AllureUtils.attachJson("Json Response: ", response.body().asPrettyString());
        log.info("Login successful for user: {}", user.getUsername());
    }


    @Test(groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class,
            description = "Invalid user should not be able to log in")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    public void invalidLogin(){
        JsonNode validUser = testDataFile.path("users").path("invalid");
        Response response = authService.login(validUser);
        log.info("Time : {}",response.getTime());
        JsonNode resJson = Utils.readAsJson(response.then().extract().asString());


//        User user = Utils.deserialize(resJson, User.class);

        User user = response.as(User.class);

        log.info("Error Message {}", user.getMessage());
        response.then().log().all();
        response .then()
                .statusCode(400)
                .time(lessThan(1500L))
                .body(matchesJsonSchemaInClasspath("schemas/message-schema.json"));


        Assert.assertEquals(user.getMessage(), "Invalid credentials", "Check error message!");


        AllureUtils.attachJson("Json Response: ", response.body().asPrettyString());
    }
}
