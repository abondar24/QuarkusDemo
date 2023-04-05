package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PersonServiceKafkaItest {

    @Inject
    PersonKafkaService kafkaService;

    @Test
    void kafkaServiceTest(){
        var person = new Person("Alex", "Bondar", "0000000");
        kafkaService.sendToKafka(person);
        var res = kafkaService.readFromKafka(person);
        assertEquals(0,person.getId());
    }
}
