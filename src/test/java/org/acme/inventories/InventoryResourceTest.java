package org.acme.inventories;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class InventoryResourceTest {

    @BeforeEach
    void cleanup() {
        given().when().get("/api/inventory")
                .then().statusCode(200)
                .extract().jsonPath().getList("id", String.class)
                .forEach(id -> given().when().delete("/api/inventory/{id}", id).then().statusCode(204));
    }

    @Test
    void createItem() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "itemName": "Tomato",
                          "quantity": 10,
                          "minimumThreshold": 4
                        }
                        """)
                .when().post("/api/inventory")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("itemName", equalTo("Tomato"))
                .body("status", equalTo("AVAILABLE"));
    }

    @Test
    void negativeQuantityValidation() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "itemName": "Onion",
                          "quantity": -1,
                          "minimumThreshold": 0
                        }
                        """)
                .when().post("/api/inventory")
                .then().statusCode(400)
                .body("fieldErrors.quantity", notNullValue());
    }

    @Test
    void statusComputation() {
        String outId = createAndGetId("Out Item", 0, 2);
        String lowId = createAndGetId("Low Item", 2, 2);
        String okId = createAndGetId("Ok Item", 5, 2);

        given().when().get("/api/inventory/{id}", outId).then().statusCode(200).body("status", equalTo("OUT"));
        given().when().get("/api/inventory/{id}", lowId).then().statusCode(200).body("status", equalTo("LOW"));
        given().when().get("/api/inventory/{id}", okId).then().statusCode(200).body("status", equalTo("AVAILABLE"));
    }

    @Test
    void inOutAdjustMovementLogic() {
        String id = createAndGetId("Beef", 10, 3);

        given().contentType("application/json").body("{\"movementType\":\"IN\",\"quantity\":5}")
                .when().patch("/api/inventory/{id}/quantity", id)
                .then().statusCode(200).body("quantity", equalTo(15));

        given().contentType("application/json").body("{\"movementType\":\"OUT\",\"quantity\":3}")
                .when().patch("/api/inventory/{id}/quantity", id)
                .then().statusCode(200).body("quantity", equalTo(12));

        given().contentType("application/json").body("{\"movementType\":\"ADJUST\",\"quantity\":7}")
                .when().patch("/api/inventory/{id}/quantity", id)
                .then().statusCode(200).body("quantity", equalTo(7));
    }

    @Test
    void movementQueryByMonth() {
        String id = createAndGetId("Fish", 10, 3);
        given().contentType("application/json").body("{\"movementType\":\"IN\",\"quantity\":2}")
                .when().patch("/api/inventory/{id}/quantity", id)
                .then().statusCode(200);

        given().when().get("/api/inventory/{id}/movements?month=2026-05", id)
                .then().statusCode(200);

        given().when().get("/api/inventory/movements?month=2026-05")
                .then().statusCode(200);
    }

    @Test
    void deleteNotFound() {
        given().when().delete("/api/inventory/{id}", UUID.randomUUID())
                .then().statusCode(404);
    }

    private String createAndGetId(String name, int quantity, int min) {
        return given()
                .contentType("application/json")
                .body("""
                        {
                          "itemName": "%s",
                          "quantity": %d,
                          "minimumThreshold": %d
                        }
                        """.formatted(name, quantity, min))
                .when().post("/api/inventory")
                .then().statusCode(201)
                .extract().jsonPath().getString("id");
    }
}
