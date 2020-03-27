package org.abondar.experimental.quarkusdemo.rest;

import org.abondar.experimental.quarkusdemo.model.Person;
import org.abondar.experimental.quarkusdemo.service.PersonService;

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

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    private PersonService personService;

    @POST
    @Path("/insert")
    public Response insertPerson(Person person) {
        var res = personService.insertPerson(person);
        return Response.ok(res).build();
    }

    @POST
    @Path("/update/{id}")
    public Response updatePhone(@PathParam("id") long id, @QueryParam("phone") String phone) {
        var res = personService.updatePhone(id, phone);

        return res == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(res).build();
    }

    @GET
    @Path("/find")
    public Response findPerson(@QueryParam("id") long id) {
        var res = personService.findPerson(id);

        return res == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(res).build();
    }

    @DELETE
    @Path("/delete")
    public Response deletePerson(@QueryParam("id") long id) {
        var res = personService.deletePerson(id);

        return Response.ok(res).build();
    }

}
