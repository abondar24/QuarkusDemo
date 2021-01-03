package org.abondar.experimental.quarkusdemo.rest;


import org.abondar.experimental.quarkusdemo.service.TokenService;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;


@Provider
@ApplicationScoped
@PreMatching
public class SecurityInterceptor implements ContainerRequestFilter {

    private final String authHeaderName = "Authorization";


    @Inject
    TokenService tokenService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws SecurityException {
        var path = containerRequestContext.getUriInfo().getPath();
        var token = containerRequestContext.getHeaderString(authHeaderName);
        var method = containerRequestContext.getMethod();
        var role = containerRequestContext.getHeaders().getFirst("Role");
        if (path.startsWith("/person") &&
                (method.equals("PUT") || method.equals("DELETE")) &&
                (token == null || role==null)) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        } else if (token != null) {
            try {
                var claims = tokenService.parseToken(token.substring(4));
                if (!tokenService.validateToken(claims)) {
                    containerRequestContext
                            .abortWith(Response
                                    .status(Response.Status.NOT_ACCEPTABLE.getStatusCode(),
                                            "Invalid token").build());
                } else {
                    containerRequestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return (UserPrincipal) () -> claims.get(Claims.given_name.name()) + (String) claims.get(Claims.family_name.name());
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

