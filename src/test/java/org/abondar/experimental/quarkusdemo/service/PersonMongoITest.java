package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@QuarkusTest
public class PersonMongoITest {

    @Inject
    PersonMongoService service;

    @Test
    void personMongoTest(){
        var person = new Person("Alex", "Bondar", "0000000");
        service.add(person);

        var res = service.getAll();
        assertEquals(1,res.size());
        assertEquals(person.getFirstName(),res.get(0).getFirstName());
    }
}
