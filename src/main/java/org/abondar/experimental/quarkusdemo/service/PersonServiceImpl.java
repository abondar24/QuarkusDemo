package org.abondar.experimental.quarkusdemo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.mapper.PersonMapper;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.abondar.experimental.quarkusdemo.model.PersonRequest;
import org.abondar.experimental.quarkusdemo.model.PersonResponse;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonServiceImpl implements PersonService {

    @Inject
    PersonMapper personMapper;


    @Inject
    PersonKafkaService kafkaService;

    @Inject
    PersonMongoService mongoService;


    @Override
    public PersonResponse insertPerson(PersonRequest request) {
        mongoService.add(request);

        var personDTO = new PersonDTO(request.firstName(), request.lastName(),request.phoneNumber());
        personMapper.insertPerson(personDTO);

        kafkaService.sendToKafka(personDTO);

        return new PersonResponse(personDTO.getId(),personDTO.getFirstName(),
                personDTO.getLastName(), personDTO.getPhoneNumber());
    }

    @Override
    public PersonResponse updatePhone(long id, PersonRequest request) {
        var personDTO = personMapper.getPersonById(id);
        if (personDTO != null) {
            personDTO.setPhoneNumber(request.phoneNumber());
            personMapper.updatePhoneNumber(personDTO);
            return new PersonResponse(personDTO.getId(),personDTO.getFirstName(),
                    personDTO.getLastName(), personDTO.getPhoneNumber());
        }

        return null;
    }

    @Override
    public Optional<PersonResponse> findPerson(long id) {
        var personDTO = personMapper.getPersonById(id);
        if (personDTO != null){
            return Optional.of(new PersonResponse(personDTO.getId(),personDTO.getFirstName(),
                    personDTO.getLastName(), personDTO.getPhoneNumber()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonResponse> findAll() {
       return mongoService.getAll();
    }

    @Override
    public boolean deletePerson(long id) {
        if (personMapper.getPersonById(id) != null) {
            personMapper.deletePerson(id);
            return true;
        }
        return false;
    }
}
