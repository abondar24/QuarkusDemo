package org.abondar.experimental.quarkusdemo.rest.route;

import org.abondar.experimental.quarkusdemo.model.Job;
import org.abondar.experimental.quarkusdemo.service.JobService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class JobRoutes extends RouteBuilder {

     @Inject
     private JobService jobService;

    @Override
    public void configure() throws Exception {
          from("platform-http:/job/find?httpMethodRestrict=GET")
                  .setBody()
                  .body(b->{
                      return jobService.getJobs();
                  })
                  .marshal()
                  .json();

          from("platform-http:/job/add?httpMethodRestrict=POST")
                  .unmarshal()
                  .json(JsonLibrary.Jackson,Job.class)
                  .process()
                  .body(Job.class,jobService::addJob)
                  .marshal()
                  .json();
    }
}
