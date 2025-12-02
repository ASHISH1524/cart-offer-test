package com.lucidity.tests;

import io.restassured.RestAssured;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base test class containing setup and teardown methods
 * This class manages MockServer lifecycle and provides helper methods
 */
public class BaseTest {
    
    protected ClientAndServer mockServer;
    protected String baseUrl = "http://localhost:8080";
    
    /**
     * Setup method - Runs once before all tests
     * Starts the MockServer and configures REST Assured base URI
     */
    @BeforeClass
    public void setup() {
        mockServer = ClientAndServer.startClientAndServer(8080);
        RestAssured.baseURI = baseUrl;
        System.out.println("========================================");
        System.out.println("Mock server started on port 8080");
        System.out.println("========================================\n");
    }
    
    /**
     * Teardown method - Runs once after all tests
     * Stops the MockServer to free up resources
     */
    @AfterClass
    public void teardown() {
        if (mockServer != null) {
            mockServer.stop();
            System.out.println("\n========================================");
            System.out.println("Mock server stopped");
            System.out.println("========================================");
        }
    }
    
    /**
     * Reset method - Runs before each test
     * Clears all mock expectations to ensure test isolation
     */
    @BeforeMethod
    public void resetMocks() {
        mockServer.reset();
    }
    
    /**
     * Helper method to create user segment mock
     * This mocks the GET /api/v1/user_segment endpoint
     * 
     * @param userId - User ID to mock
     * @param segment - Customer segment (p1, p2, or p3)
     */
    protected void createUserSegmentMock(int userId, String segment) {
        mockServer.when(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/api/v1/user_segment")
                .withQueryStringParameter("user_id", String.valueOf(userId))
        ).respond(
            HttpResponse.response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"segment\":\"" + segment + "\"}")
        );
    }
}