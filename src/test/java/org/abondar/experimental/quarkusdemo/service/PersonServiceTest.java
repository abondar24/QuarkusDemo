package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.abondar.experimental.quarkusdemo.model.PersonRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@QuarkusTest
public class PersonServiceTest {

    //todo mock dao in real service
    @Inject
    PersonService personService;

    //re-enable once mocks are added
    @Test
    @Disabled
    void testService() {
        var person = new PersonRequest("Alex", "Bondar", "0000000");
        var res = personService.insertPerson(person);
        assertEquals(person.firstName(),res.firstName());

        var updPerson = new PersonRequest("Alex", "Bondar", "0000");

        res = personService.updatePhone(7, updPerson);
        assertEquals(updPerson.phoneNumber(), res.phoneNumber());

        res = personService.updatePhone(8, person);
        assertNull(res);

        var found = personService.findPerson(8);
        assertTrue(found.isEmpty());

        var deleted = personService.deletePerson(8);
        assertFalse(deleted);

        deleted = personService.deletePerson(7);
        assertTrue(deleted);
    }
}
