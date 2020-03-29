package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.Mock;
import io.smallrye.mutiny.Multi;
import org.abondar.experimental.quarkusdemo.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Mock
@ApplicationScoped
@Named("mockService")
public class PersonKafkaServiceMockImpl implements PersonKafkaService {


    @Override
    public void sendToKafka(Person person) {

    }

    @Override
    public long readFromKafka(Person person) {
        return person.getId();
    }

    @Override
    public Multi<Person> getKafkaPersons() {
        return null;
    }


}
