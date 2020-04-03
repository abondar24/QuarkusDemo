package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class TokenServiceTest {

    @Inject
    private TokenService tokenService;

    @Test
    public void testTokenGeneration() throws Exception{
        var token = tokenService.generateToken(7);
        System.out.println(token);

        assertNotNull(token);
    }

    @Test
    public void testTokenCheck() throws Exception{
        var token = tokenService.generateToken(7);

        var valid = tokenService.checkToken(token);

        assertTrue(valid);
    }
}
