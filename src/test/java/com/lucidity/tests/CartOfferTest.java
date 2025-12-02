package com.lucidity.tests;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * Test class for Zomato Cart Offer API
 * Contains 15 comprehensive test cases covering various scenarios
 * 
 * @author Your Name
 * @date December 2024
 */
@Epic("Zomato Cart Offer System")
@Feature("Cart Offer API")
public class CartOfferTest extends BaseTest {
    
    /**
     * TEST CASE 1: Verify flat amount discount for P1 segment
     * 
     * Description: This test verifies that when a P1 segment customer applies 
     * a flat amount discount offer, the correct discount is applied to cart
     * 
     * Test Steps:
     * 1. Setup user segment mock for P1
     * 2. Create offer with FLATX type, value 10 for P1 segment
     * 3. Apply offer to cart with value 200
     * 4. Verify final cart value is 190 (200 - 10)
     * 
     * Expected Result: Cart value should be 190
     */
    @Test(priority = 1, description = "Verify flat amount discount for P1 segment")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Flat Amount Discount")
    public void testFlatAmountDiscountP1() {
        System.out.println("\n--- TEST 1: Flat Amount Discount for P1 Segment ---");
        
        // Step 1: Setup mock for user segment
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        // Step 2: Create offer
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":10,\"customer_segment\":[\"p1\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .when()
            .post("/api/v1/offer")
            .then()
            .statusCode(200);
        System.out.println("✓ Offer created: FLATX, Rs.10 off for P1");
        
        // Step 3: Apply offer to cart
        String cartPayload = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .when()
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        // Step 4: Verify result
        Assert.assertEquals(response.statusCode(), 200);
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 190.0, "Cart value should be 190 after Rs.10 discount");
        System.out.println("✓ Verification: Expected=190, Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 2: Verify flat percentage discount for P1 segment
     * 
     * Description: This test verifies percentage-based discount calculation
     * Formula: Final Amount = Cart Value - (Cart Value × Percentage / 100)
     * 
     * Test Steps:
     * 1. Setup user segment mock for P1
     * 2. Create offer with FLAT% type, value 10% for P1 segment
     * 3. Apply offer to cart with value 200
     * 4. Verify final cart value is 180 (200 - 20)
     * 
     * Expected Result: Cart value should be 180
     */
    @Test(priority = 2, description = "Verify percentage discount for P1 segment")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Percentage Discount")
    public void testFlatPercentageDiscountP1() {
        System.out.println("\n--- TEST 2: Flat Percentage Discount for P1 Segment ---");
        
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        // Create 10% discount offer
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLAT%\",\"offer_value\":10,\"customer_segment\":[\"p1\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .post("/api/v1/offer");
        System.out.println("✓ Offer created: FLAT%, 10% off for P1");
        
        // Apply offer
        String cartPayload = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        // Verify: 200 - (200 * 10/100) = 180
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 180.0, "Cart value should be 180 after 10% discount");
        System.out.println("✓ Calculation: 200 - (200 × 10/100) = " + cartValue);
        System.out.println("✓ Verification: Expected=180, Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 3: Verify flat amount discount for P2 segment
     * 
     * Description: Tests that P2 segment users receive their segment-specific discount
     * This ensures segmentation logic works correctly for different customer groups
     * 
     * Test Steps:
     * 1. Setup user segment mock for P2
     * 2. Create offer with Rs.20 off for P2 segment
     * 3. Apply offer to cart with value 300
     * 4. Verify final cart value is 280
     * 
     * Expected Result: Cart value should be 280
     */
    @Test(priority = 3, description = "Verify flat amount discount for P2 segment")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Segment-based Discount")
    public void testFlatAmountDiscountP2() {
        System.out.println("\n--- TEST 3: Flat Amount Discount for P2 Segment ---");
        
        createUserSegmentMock(2, "p2");
        System.out.println("✓ User segment mock created: User 2 -> P2");
        
        // Create offer for P2 with Rs.20 off
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":20,\"customer_segment\":[\"p2\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .post("/api/v1/offer");
        System.out.println("✓ Offer created: FLATX, Rs.20 off for P2");
        
        // Apply offer
        String cartPayload = "{\"cart_value\":300,\"user_id\":2,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        // Verify: 300 - 20 = 280
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 280.0, "Cart value should be 280");
        System.out.println("✓ Verification: Expected=280, Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 4: Verify percentage discount for P3 segment
     * 
     * Description: Validates percentage discount for P3 premium segment
     * P3 customers typically receive higher discount percentages
     * 
     * Test Steps:
     * 1. Setup user segment mock for P3
     * 2. Create offer with 15% off for P3 segment
     * 3. Apply offer to cart with value 500
     * 4. Verify final cart value is 425
     * 
     * Expected Result: Cart value should be 425
     */
    @Test(priority = 4, description = "Verify percentage discount for P3 segment")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Segment-based Discount")
    public void testPercentageDiscountP3() {
        System.out.println("\n--- TEST 4: Percentage Discount for P3 Segment ---");
        
        createUserSegmentMock(3, "p3");
        System.out.println("✓ User segment mock created: User 3 -> P3");
        
        // Create 15% discount for P3
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLAT%\",\"offer_value\":15,\"customer_segment\":[\"p3\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .post("/api/v1/offer");
        System.out.println("✓ Offer created: FLAT%, 15% off for P3");
        
        // Apply offer
        String cartPayload = "{\"cart_value\":500,\"user_id\":3,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        // Verify: 500 - (500 * 15/100) = 425
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 425.0, "Cart value should be 425");
        System.out.println("✓ Calculation: 500 - (500 × 15/100) = " + cartValue);
        System.out.println("✓ Verification: Expected=425, Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 5: Verify no discount for non-matching segment
     * 
     * Description: Security test to ensure users cannot access offers 
     * meant for other customer segments
     * 
     * Test Steps:
     * 1. Setup user segment mock for P1
     * 2. Create offer only for P2 segment
     * 3. Try to apply offer as P1 user
     * 4. Verify no discount is applied (cart value remains same)
     * 
     * Expected Result: Cart value should remain 200
     */
    @Test(priority = 5, description = "Verify no discount for non-matching segment")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Negative Testing")
    public void testNoDiscountForNonMatchingSegment() {
        System.out.println("\n--- TEST 5: No Discount for Non-Matching Segment ---");
        
        // User is in P1 segment
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        // But offer is for P2 only
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":10,\"customer_segment\":[\"p2\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .post("/api/v1/offer");
        System.out.println("✓ Offer created for P2 only (User is P1)");
        
        // Try to apply offer
        String cartPayload = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        // Verify no discount applied
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 200.0, "Cart value should remain 200");
        System.out.println("✓ Verification: No discount applied, Cart value=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 6: Verify offer applicable to multiple segments
     * 
     * Description: Tests that a single offer can be created for multiple 
     * customer segments and applies correctly
     * 
     * Test Steps:
     * 1. Setup user segment mock for P1
     * 2. Create offer for both P1 and P2 segments
     * 3. Apply offer as P1 user
     * 4. Verify discount is applied correctly
     * 
     * Expected Result: Cart value should be 185
     */
    @Test(priority = 6, description = "Verify offer for multiple segments")
    @Severity(SeverityLevel.NORMAL)
    @Story("Multi-Segment Offers")
    public void testOfferForMultipleSegments() {
        System.out.println("\n--- TEST 6: Offer for Multiple Segments ---");
        
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        // Create offer for both P1 and P2
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":15,\"customer_segment\":[\"p1\",\"p2\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .post("/api/v1/offer");
        System.out.println("✓ Offer created for multiple segments: [P1, P2]");
        
        // P1 user applies offer
        String cartPayload = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 185.0, "Cart value should be 185");
        System.out.println("✓ Verification: Expected=185, Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 7: Verify restaurant-specific offers
     * 
     * Description: Ensures offers are isolated per restaurant
     * Offer created for Restaurant 1 should not apply to Restaurant 2
     * 
     * Test Steps:
     * 1. Setup user segment mock
     * 2. Create offer for restaurant 1
     * 3. Try to use offer at restaurant 2
     * 4. Verify no discount is applied
     * 
     * Expected Result: Cart value should remain 200
     */
    @Test(priority = 7, description = "Verify restaurant specific offers")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Restaurant Isolation")
    public void testRestaurantSpecificOffers() {
        System.out.println("\n--- TEST 7: Restaurant Specific Offers ---");
        
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        // Create offer for restaurant 1
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":10,\"customer_segment\":[\"p1\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .post("/api/v1/offer");
        System.out.println("✓ Offer created for Restaurant 1");
        
        // Try to use offer at restaurant 2
        String cartPayload = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":2}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        // Should not get discount at different restaurant
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 200.0, "No discount for different restaurant");
        System.out.println("✓ Verification: Offer not applied at Restaurant 2 -> PASSED\n");
    }
    
    /**
     * TEST CASE 8: Verify discount greater than cart value
     * 
     * Description: Edge case test to ensure cart value doesn't go negative
     * when discount is greater than cart amount
     * 
     * Test Steps:
     * 1. Setup user segment mock
     * 2. Create offer with discount value 300
     * 3. Apply to cart with value 200
     * 4. Verify cart value is not negative
     * 
     * Expected Result: Cart value should be >= 0
     */
    @Test(priority = 8, description = "Verify discount greater than cart value")
    @Severity(SeverityLevel.NORMAL)
    @Story("Edge Cases")
    public void testDiscountGreaterThanCartValue() {
        System.out.println("\n--- TEST 8: Discount Greater Than Cart Value ---");
        
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        // Create large discount
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":300,\"customer_segment\":[\"p1\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .post("/api/v1/offer");
        System.out.println("✓ Offer created: Rs.300 off (larger than cart)");
        
        // Apply to smaller cart
        String cartPayload = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        // Cart value should not be negative
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertTrue(cartValue >= 0, "Cart value should not be negative");
        System.out.println("✓ Verification: Cart value=" + cartValue + " (non-negative) -> PASSED\n");
    }
    
    /**
     * TEST CASE 9: Verify 100% discount scenario
     * 
     * Description: Tests maximum discount scenario where cart becomes free
     * 
     * Test Steps:
     * 1. Setup user segment mock
     * 2. Create offer with 100% discount
     * 3. Apply to cart
     * 4. Verify cart value becomes 0
     * 
     * Expected Result: Cart value should be 0
     */
    @Test(priority = 9, description = "Verify 100% discount")
    @Severity(SeverityLevel.NORMAL)
    @Story("Edge Cases")
    public void testFullDiscount() {
        System.out.println("\n--- TEST 9: 100% Discount Scenario ---");
        
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        // Create 100% discount
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLAT%\",\"offer_value\":100,\"customer_segment\":[\"p1\"]}";
        given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .post("/api/v1/offer");
        System.out.println("✓ Offer created: 100% off");
        
        String cartPayload = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 0.0, "Cart value should be 0");
        System.out.println("✓ Verification: Expected=0, Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 10: Verify offer creation API response
     * 
     * Description: Validates the offer creation endpoint returns correct response
     * 
     * Test Steps:
     * 1. Send POST request to create offer
     * 2. Verify HTTP status code is 200
     * 3. Verify response message is "success"
     * 
     * Expected Result: response_msg = "success"
     */
    @Test(priority = 10, description = "Verify offer creation response")
    @Severity(SeverityLevel.CRITICAL)
    @Story("API Validation")
    public void testOfferCreationResponse() {
        System.out.println("\n--- TEST 10: Offer Creation API Response ---");
        
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":10,\"customer_segment\":[\"p1\"]}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(offerPayload)
            .when()
            .post("/api/v1/offer")
            .then()
            .extract().response();
        
        Assert.assertEquals(response.statusCode(), 200);
        String responseMsg = response.jsonPath().getString("response_msg");
        Assert.assertEquals(responseMsg, "success", "Response should be success");
        System.out.println("✓ Status Code: " + response.statusCode());
        System.out.println("✓ Response Message: " + responseMsg + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 11: Verify different discount values for same segment
     * 
     * Description: Tests that newer offers override or coexist with older offers
     * for the same segment
     * 
     * Test Steps:
     * 1. Setup user segment mock for P1
     * 2. Create first offer with Rs.10 off
     * 3. Create second offer with Rs.25 off
     * 4. Apply offer and verify which discount is applied
     * 
     * Expected Result: One of the discounts should be applied
     */
    @Test(priority = 11, description = "Verify multiple offers for same segment")
    @Severity(SeverityLevel.NORMAL)
    @Story("Multiple Offers")
    public void testMultipleOffersForSameSegment() {
        System.out.println("\n--- TEST 11: Multiple Offers for Same Segment ---");
        
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        // Create first offer
        String offer1 = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":10,\"customer_segment\":[\"p1\"]}";
        given().contentType(ContentType.JSON).body(offer1).post("/api/v1/offer");
        System.out.println("✓ First offer created: Rs.10 off");
        
        // Create second offer
        String offer2 = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":25,\"customer_segment\":[\"p1\"]}";
        given().contentType(ContentType.JSON).body(offer2).post("/api/v1/offer");
        System.out.println("✓ Second offer created: Rs.25 off");
        
        // Apply offer
        String cartPayload = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertTrue(cartValue < 200, "Some discount should be applied");
        System.out.println("✓ Verification: Cart value=" + cartValue + " (discount applied) -> PASSED\n");
    }
    
    /**
     * TEST CASE 12: Verify decimal cart values
     * 
     * Description: Tests that system handles decimal cart values correctly
     * Real-world carts often have decimal amounts (taxes, service charges)
     * 
     * Test Steps:
     * 1. Setup user segment mock
     * 2. Create offer
     * 3. Apply to cart with decimal value (199.99)
     * 4. Verify calculation accuracy
     * 
     * Expected Result: Correct calculation with decimals
     */
    @Test(priority = 12, description = "Verify decimal cart values")
    @Severity(SeverityLevel.NORMAL)
    @Story("Decimal Handling")
    public void testDecimalCartValues() {
        System.out.println("\n--- TEST 12: Decimal Cart Values ---");
        
        createUserSegmentMock(1, "p1");
        System.out.println("✓ User segment mock created: User 1 -> P1");
        
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":10.50,\"customer_segment\":[\"p1\"]}";
        given().contentType(ContentType.JSON).body(offerPayload).post("/api/v1/offer");
        System.out.println("✓ Offer created: Rs.10.50 off");
        
        String cartPayload = "{\"cart_value\":199.99,\"user_id\":1,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        double cartValue = response.jsonPath().getDouble("cart_value");
        double expected = 189.49; // 199.99 - 10.50
        Assert.assertEquals(cartValue, expected, 0.01, "Decimal calculation should be accurate");
        System.out.println("✓ Calculation: 199.99 - 10.50 = " + cartValue);
        System.out.println("✓ Verification: Expected=" + expected + ", Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 13: Verify small percentage discount
     * 
     * Description: Tests precision with small percentage values
     * 
     * Test Steps:
     * 1. Setup user segment mock
     * 2. Create offer with 5% discount
     * 3. Apply to cart
     * 4. Verify accurate percentage calculation
     * 
     * Expected Result: Cart value should be 190 (5% of 200 = 10)
     */
    @Test(priority = 13, description = "Verify small percentage discount")
    @Severity(SeverityLevel.NORMAL)
    @Story("Percentage Calculations")
    public void testSmallPercentageDiscount() {
        System.out.println("\n--- TEST 13: Small Percentage Discount ---");
        
        createUserSegmentMock(2, "p2");
        System.out.println("✓ User segment mock created: User 2 -> P2");
        
        // Create 5% discount
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLAT%\",\"offer_value\":5,\"customer_segment\":[\"p2\"]}";
        given().contentType(ContentType.JSON).body(offerPayload).post("/api/v1/offer");
        System.out.println("✓ Offer created: 5% off");
        
        String cartPayload = "{\"cart_value\":200,\"user_id\":2,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 190.0, "5% of 200 = 10, so cart should be 190");
        System.out.println("✓ Calculation: 200 - (200 × 5/100) = " + cartValue);
        System.out.println("✓ Verification: Expected=190, Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 14: Verify large cart value with percentage discount
     * 
     * Description: Boundary test with large cart amounts
     * 
     * Test Steps:
     * 1. Setup user segment mock
     * 2. Create 20% discount offer
     * 3. Apply to large cart value (5000)
     * 4. Verify correct calculation
     * 
     * Expected Result: Cart value should be 4000
     */
    @Test(priority = 14, description = "Verify large cart value")
    @Severity(SeverityLevel.MINOR)
    @Story("Boundary Testing")
    public void testLargeCartValue() {
        System.out.println("\n--- TEST 14: Large Cart Value with Discount ---");
        
        createUserSegmentMock(3, "p3");
        System.out.println("✓ User segment mock created: User 3 -> P3");
        
        String offerPayload = "{\"restaurant_id\":1,\"offer_type\":\"FLAT%\",\"offer_value\":20,\"customer_segment\":[\"p3\"]}";
        given().contentType(ContentType.JSON).body(offerPayload).post("/api/v1/offer");
        System.out.println("✓ Offer created: 20% off for large cart");
        
        String cartPayload = "{\"cart_value\":5000,\"user_id\":3,\"restaurant_id\":1}";
        Response response = given()
            .contentType(ContentType.JSON)
            .body(cartPayload)
            .post("/api/v1/cart/apply_offer")
            .then()
            .extract().response();
        
        double cartValue = response.jsonPath().getDouble("cart_value");
        Assert.assertEquals(cartValue, 4000.0, "20% of 5000 = 1000, so cart should be 4000");
        System.out.println("✓ Calculation: 5000 - (5000 × 20/100) = " + cartValue);
        System.out.println("✓ Verification: Expected=4000, Actual=" + cartValue + " -> PASSED\n");
    }
    
    /**
     * TEST CASE 15: Verify all three segments with different offers
     * 
     * Description: Integration test verifying all segments work independently
     * 
     * Test Steps:
     * 1. Create offers for P1, P2, and P3
     * 2. Test each segment gets correct discount
     * 3. Verify no cross-segment application
     * 
     * Expected Result: Each segment gets only its designated offer
     */
    @Test(priority = 15, description = "Verify all segments with different offers")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Integration Testing")
    public void testAllSegmentsWithDifferentOffers() {
        System.out.println("\n--- TEST 15: All Segments Integration Test ---");
        
        // Create offers for all segments
        String offerP1 = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":10,\"customer_segment\":[\"p1\"]}";
        String offerP2 = "{\"restaurant_id\":1,\"offer_type\":\"FLATX\",\"offer_value\":15,\"customer_segment\":[\"p2\"]}";
        String offerP3 = "{\"restaurant_id\":1,\"offer_type\":\"FLAT%\",\"offer_value\":20,\"customer_segment\":[\"p3\"]}";
        
        given().contentType(ContentType.JSON).body(offerP1).post("/api/v1/offer");
        given().contentType(ContentType.JSON).body(offerP2).post("/api/v1/offer");
        given().contentType(ContentType.JSON).body(offerP3).post("/api/v1/offer");
        System.out.println("✓ Offers created for all segments");
        
        // Test P1
        createUserSegmentMock(1, "p1");
        String cartP1 = "{\"cart_value\":200,\"user_id\":1,\"restaurant_id\":1}";
        Response responseP1 = given().contentType(ContentType.JSON).body(cartP1).post("/api/v1/cart/apply_offer").then().extract().response();
        Assert.assertEquals(responseP1.jsonPath().getDouble("cart_value"), 190.0);
        System.out.println("✓ P1 verification passed: 200 - 10 = 190");
        
        // Test P2
        createUserSegmentMock(2, "p2");
        String cartP2 = "{\"cart_value\":200,\"user_id\":2,\"restaurant_id\":1}";
        Response responseP2 = given().contentType(ContentType.JSON).body(cartP2).post("/api/v1/cart/apply_offer").then().extract().response();
        Assert.assertEquals(responseP2.jsonPath().getDouble("cart_value"), 185.0);
        System.out.println("✓ P2 verification passed: 200 - 15 = 185");
        
        // Test P3
        createUserSegmentMock(3, "p3");
        String cartP3 = "{\"cart_value\":200,\"user_id\":3,\"restaurant_id\":1}";
        Response responseP3 = given().contentType(ContentType.JSON).body(cartP3).post("/api/v1/cart/apply_offer").then().extract().response();
        Assert.assertEquals(responseP3.jsonPath().getDouble("cart_value"), 160.0);
        System.out.println("✓ P3 verification passed: 200 - 40 = 160");
        
        System.out.println("✓ All segments integration test -> PASSED\n");
    }
}