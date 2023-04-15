package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;

import org.abondar.experimental.quarkusdemo.mapper.PersonMapper;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.abondar.experimental.quarkusdemo.model.PersonRequest;
import org.abondar.experimental.quarkusdemo.model.PersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@QuarkusTest
public class PersonServiceTest {

    @Inject
    PersonService personService;

    //TODO: fix mocking once new quarkus-mybatis is out

    PersonMapper mapper;

    @InjectMock
    PersonKafkaService kafkaService;

    @InjectMock
    PersonMongoService mongoService;

    private PersonRequest person;

    @BeforeEach
    void setUp(){
        mapper = mock(PersonMapper.class);
        MockitoAnnotations.openMocks(this);

        person = new PersonRequest("Alex", "Bondar", "0000000");
        PersonDTO foundDTO = new PersonDTO(7, "test", "test", "test");

        when(mapper.getPersonById(7)).thenReturn(foundDTO);
        when(mapper.getPersonById(8)).thenReturn(null);
    }

    @Test
    void testInsert() {
        doNothing().when(mongoService).add(person);
        doNothing().when(mapper).insertPerson(any(PersonDTO.class));
        doNothing().when(kafkaService).sendToKafka(any(PersonDTO.class));
        var res = personService.insertPerson(person);
        assertEquals(person.firstName(), res.firstName());

    }

    @Test
    @Disabled
    void testUpdate(){
        var updPerson = new PersonRequest("Alex", "Bondar", "0000");
        doNothing().when(mapper).updatePhoneNumber(any(PersonDTO.class));
        var res = personService.updatePhone(7, updPerson);
        assertEquals(updPerson.phoneNumber(), res.phoneNumber());

        res = personService.updatePhone(8, person);
        assertNull(res);
    }

    @Test
    @Disabled
    void testFind(){
        var found = personService.findPerson(7);
        assertTrue(found.isPresent());
        assertEquals(7,found.get().id());

        found = personService.findPerson(8);
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAll(){
        var person = new PersonResponse(7,"test","test","test");
        when(mongoService.getAll()).thenReturn(List.of(person));
        var found = personService.findAll();

        assertEquals(1,found.size());
    }

    @Test
    @Disabled
    void testDelete(){
        doNothing().when(mapper).deletePerson(anyLong());
        var deleted = personService.deletePerson(8);
        assertFalse(deleted);

        deleted = personService.deletePerson(7);
        assertTrue(deleted);
    }
}
