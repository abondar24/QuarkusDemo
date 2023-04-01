package org.abondar.experimental.quarkusdemo.rest.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abondar.experimental.quarkusdemo.service.TokenService;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuthResource {
    @Inject
    TokenService tokenService;

    @GET
    @Path("/{id}")
    @Operation(summary = "Authenticate a person")
    public Response getJWT(@PathParam("id") long id) throws Exception {

        var token = tokenService.generateToken(id);
        if (token.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(token).build();
    }


}
