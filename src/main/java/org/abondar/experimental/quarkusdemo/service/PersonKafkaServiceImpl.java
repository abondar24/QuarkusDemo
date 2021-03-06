package org.abondar.experimental.quarkusdemo.service;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.abondar.experimental.quarkusdemo.model.Person;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static org.abondar.experimental.quarkusdemo.util.KafkaUtil.ID_SINK;
import static org.abondar.experimental.quarkusdemo.util.KafkaUtil.PERSON_SINK;
import static org.abondar.experimental.quarkusdemo.util.KafkaUtil.PERSON_TOPIC;

@ApplicationScoped
public class PersonKafkaServiceImpl implements PersonKafkaService {

    private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Inject
    @Channel(PERSON_SINK)
    private Emitter<Person> priceEmitter;

    @Override
    public void sendToKafka(Person person) {
        priceEmitter.send(person);
    }

    @Incoming(PERSON_TOPIC)
    @Outgoing(ID_SINK)
    @Broadcast
    @Override
    public long readFromKafka(Person person) {

        logger.info(person.toString());


        return person.getId();
    }


}
