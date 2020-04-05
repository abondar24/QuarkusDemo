package org.abondar.experimental.quarkusdemo.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
public class Job implements Serializable {

    private static long serialVersionUID = -21L;

    private long id;

    public String jobName;

    public Job(){}

    public Job(long id, String jobName) {
        this.id = id;
        this.jobName = jobName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
