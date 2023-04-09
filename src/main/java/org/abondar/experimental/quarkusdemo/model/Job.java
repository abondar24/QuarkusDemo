package org.abondar.experimental.quarkusdemo.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serial;
import java.io.Serializable;

@RegisterForReflection
public record Job(
        long id,

        String jobName
) implements Serializable {

    @Serial
    private static final long serialVersionUID = -21L;

}
