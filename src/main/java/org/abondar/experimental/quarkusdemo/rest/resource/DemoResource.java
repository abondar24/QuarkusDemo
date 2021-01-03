package org.abondar.experimental.quarkusdemo.rest.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import org.abondar.experimental.quarkusdemo.service.DemoService;

import org.apache.camel.Produce;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.annotations.SseElementType;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    @Operation(summary = "Stream of values returned every 3 seconds")
    @APIResponse(description = "Generated string", responseCode = "200")
    @PermitAll
    public Multi<String> personStream() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(3))
                .onItem()
                .apply(n -> "Stream response: " + n);
    }


    @GET
    @Path("/vertx")
    @Operation(summary = "Read file")
    @APIResponse(description = "File contents", responseCode = "200")
    @Produce(MediaType.TEXT_PLAIN)
    public Uni<String> asyncAction(){
       return vertx.fileSystem()
               .readFile("/META-INF/text.txt")
               .onItem()
               .transform(b->b.toString("UTF-8"));

    }


}
