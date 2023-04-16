package org.abondar.experimental.quarkusdemo.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.ws.rs.core.MediaType;
import org.abondar.experimental.quarkusdemo.model.PersonResponse;
import org.abondar.experimental.quarkusdemo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class AuthResourceTest {

    @InjectMock
    PersonService personService;

    @Test
    void authPersonTest() {

        var person = new PersonResponse(7,"test","test","test");
        Mockito.when(personService.findPerson(Mockito.anyLong())).thenReturn(Optional.of(person));

        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/auth/7")
                .then()
                .statusCode(200)
                .body(is(notNullValue()));

    }
}
