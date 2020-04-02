package org.abondar.experimental.quarkusdemo.rest;


import io.jsonwebtoken.Jwts;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;


import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    private final List<String> acceptedPaths = List.of("/demo", "/person/auth");
    private final String authHeaderName = "Authorization";


    @Context
    SecurityContext securityContext;



    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws SecurityException {
        var path = containerRequestContext.getUriInfo().getPath();
        var token = containerRequestContext.getHeaderString(authHeaderName);

        if ((!path.contains(acceptedPaths.get(0)) && !path.equals(acceptedPaths.get(1))) && token == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } else {
            checkToken(token.substring(4));
        }


    }

    private void checkToken(String token) throws SecurityException {
        System.out.println(token);

        //TODO: parse jwt
       // Claims claims = Jwts.parser().setSigningKey()
    }
}
