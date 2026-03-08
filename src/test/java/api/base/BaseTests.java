package api.base;

import api.ApiClient;
import apis.services.AuthService;
import apis.services.PostService;
import apis.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.listeners.TestListeners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import utils.ConfigManager;
import utils.Utils;

import java.lang.reflect.Method;
@Listeners(TestListeners.class)
public class BaseTests {

    private static final Logger log = LoggerFactory.getLogger(BaseTests.class);
    protected static final JsonNode testDataFile = Utils.readAsJsonResource("testData/apiTestData.json");

    protected ApiClient apiClient;
    protected AuthService authService;
    protected PostService postService;
    private String env;

    @BeforeClass(alwaysRun = true )
    @Parameters({"env"})
    public void initialize(@Optional("prod") String env) {
        this.env = env;

    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method m) {
        log.info("Initiating {} | Thread: {}", m.getName(), Thread.currentThread().getId());

        apiClient = new ApiClient(ConfigManager.get(env+".api.baseUrl"));
        authService = new AuthService(apiClient);
        postService = new PostService(apiClient);
    }
}