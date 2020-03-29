package org.abondar.experimental.quarkusdemo.service;

import org.abondar.experimental.quarkusdemo.model.Person;

public interface PersonKafkaService {

    void sendToKafka(Person person);

    long readFromKafka(Person person);

}
