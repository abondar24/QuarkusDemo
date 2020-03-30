package org.abondar.experimental.quarkusdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class PersonResourceTest {


    @Inject
    private ObjectMapper mapper;

    @Test
    public void insertPersonTest() throws Exception {
        var person = new Person();
        person.setFirstName("test");
        person.setLastName("test");
        person.setPhoneNumber("0000");

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(person))
                .post("/person/insert")
                .then()
                .statusCode(200)
                .body("id", is(0))
                .body("firstName", is("test"))
                .body("lastName", is("test"))
                .body("phoneNumber", is("0000"));

    }


    @Test
    public void updatePersonTest() throws Exception {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)

                .queryParam("phone", "0000")
                .post("/person/update/{id}", 7)
                .then()
                .statusCode(200)
                .body("phoneNumber", is("0000"));


    }

    @Test
    public void updatePersoNotFoundTest() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("phone", "0000")
                .post("/person/update/{id}", 8)
                .then()
                .statusCode(404);
    }

    @Test
    public void findPersonTest() throws Exception {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)

                .queryParam("id", 7)
                .get("/person/find")
                .then()
                .statusCode(200);

    }

    @Test
    public void findAllTest() throws Exception{
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/all")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void deletePersonTest() throws Exception {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)

                .queryParam("id", 7)
                .delete("/person/delete")
                .then()
                .statusCode(200);

    }
}
