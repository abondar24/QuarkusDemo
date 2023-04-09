package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class TokenServiceTest {

    @Inject
    TokenService tokenService;

    //TODO: mock person service - make it return some non-empty person object
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
