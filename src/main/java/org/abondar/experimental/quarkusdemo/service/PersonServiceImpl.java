package org.abondar.experimental.quarkusdemo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.mapper.PersonMapper;
import org.abondar.experimental.quarkusdemo.model.Person;

import java.util.List;

@ApplicationScoped
public class PersonServiceImpl implements PersonService {

    @Inject
    PersonMapper personMapper;


//    @Inject
//    private PersonKafkaService kafkaService;

//    @Inject
//    private PersonMongoService mongoService;


    @Override
    public Person insertPerson(Person person) {
        personMapper.insertPerson(person);
    //    kafkaService.sendToKafka(person);
   //     mongoService.add(person);
        return person;
    }

    @Override
    public Person updatePhone(long id, Person person) {
        var pers = personMapper.getPersonById(id);
        if (pers != null) {
            pers.setPhoneNumber(person.getPhoneNumber());
            personMapper.updatePhoneNumber(person);
            return person;
        }

        return null;
    }

    @Override
    public Person findPerson(long id) {
        var res = personMapper.getPersonById(id);
        return res;
    }

    @Override
    public List<Person> findAll() {
     //   return mongoService.getAll();
        return List.of();
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
