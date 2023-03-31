//package org.abondar.experimental.quarkusdemo.rest;
//
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.ws.rs.container.ContainerRequestContext;
//import jakarta.ws.rs.container.ContainerRequestFilter;
//import jakarta.ws.rs.container.PreMatching;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.Provider;
//import jakarta.inject.Inject;
//
//import org.abondar.experimental.quarkusdemo.service.TokenService;
//import org.eclipse.microprofile.jwt.Claims;
//
//
//import java.nio.file.attribute.UserPrincipal;
//import java.security.Principal;
//
//
//@Provider
//@ApplicationScoped
//@PreMatching
//public class SecurityInterceptor implements ContainerRequestFilter {
//
//    private final String authHeaderName = "Authorization";
//
//
//    @Inject
//    TokenService tokenService;
//
//    @Override
//    public void filter(ContainerRequestContext containerRequestContext) throws SecurityException {
//        var path = containerRequestContext.getUriInfo().getPath();
//        var token = containerRequestContext.getHeaderString(authHeaderName);
//        var method = containerRequestContext.getMethod();
//        var role = containerRequestContext.getHeaders().getFirst("Role");
//        if (path.startsWith("/person") &&
//                (method.equals("PUT") || method.equals("DELETE")) &&
//                (token == null || role==null)) {
//            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//        } else if (token != null) {
//            try {
//                var claims = tokenService.parseToken(token.substring(4));
//                if (!tokenService.validateToken(claims)) {
//                    containerRequestContext
//                            .abortWith(Response
//                                    .status(Response.Status.NOT_ACCEPTABLE.getStatusCode(),
//                                            "Invalid token").build());
//                } else {
//                    containerRequestContext.setSecurityContext(new SecurityContext() {
//                        @Override
//                        public Principal getUserPrincipal() {
//                            return (UserPrincipal) () -> claims.get(Claims.given_name.name()) + (String) claims.get(Claims.family_name.name());
//                        }
//
//                        @Override
//                        public boolean isUserInRole(String rl) {
//                            return role.equals(rl);
//                        }
//
//                        @Override
//                        public boolean isSecure() {
//                            return false;
//                        }
//
//                        @Override
//                        public String getAuthenticationScheme() {
//                            return "JWT";
//                        }
//                    });
//                }
//            } catch (Exception ex) {
//                throw new SecurityException(ex.getMessage());
//            }
//
//        }
//    }
//
//
//
//}
//
