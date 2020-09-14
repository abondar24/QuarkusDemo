package org.abondar.experimental.quarkusdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class PersonResourceTest {


    @Inject
    private ObjectMapper mapper;

    private Header authHeader;

    @BeforeEach
    public void setUp() {
        var token = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)

                .queryParam("id", 7)
                .get("/person/auth")
                .asString();

        authHeader = new Header("Authorization", "JWT " + token);

    }

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
                .post("/person")
                .then()
                .statusCode(200)
                .body("id", is(0))
                .body("firstName", is("test"))
                .body("lastName", is("test"))
                .body("phoneNumber", is("0000"));

    }


    @Test
    public void updatePersonTest() {
        var person = new Person();
        person.setPhoneNumber("0000");

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .body(person)
                .put("/person/{id}/phone", 7)
                .then()
                .statusCode(200)
                .body("phoneNumber", is("0000"));


    }

    @Test
    public void updatePersonNotFoundTest() {
        var person = new Person();
        person.setPhoneNumber("phone");

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .body(person)
                .put("/person/{id}/phone", 8)
                .then()
                .statusCode(404);
    }

    @Test
    public void findPersonTest() {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .pathParam("id", 7)
                .get("/person/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void findAllTest() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .get("/person")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void deletePersonTest() {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .queryParam("id", 7)
                .delete("/person/delete")
                .then()
                .statusCode(200);

    }

    @Test
    public void authPersonTest() {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("id", 7)
                .get("/person/auth")
                .then()
                .statusCode(200)
                .body(is(notNullValue()));

    }
}
