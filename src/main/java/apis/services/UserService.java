package apis.services;

import api.ApiClient;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private final ApiClient apiClient;

    public UserService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response getAllUsers() {
        return apiClient.get("/users");
    }

    public Response getAllUsers(int limit, int skip) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("skip", skip);
        return apiClient.get("/users", queryParams);
    }

    public Response getUserById(int userId) {
        return apiClient.get("/users/" + userId);
    }

    public Response createUser(Object userBody) {
        return apiClient.post("/users/add", userBody);
    }
}