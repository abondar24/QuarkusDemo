package org.abondar.experimental.quarkusdemo.service;

import io.quarkus.test.Mock;
import org.abondar.experimental.quarkusdemo.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@Mock
@ApplicationScoped
@Named("mockService")
public class PersonServiceMockImpl  implements PersonService{
    @Override
    public Person insertPerson(Person person) {
        return person;
    }

    @Override
    public Person updatePhone(long id, String phoneNumber) {
       var person = new Person();
        if (findPerson(id)!=null){
            person.setPhoneNumber(phoneNumber);
            return person;
        }

        return null;
    }

    @Override
    public Person findPerson(long id) {
        if (id==7){
            return new Person();
        }
        return null;
    }

    @Override
    public List<Person> findAll() {
        return List.of(new Person(),new Person());
    }

    @Override
    public boolean deletePerson(long id) {
        return id == 7;
    }
}
