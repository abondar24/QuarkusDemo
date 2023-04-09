package org.abondar.experimental.quarkusdemo.service;

import jakarta.enterprise.context.ApplicationScoped;

import org.abondar.experimental.quarkusdemo.model.Job;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class JobService {

    private List<Job> jobs = new ArrayList<>();

    public void addJob(Job job){
        jobs.add(job);
    }

    public List<Job> getJobs(){
        return jobs;
    }
}
