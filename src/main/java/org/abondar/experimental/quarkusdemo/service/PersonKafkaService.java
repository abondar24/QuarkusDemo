package org.abondar.experimental.quarkusdemo.service;

import org.abondar.experimental.quarkusdemo.model.PersonDTO;


public interface PersonKafkaService {

    void sendToKafka(PersonDTO personDTO);

    long readFromKafka(PersonDTO personDTO);

}
