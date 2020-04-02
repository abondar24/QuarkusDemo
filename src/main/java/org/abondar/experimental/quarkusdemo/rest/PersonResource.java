package org.abondar.experimental.quarkusdemo.rest;

import org.abondar.experimental.quarkusdemo.model.Person;
import org.abondar.experimental.quarkusdemo.service.PersonService;
import org.abondar.experimental.quarkusdemo.service.TokenService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.abondar.experimental.quarkusdemo.util.KafkaUtil.ID_TOPIC;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PersonResource {

    @Inject
    public TokenService tokenService;
    @Inject
    private PersonService personService;
    @Inject
    @Channel(ID_TOPIC)
    private Publisher<Long> idPublisher;

    @GET
    @Path("/auth")
    @PermitAll
    @Operation(summary = "Authenticate a person")
    public Response getJWT(@QueryParam("id") long id) throws Exception {

        var token = tokenService.generateToken(id);
        if (token.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(token).build();
    }


    @POST
    @Path("/insert")
    @Operation(summary = "Creates a new person")
    @APIResponse(description = "person object", responseCode = "200")
    public Response insertPerson(Person person) {
        var res = personService.insertPerson(person);
        return Response.ok(res).build();
    }

    @POST
    @Path("/update/{id}")
    @Operation(summary = "Updates person's phone number")
    @APIResponses({
            @APIResponse(description = "person object", responseCode = "200"),
            @APIResponse(description = "person not found", responseCode = "404")
    })
    public Response updatePhone(@PathParam("id") long id, @QueryParam("phone") String phone) {
        var res = personService.updatePhone(id, phone);

        return res == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(res).build();
    }

    @GET
    @Path("/find")
    @Operation(summary = "Find person by id")
    @APIResponses({
            @APIResponse(description = "person object", responseCode = "200"),
            @APIResponse(description = "person not found", responseCode = "404")
    })
    public Response findPerson(@QueryParam("id") long id) {
        var res = personService.findPerson(id);

        return res == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(res).build();
    }

    @GET
    @Path("/all")
    @Operation(summary = "Find all persons")
    @APIResponses({
            @APIResponse(description = "person object", responseCode = "200"),
            @APIResponse(description = "person not found", responseCode = "404")
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
    public Publisher<Long> read() {
        return idPublisher;
    }


    @DELETE
    @Path("/delete")
    @Operation(summary = "Delete person")
    @APIResponse(description = "deletion result.", responseCode = "200")
    public Response deletePerson(@QueryParam("id") long id) {
        var res = personService.deletePerson(id);

        return Response.ok(res).build();
    }

}
