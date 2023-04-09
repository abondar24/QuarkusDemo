package org.abondar.experimental.quarkusdemo.mapper;

import io.quarkus.test.junit.QuarkusTest;

import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class PersonMapperITest {

    @Inject
    PersonMapper personMapper;

    @Test
    public void testPersonMapper() {
        var person = new PersonDTO("Alex", "Bondar", "+79991112233");
        personMapper.insertPerson(person);
        assertTrue(person.getId() > 0);

        var newPhone = "+79991112244";
        person.setPhoneNumber(newPhone);
        personMapper.updatePhoneNumber(person);

        var res = personMapper.getPersonById(person.getId());
        assertEquals(person.getFirstName(), res.getFirstName());
        assertEquals(newPhone, res.getPhoneNumber());

        personMapper.deletePerson(person.getId());
        res = personMapper.getPersonById(person.getId());
        assertNull(res);
    }


}
