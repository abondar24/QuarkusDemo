package org.abondar.experimental.quarkusdemo.mapper;

import io.quarkus.test.junit.QuarkusTest;
import org.abondar.experimental.quarkusdemo.conf.TestQualifier;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class PersonMapperTest {


    private PersonMapper personMapper;

    private SqlSession sqlSession;

    @Inject
    @TestQualifier
    private SqlSessionFactory factory;

    @BeforeEach
    public void setUp() {
        sqlSession = factory.openSession();
        personMapper = sqlSession.getMapper(PersonMapper.class);
    }

    @Test
    public void testPersonMapper() {
        var person = new Person("Alex", "Bondar", "+79991112233");
        personMapper.insertPerson(person);
        assertTrue(person.getId() > 0);

        var newPhone = "+79991112244";
        person.setPhoneNumber(newPhone);
        personMapper.updatePhoneNumber(person);

        var res = personMapper.getPersonById(person.getId());
        assertEquals(person.getFirstName(), res.getFirstName());
        assertEquals(newPhone, res.getPhoneNumber());

        personMapper.deletePerson(person.getId());
        res = personMapper.getPersonById(person.getId());
        assertNull(res);
    }

    @AfterEach
    public void closeSession() {
        sqlSession.commit();
        sqlSession.close();
    }
}
