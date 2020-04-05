package org.abondar.experimental.quarkusdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.abondar.experimental.quarkusdemo.model.Job;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class JobRouteTest {

    @Inject
    private ObjectMapper mapper;

    @Test
    public void jobAddTest() throws Exception{

        var job = new Job(100,"Clean");
        given()
                .when()
                .body(mapper.writeValueAsString(job))
                .post("/job/add")
                .then()
                .statusCode(200);
    }


    @Test
    public void jobFindTest() throws Exception{

        given()
                .when()
                .get("/job/find")
                .then()
                .statusCode(200);
    }
}
