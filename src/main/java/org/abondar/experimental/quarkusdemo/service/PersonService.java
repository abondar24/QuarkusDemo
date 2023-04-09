package org.abondar.experimental.quarkusdemo.service;

import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.abondar.experimental.quarkusdemo.model.PersonRequest;
import org.abondar.experimental.quarkusdemo.model.PersonResponse;

import java.util.List;
import java.util.Optional;


public interface PersonService {

    PersonResponse insertPerson(PersonRequest request);

    PersonResponse updatePhone(long id, PersonRequest request);

    Optional<PersonResponse> findPerson(long id);

    List<PersonResponse> findAll();

    boolean deletePerson(long id);
}
