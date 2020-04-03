package org.abondar.experimental.quarkusdemo.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.smallrye.jwt.build.Jwt;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@RequestScoped
public class TokenService {

    private final static int EXPIRY_TIME = 3600;

    private final static String PRIMARY_KEY_ID = "/privateKey.pem";

    @Inject
    private PersonService personService;

    public String generateToken(long id) throws Exception {

        var person = findPerson(id);
        if (person.isEmpty()) {
            return "";
        }

        var key = readPrivateKey();
        return getToken(key, person.get().getFirstName(), person.get().getLastName());
    }

    public  boolean validateToken(String token) throws Exception{

            var key = readPrivateKey();

            try {
                var claims = Jwts.parser()
                        .setSigningKey(key)
                        .parseClaimsJws(token).getBody();

                return claims.get(Claims.given_name.name()) != null && claims.get(Claims.family_name.name()) != null;

            } catch (ExpiredJwtException ex){
                return false;
            }
    }

    private String getToken(PrivateKey key, String firstName, String lastName) throws Exception {

        var claims = Jwt.claims();

        claims.issuedAt(System.currentTimeMillis());
        claims.expiresAt(new Date().getTime() + EXPIRY_TIME);
        claims.claim(Claims.given_name.name(), firstName);
        claims.claim(Claims.family_name.name(), lastName);

        return claims.jws().signatureKeyId(PRIMARY_KEY_ID).sign(key);
    }

    private Optional<Person> findPerson(long id) {
        var person = personService.findPerson(id);
        return Optional.of(person);
    }

    private PrivateKey readPrivateKey() throws Exception{
        try (var is = TokenService.class.getResourceAsStream(PRIMARY_KEY_ID)) {
            var buf = new byte[4096];
            var length = is.read(buf);
            return decodeKey(new String(buf, 0, length, StandardCharsets.UTF_8));
        }
    }


    private PrivateKey decodeKey(String encKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var bytes = toBytes(encKey);
        var keySpec = new PKCS8EncodedKeySpec(bytes);
        var keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(keySpec);
    }


    private byte[] toBytes(String encKey) {
        var normalizedPem = removeEndings(encKey);
        return Base64.getDecoder().decode(normalizedPem);
    }


    private String removeEndings(String key) {
        key = key.replaceAll("-----BEGIN (.*)-----", "");
        key = key.replaceAll("-----END (.*)----", "");
        key = key.replaceAll("\r\n", "");
        key = key.replaceAll("\n", "");
        return key.trim();
    }
}
