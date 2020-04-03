package org.abondar.experimental.quarkusdemo.model;


import io.quarkus.kafka.client.serialization.JsonbDeserializer;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PersonDeserializer extends JsonbDeserializer<Person> {


    public PersonDeserializer() {
        super(Person.class);
    }

}
