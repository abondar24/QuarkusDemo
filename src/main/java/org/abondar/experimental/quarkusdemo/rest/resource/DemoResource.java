package org.abondar.experimental.quarkusdemo.rest.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.service.DemoService;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.annotations.SseElementType;


import java.time.Duration;

@Path("/demo")
public class DemoResource {
    @Inject
    ObjectMapper mapper;

    @Inject
    DemoService service;

    @Inject
    Vertx vertx;

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Application status")
    @APIResponse(description = "Application status string", responseCode = "200")
    public String status() throws Exception {
        return mapper.writeValueAsString("quarkus app is up");
    }

    @GET
    @Path("/hello/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Greets by name")
    @APIResponse(description = "Greeting", responseCode = "200")
    public String hello(@PathParam("name") String name) throws Exception {
        return mapper.writeValueAsString(service.generateHello(name));
    }

    @GET
    @Path("/vertx")
    @Operation(summary = "Read file")
    @APIResponse(description = "File contents", responseCode = "200")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> asyncAction(){
       return vertx.fileSystem()
               .readFile("text.txt")
               .onItem()
               .transform(b->b.toString("UTF-8"));

    }


}
