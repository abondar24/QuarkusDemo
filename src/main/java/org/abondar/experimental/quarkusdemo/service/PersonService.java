package org.abondar.experimental.quarkusdemo.service;

import org.abondar.experimental.quarkusdemo.model.Person;



public interface PersonService {

    Person insertPerson(Person person);

    Person updatePhone(long id, String phoneNumber);

    Person findPerson(long id);

    boolean deletePerson(long id);
}
