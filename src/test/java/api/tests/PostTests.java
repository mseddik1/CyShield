package api.tests;

import api.base.BaseTests;
import apis.models.Posts;
import com.fasterxml.jackson.databind.JsonNode;
import com.utils.AllureUtils;
import com.utils.RetryAnalyzer;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class PostTests extends BaseTests {
    private static final Logger log = LoggerFactory.getLogger(PostTests.class);


    @Test(
            groups = {"smoke", "regression"},
            retryAnalyzer = RetryAnalyzer.class,
            description = "User should be able to retrieve posts successfully"
    )
    @Story("Get Posts")
    @Severity(SeverityLevel.CRITICAL)
    public void getPostsPaginated(){
        Response response = postService.getAllPosts(5,5);

        log.info("Time: {}",response.getTime());
        JsonNode resJson = Utils.readAsJson(response.then().extract().asString());


        Posts post = Utils.deserialize(resJson, Posts.class);

        log.info("Title:  {}", post.getPosts().get(0).getTitle());

        response .then()
                .statusCode(200)
                .time(lessThan(1500L))
                .body(matchesJsonSchemaInClasspath("schemas/posts-schema.json"))
                .body("limit", equalTo(5))
                .body("skip", equalTo(5))
                .body("posts.size()", equalTo(5));

        Assert.assertNotNull(post.getPosts());
        Assert.assertFalse(post.getPosts().isEmpty(), "Posts list should not be empty");
        Assert.assertNotNull(post.getPosts().get(0).getTitle(), "First post title should not be null");
        AllureUtils.attachJson("Json Response: ", response.body().asPrettyString());


    }
}
