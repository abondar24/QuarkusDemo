package org.abondar.experimental.quarkusdemo.rest.route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.Job;
import org.abondar.experimental.quarkusdemo.service.JobService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;


@ApplicationScoped
public class JobServiceRoute extends RouteBuilder {

    @Inject
    JobService jobService;


    @Override
    public void configure() throws Exception {

        from("direct:findJob")
                .log("Looking for jobs")
                .setBody()
                .body(b-> jobService.getJobs())
                .marshal()
                .json();

        from("direct:addJob")
                .log("Adding a new job")
                .unmarshal()
                .json(JsonLibrary.Jackson,Job.class)
                .process()
                .body(Job.class,jobService::addJob)
                .marshal()
                .json();
    }
}
