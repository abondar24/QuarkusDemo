package org.abondar.experimental.quarkusdemo.mapper;

import io.quarkiverse.mybatis.runtime.meta.MapperDataSource;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@MapperDataSource("xmlconfig")
public interface PersonMapper {

    void insertPerson(@Param("person") PersonDTO personDTO);

    void updatePhoneNumber(@Param("person") PersonDTO personDTO);

    PersonDTO getPersonById(@Param("id") long id);

    void deletePerson(@Param("id") long id);
}
