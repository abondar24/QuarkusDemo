package org.abondar.experimental.quarkusdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.abondar.experimental.quarkusdemo.service.DemoService;
import org.jboss.resteasy.annotations.SseElementType;


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
    private ObjectMapper mapper;

    @Inject
    private DemoService service;

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public String status() throws Exception{
        return mapper.writeValueAsString("quarkus app is up");
    }

    @GET
    @Path("/hello/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(@PathParam("name") String name) throws Exception{
        return mapper.writeValueAsString(service.generateHello(name));
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<String> personStream() {
        return  Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(3))
                .onItem()
                .apply(n->"Stream response: "+n);
    }



}
