package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiClient {

    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private RequestSpecification request() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json");
    }

    private RequestSpecification request(Map<String, ?> queryParams) {
        RequestSpecification request = request();
        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }
        return request;
    }

    public Response get(String endpoint) {
        return request().when().get(endpoint);
    }

    public Response get(String endpoint, Map<String, ?> queryParams) {
        return request(queryParams).when().get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return request().body(body).when().post(endpoint);
    }

    public Response put(String endpoint, Object body) {
        return request().body(body).when().put(endpoint);
    }

    public Response patch(String endpoint, Object body) {
        return request().body(body).when().patch(endpoint);
    }

    public Response delete(String endpoint) {
        return request().when().delete(endpoint);
    }
}