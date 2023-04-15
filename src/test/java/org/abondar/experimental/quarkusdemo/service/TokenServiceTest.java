package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;

import org.abondar.experimental.quarkusdemo.model.PersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TokenServiceTest {

    @Inject
    TokenService tokenService;

    @InjectMock
    PersonService personService;

    @BeforeEach
    public void setUp(){
        var person = new PersonResponse(7,"test","test","test");
        var res = Optional.of(person);
        when(personService.findPerson(person.id())).thenReturn(res);
    }

    @Test
    void testTokenGeneration() throws Exception {
        var token = tokenService.generateToken(7);
        System.out.println(token);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testTokenCheck() throws Exception {
        var token = tokenService.generateToken(7);
        var claims = tokenService.parseToken(token);
        var valid = tokenService.validateToken(claims);

        assertTrue(valid);
    }
}
