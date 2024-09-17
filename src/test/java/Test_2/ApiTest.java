package Test_2;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
    }

    @Test
    public void addNewDevice() {
        // Define the request Payload
        String requestBody = "{"
            + "\"name\": \"Apple Max Pro 1TB\","
            + "\"data\": {"
            + "\"year\": 2023,"
            + "\"price\": 7999.99,"
            + "\"CPU model\": \"Apple ARM A7\","
            + "\"Hard disk size\": \"1 TB\""
            + "}"
            + "}";

        // Send POST request
        Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post();

        // Log response details
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Response Status Code: " + response.getStatusCode());

        // Validate the response
        Assert.assertEquals(response.statusCode(), 201, "Expected status code 201 but got " + response.statusCode());

        String id = response.path("id");
        String createdAt = response.path("createdAt");

        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(createdAt, "CreatedAt should not be null");

        // Validate response content
        Integer year = response.path("data.year");
        Assert.assertEquals(year, Integer.valueOf(2023), "Year mismatch");

        Double price = response.path("data.price");
        Assert.assertEquals(price, Double.valueOf(7999.99), 0.01, "Price mismatch");

        Assert.assertEquals(response.path("name"), "Apple Max Pro 1TB", "Name mismatch");
        Assert.assertEquals(response.path("data.CPU model"), "Apple ARM A7", "CPU model mismatch");
        Assert.assertEquals(response.path("data.Hard disk size"), "1 TB", "Hard disk size mismatch");
    }
}
