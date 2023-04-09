package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PersonServiceKafkaITest {

    @Inject
    PersonKafkaService kafkaService;

    @Test
    void kafkaServiceTest(){
        var person = new PersonDTO("Alex", "Bondar", "0000000");
        kafkaService.sendToKafka(person);
        var res = kafkaService.readFromKafka(person);
        assertEquals(res,person.getId());
    }
}
