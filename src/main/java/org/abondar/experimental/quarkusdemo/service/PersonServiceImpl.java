package org.abondar.experimental.quarkusdemo.service;

import org.abondar.experimental.quarkusdemo.conf.DevQualifier;
import org.abondar.experimental.quarkusdemo.mapper.PersonMapper;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PersonServiceImpl implements PersonService {

    private PersonMapper personMapper;

    private SqlSession sqlSession;

    @Inject
    @DevQualifier
    private SqlSessionFactory factory;

    @PostConstruct
    public void init(){
        sqlSession = factory.openSession();
        personMapper = sqlSession.getMapper(PersonMapper.class);
    }

    @PreDestroy
    public void close(){
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Person insertPerson(Person person) {
        personMapper.insertPerson(person);
        return person;
    }

    @Override
    public Person updatePhone(long id, String phoneNumber) {
        var person = personMapper.getPersonById(id);
        if (person!=null){
            person.setPhoneNumber(phoneNumber);
            personMapper.updatePhoneNumber(person);
            return person;
        }

        return null;
    }

    @Override
    public Person findPerson(long id) {
        return personMapper.getPersonById(id);
    }

    @Override
    public boolean deletePerson(long id) {
        if (personMapper.getPersonById(id)!=null){
            personMapper.deletePerson(id);
        }
        return false;
    }
}
