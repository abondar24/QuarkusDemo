//package org.abondar.experimental.quarkusdemo.service;
//
//import org.abondar.experimental.quarkusdemo.conf.DevQualifier;
//import org.abondar.experimental.quarkusdemo.mapper.PersonMapper;
//import org.abondar.experimental.quarkusdemo.model.Person;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import java.util.List;
//
//@ApplicationScoped
//public class PersonServiceImpl implements PersonService {
//
//    private PersonMapper personMapper;
//
//    private SqlSession sqlSession;
//
//    @Inject
//    @DevQualifier
//    private SqlSessionFactory factory;
//
//    @Inject
//    private PersonKafkaService kafkaService;
//
//    @Inject
//    private PersonMongoService mongoService;
//
//    @PostConstruct
//    public void init() {
//        sqlSession = factory.openSession();
//        personMapper = sqlSession.getMapper(PersonMapper.class);
//    }
//
//    @PreDestroy
//    public void close() {
//        sqlSession.close();
//    }
//
//    @Override
//    public Person insertPerson(Person person) {
//        personMapper.insertPerson(person);
//
//
//        kafkaService.sendToKafka(person);
//        mongoService.add(person);
//        sqlSession.commit();
//        return person;
//    }
//
//    @Override
//    public Person updatePhone(long id, Person person) {
//        var pers = personMapper.getPersonById(id);
//        if (pers != null) {
//            pers.setPhoneNumber(person.getPhoneNumber());
//            personMapper.updatePhoneNumber(person);
//            sqlSession.commit();
//            return person;
//        }
//
//        return null;
//    }
//
//    @Override
//    public Person findPerson(long id) {
//        var res = personMapper.getPersonById(id);
//        sqlSession.commit();
//        return res;
//    }
//
//    @Override
//    public List<Person> findAll() {
//        return mongoService.getAll();
//    }
//
//    @Override
//    public boolean deletePerson(long id) {
//        if (personMapper.getPersonById(id) != null) {
//            personMapper.deletePerson(id);
//            sqlSession.commit();
//            return true;
//        }
//        return false;
//    }
//}
