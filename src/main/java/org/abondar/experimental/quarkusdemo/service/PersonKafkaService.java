package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.abondar.experimental.quarkusdemo.model.Person;

@RegisterForReflection
public interface PersonKafkaService {

    void sendToKafka(Person person);

    long readFromKafka(Person person);

}
