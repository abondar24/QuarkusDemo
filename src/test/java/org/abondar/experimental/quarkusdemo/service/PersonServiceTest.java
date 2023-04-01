package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.abondar.experimental.quarkusdemo.model.Person;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@QuarkusTest
public class PersonServiceTest {

    //todo mock dao in real service
    //todo test kafka service
    //todo test mongo
    @Inject
    @Named("mockService")
    PersonService personService;

    @Test
    void testService() {
        var person = new Person("Alex", "Bondar", "0000000");
        var res = personService.insertPerson(person);
        assertEquals(person.getFirstName(), res.getFirstName());

        var phone = "0000";
        person.setPhoneNumber(phone);
        res = personService.updatePhone(7, person);
        assertEquals(phone, res.getPhoneNumber());

        res = personService.updatePhone(8, person);
        assertNull(res);

        res = personService.findPerson(8);
        assertNull(res);

        var deleted = personService.deletePerson(8);
        assertFalse(deleted);

        deleted = personService.deletePerson(7);
        assertTrue(deleted);
    }
}
