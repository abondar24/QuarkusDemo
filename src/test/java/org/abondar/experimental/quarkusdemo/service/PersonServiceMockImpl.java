//package org.abondar.experimental.quarkusdemo.service;
//
//import io.quarkus.test.Mock;
//import org.abondar.experimental.quarkusdemo.model.Person;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Named;
//import java.util.List;
//
//@Mock
//@ApplicationScoped
//@Named("mockService")
//public class PersonServiceMockImpl implements PersonService {
//    @Override
//    public Person insertPerson(Person person) {
//        return person;
//    }
//
//    @Override
//    public Person updatePhone(long id, Person person) {
//        var pers = new Person();
//        if (findPerson(id) != null) {
//            pers.setPhoneNumber(person.getPhoneNumber());
//            return pers;
//        }
//
//        return null;
//    }
//
//    @Override
//    public Person findPerson(long id) {
//        if (id == 7) {
//            var per = new Person();
//            per.setFirstName("test");
//            per.setLastName("test");
//            return per;
//        }
//        return null;
//    }
//
//    @Override
//    public List<Person> findAll() {
//        return List.of(new Person(), new Person());
//    }
//
//    @Override
//    public boolean deletePerson(long id) {
//        return id == 7;
//    }
//}
