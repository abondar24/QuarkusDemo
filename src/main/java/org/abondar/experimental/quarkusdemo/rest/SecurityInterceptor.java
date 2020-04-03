package org.abondar.experimental.quarkusdemo.rest;


import org.abondar.experimental.quarkusdemo.service.TokenService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    private final List<String> acceptedPaths = List.of("/demo", "/person/auth");
    private final String authHeaderName = "Authorization";


    @Inject
    private TokenService tokenService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws SecurityException {
        var path = containerRequestContext.getUriInfo().getPath();
        var token = containerRequestContext.getHeaderString(authHeaderName);

        if ((!path.contains(acceptedPaths.get(0)) && !path.equals(acceptedPaths.get(1))) && token == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } else if (token!=null){
            try {
                if (!tokenService.validateToken(token.substring(4))) {
                    containerRequestContext
                            .abortWith(Response
                                    .status(Response.Status.NOT_ACCEPTABLE.getStatusCode(),
                                            "Invalid token").build());
                }
            } catch (Exception ex) {
                    throw new SecurityException(ex.getMessage());
            }

        }
    }


}

