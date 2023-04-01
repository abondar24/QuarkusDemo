package org.abondar.experimental.quarkusdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class PersonResourceTest {


    @Inject
    ObjectMapper mapper;

    private Header authHeader;

    private Header userRole;

    @BeforeEach
    void setUp() {
        var token = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)

                .get("/auth/7")
                .asString();

        authHeader = new Header("Authorization", "JWT " + token);
        userRole = new Header("Role","User");
    }

    @Test
    void insertPersonTest() throws Exception {
        var person = new Person();
        person.setFirstName("test");
        person.setLastName("test");
        person.setPhoneNumber("0000");

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .header(userRole)
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
    void updatePersonTest() {
        var person = new Person();
        person.setPhoneNumber("0000");

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .header(userRole)
                .body(person)
                .put("/person/{id}/phone", 7)
                .then()
                .statusCode(200)
                .body("phoneNumber", is("0000"));


    }

    @Test
    void updatePersonNotFoundTest() {
        var person = new Person();
        person.setPhoneNumber("phone");

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .header(userRole)
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
                .pathParam("id", 7)
                .get("/person/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    void findAllTest() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void deletePersonTest() {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .header(new Header("Role","Admin"))
                .queryParam("id", 7)
                .delete("/person/delete")
                .then()
                .statusCode(200);

    }

    @Test
    void deletePersonUBadRoleTest() {

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .header(userRole)
                .queryParam("id", 7)
                .delete("/person/delete")
                .then()
                .statusCode(403);

    }


}
