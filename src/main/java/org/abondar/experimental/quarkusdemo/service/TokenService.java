package org.abondar.experimental.quarkusdemo.service;


import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.PersonResponse;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;


import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class TokenService {

    private final static int EXPIRY_TIME = 3600;

    private final static String PRIMARY_KEY_ID = "/privateKey.pem";

    @Inject
    PersonService personService;

    @Inject
    JWTParser parser;


    public String generateToken(long id) {
        var person = findPerson(id);
        if (person.isEmpty()) {
            return "";
        }

        return getToken(person.get().firstName(), person.get().lastName());
    }

    public JsonWebToken parseToken(String token) throws Exception{
        return parser.parse(token);
    }


    public boolean validateToken(JsonWebToken jwt) {

        var groups = (HashSet<String>)jwt.getClaim(Claims.groups.name());
         return  groups.size()==2 &&
                    jwt.getClaim(Claims.given_name.name()) != null &&
                    jwt.getClaim(Claims.family_name.name()) != null;
    }


    private String getToken(String firstName, String lastName) {
        var claims = Jwt.claims();

        claims.groups(new HashSet<>(List.of("User","Admin")));
        claims.upn("alex@mail.com");
        claims.issuedAt(System.currentTimeMillis());
        claims.expiresAt(new Date().getTime() + EXPIRY_TIME);
        claims.claim(Claims.given_name.name(), firstName);
        claims.claim(Claims.family_name.name(), lastName);

        return claims.jws()
                .keyId(PRIMARY_KEY_ID)
                .sign();
    }

    private Optional<PersonResponse> findPerson(long id) {
        return personService.findPerson(id);
    }


}
