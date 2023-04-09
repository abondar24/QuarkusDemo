package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.abondar.experimental.quarkusdemo.model.PersonRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@QuarkusTest
public class PersonMongoITest {

    @Inject
    PersonMongoService service;

    @Test
    void personMongoTest(){
        var person = new PersonRequest("Alex", "Bondar", "0000000");
        service.add(person);

        var res = service.getAll();
        assertEquals(1,res.size());
        assertEquals(person.firstName(),res.get(0).firstName());
    }
}
