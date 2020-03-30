package org.abondar.experimental.quarkusdemo.service;

import org.abondar.experimental.quarkusdemo.model.Person;

import java.util.List;


public interface PersonService {

    Person insertPerson(Person person);

    Person updatePhone(long id, String phoneNumber);

    Person findPerson(long id);

    List<Person> findAll();

    boolean deletePerson(long id);
}
