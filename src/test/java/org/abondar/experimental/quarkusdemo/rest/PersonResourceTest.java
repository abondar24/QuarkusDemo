package org.abondar.experimental.quarkusdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Header;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.abondar.experimental.quarkusdemo.model.PersonRequest;
import org.abondar.experimental.quarkusdemo.model.PersonResponse;
import org.abondar.experimental.quarkusdemo.service.PersonMongoService;
import org.abondar.experimental.quarkusdemo.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class PersonResourceTest {

    @InjectMock
    PersonService personService;


    private Header authHeader;

    private Header userRole;

    private PersonResponse personResponse;

    @BeforeEach
    void setUp() {
        personResponse = new PersonResponse(7,"test","test","test");
        Mockito.when(personService.findPerson(Mockito.anyLong())).thenReturn(Optional.of(personResponse));

        var token = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)

                .get("/auth/7")
                .asString();

        authHeader = new Header("Authorization", "Bearer " + token);
        userRole = new Header("Role","User");
    }

    @Test
    void insertPersonTest()  {
        var person = new PersonRequest("test","test","test");
        Mockito.when(personService.insertPerson(person)).thenReturn(personResponse);

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .header(userRole)
                .body(person)
                .post("/person")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body("id", is(7))
                .body("firstName", is(personResponse.firstName()))
                .body("lastName", is(personResponse.lastName()))
                .body("phoneNumber", is(personResponse.phoneNumber()));

    }


    @Test
    void updatePersonTest() {
        var id = 7;
        var person = new PersonRequest("test","test","0000");
        personResponse = new PersonResponse(7,"test","test",person.phoneNumber());
        Mockito.when(personService.updatePhone(id,person)).thenReturn(personResponse);

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .header(userRole)
                .body(person)
                .put("/person/{id}/phone", id)
                .then()
                .statusCode(200)
                .body("phoneNumber", is("0000"));

    }

    @Test
    void updatePersonNotFoundTest() {
        var person = new PersonDTO();
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
        Mockito.when(personService.findAll())
                .thenReturn(List.of(new PersonResponse(7,"test","test","test")));

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person")
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void deletePersonTest() throws Exception{
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header(authHeader)
                .header(new Header("Role","Admin"))
                .delete("/person/7")
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
                .delete("/person/7")
                .then()
                .statusCode(403);

    }


}
