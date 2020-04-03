package org.abondar.experimental.quarkusdemo.mapper;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
@RegisterForReflection
public interface PersonMapper {

    void insertPerson(@Param("person") Person person);

    void updatePhoneNumber(@Param("person") Person person);

    Person getPersonById(@Param("id") long id);

    void deletePerson(@Param("id") long id);
}
