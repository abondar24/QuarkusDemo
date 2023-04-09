package org.abondar.experimental.quarkusdemo.rest.route;

import jakarta.enterprise.context.ApplicationScoped;
import org.abondar.experimental.quarkusdemo.model.Job;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class JobRestRoute extends RouteBuilder {

    @Override
    public void configure() {
        rest("/job")
                .get()
                .apiDocs(true)
                .outType(Job.class)
                .consumes("application/json")
                .produces("application/json")
                        .to("direct:findJob")
                .post()
                .apiDocs(true)
                .type(Job.class)
                .consumes("application/json")
                .to("direct:addJob");
    }
}
