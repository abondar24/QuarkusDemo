package org.abondar.experimental.quarkusdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class DemoResourceTest {

    @Inject
     ObjectMapper mapper;

    @Test
    public void testStatus() throws Exception {

        String res = mapper.writeValueAsString("quarkus app is up");
        given()
                .when()
                .get("/demo/status")
                .then()
                .statusCode(200)
                .body(is(res));

    }

    @Test
    public void testHello() throws Exception {

        String res = mapper.writeValueAsString("Hello Alex");
        given()
                .when()
                .get("/demo/hello/{name}", "Alex")
                .then()
                .statusCode(200)
                .body(is(res));

    }
}
