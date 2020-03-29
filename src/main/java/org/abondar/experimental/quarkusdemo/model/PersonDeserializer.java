package org.abondar.experimental.quarkusdemo.model;


import io.quarkus.kafka.client.serialization.JsonbDeserializer;


public class PersonDeserializer extends JsonbDeserializer<Person> {


    public PersonDeserializer() {
        super(Person.class);
    }

}
