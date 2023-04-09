package org.abondar.experimental.quarkusdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.abondar.experimental.quarkusdemo.model.Job;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class JobRouteTest {

    @Inject
    ObjectMapper mapper;

    @Test
    public void jobAddTest() throws Exception{

        var job = new Job(100,"Clean");
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(job))
                .post("/job")
                .then()
                .statusCode(200)
                .body("id", is(100))
                .body("jobName",is(job.jobName()));
    }


    @Test
    public void jobFindTest() throws Exception {
        var job = new Job(100,"Clean");
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(job))
                .post("/job")
                .then()
                .statusCode(200);

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/job")
                .then()
                .statusCode(200)
                .body("get(0).jobName",is(job.jobName()));
    }
}
