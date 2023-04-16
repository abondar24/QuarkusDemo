package org.abondar.experimental.quarkusdemo.rest;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import jakarta.inject.Inject;

import org.abondar.experimental.quarkusdemo.service.TokenService;
import org.eclipse.microprofile.jwt.Claims;


import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;


//todo fix jwt keys
@Provider
@ApplicationScoped
@PreMatching
public class SecurityInterceptor implements ContainerRequestFilter {


    @Inject
    TokenService tokenService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws SecurityException {
        String authHeaderName = "Authorization";
        String roleHeaderName = "Role";
        String authHeader = "Bearer ";

        var path = containerRequestContext.getUriInfo().getPath();
        var token = containerRequestContext.getHeaderString(authHeaderName);
        var method = containerRequestContext.getMethod();
        var role = containerRequestContext.getHeaders().getFirst(roleHeaderName);

        if (path.startsWith("/person") &&
                (!method.equals("GET")) && (token == null || role==null)) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } else if (token != null) {
            try {
                var jwt = tokenService.parseToken(token.substring(authHeader.length()));
                if (!tokenService.validateToken(jwt)) {
                    containerRequestContext
                            .abortWith(Response
                                    .status(Response.Status.NOT_ACCEPTABLE.getStatusCode(),
                                            "Invalid token").build());
                } else {

                    containerRequestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return (UserPrincipal) () -> jwt.getClaim(Claims.given_name.name()) +  (String) jwt.getClaim(Claims.family_name.name());
                        }

                        @Override
                        public boolean isUserInRole(String rl) {
                            return role.equals(rl);
                        }

                        @Override
                        public boolean isSecure() {
                            return false;
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return "JWT";
                        }
                    });
                }
            } catch (Exception ex) {
                throw new SecurityException(ex.getMessage());
            }

        }
    }



}

