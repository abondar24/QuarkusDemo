package org.abondar.experimental.quarkusdemo.rest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class AuthResourceTest {

    @Test
    void authPersonTest() {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/auth/7")
                .then()
                .statusCode(200)
                .body(is(notNullValue()));

    }
}
