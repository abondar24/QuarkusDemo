package org.abondar.experimental.quarkusdemo.rest.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.abondar.experimental.quarkusdemo.service.PersonService;
import org.abondar.experimental.quarkusdemo.service.TokenService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;



import static org.abondar.experimental.quarkusdemo.util.KafkaUtil.ID_TOPIC;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonResource {

    @Inject
    PersonService personService;
    @Inject
    @Channel(ID_TOPIC)
    Publisher<Long> idPublisher;


    @POST
    @Operation(summary = "Creates a new person")
    @APIResponses({
            @APIResponse(description = "person object", responseCode = "200"),
            @APIResponse(description = "missing JWT token or role", responseCode = "401"),
            @APIResponse(description = "role is not User or Admin", responseCode = "403"),
            @APIResponse(description = "invalid token", responseCode = "406")
    })
    @RolesAllowed({"User","Admin"})
    public Response insertPerson(Person person) {
        var res = personService.insertPerson(person);
        return Response.ok(res).build();
    }

    @PUT
    @Path("/{id}/phone")
    @Operation(summary = "Updates person's phone number")
    @APIResponses({
            @APIResponse(description = "person object", responseCode = "200"),
            @APIResponse(description = "missing JWT token or role", responseCode = "401"),
            @APIResponse(description = "role is not User or Admin", responseCode = "403"),
            @APIResponse(description = "person not found", responseCode = "404"),
            @APIResponse(description = "invalid token", responseCode = "406")
    })
    @RolesAllowed({"User","Admin"})
    public Response updatePhone(@PathParam("id") long id, Person person) {
        var res = personService.updatePhone(id, person);

        return res == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(res).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Find person by id")
    @APIResponses({
            @APIResponse(description = "person object", responseCode = "200"),
            @APIResponse(description = "person not found", responseCode = "404"),
            @APIResponse(description = "invalid token", responseCode = "406")
    })
    public Response findPerson(@PathParam("id") long id) {
        var res = personService.findPerson(id);

        return res == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(res).build();
    }

    @GET
    @Operation(summary = "Find all persons")
    @APIResponses({
            @APIResponse(description = "person object", responseCode = "200"),
            @APIResponse(description = "person not found", responseCode = "404"),
            @APIResponse(description = "invalid token", responseCode = "406")
    })
    public Response all() {
        var res = personService.findAll();

        return res.isEmpty() ? Response.status(Response.Status.NO_CONTENT).build() : Response.ok(res).build();
    }


    @GET
    @Path("/ids")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get stream of recently added ids")
    @APIResponses({
            @APIResponse(description = "stream of ids", responseCode = "200"),
            @APIResponse(description = "invalid token", responseCode = "406")
    })
    public Publisher<Long> read() {
        return idPublisher;
    }


    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete person")
    @APIResponses({
            @APIResponse(description = "deletion result.", responseCode = "200"),
            @APIResponse(description = "missing JWT token or role", responseCode = "401"),
            @APIResponse(description = "role is not User or Admin", responseCode = "403"),
            @APIResponse(description = "invalid token", responseCode = "406")
    })
    @RolesAllowed({"Admin"})
    public Response deletePerson(@QueryParam("id") long id) {
        var res = personService.deletePerson(id);

        return Response.ok(res).build();
    }

}
