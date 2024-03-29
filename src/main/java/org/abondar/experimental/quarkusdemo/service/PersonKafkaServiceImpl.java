package org.abondar.experimental.quarkusdemo.service;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.abondar.experimental.quarkusdemo.model.PersonDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.abondar.experimental.quarkusdemo.util.KafkaUtil.ID_SINK;
import static org.abondar.experimental.quarkusdemo.util.KafkaUtil.PERSON_SINK;
import static org.abondar.experimental.quarkusdemo.util.KafkaUtil.PERSON_TOPIC;

@ApplicationScoped
public class PersonKafkaServiceImpl implements PersonKafkaService {

    private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Inject
    @Channel(PERSON_SINK)
    Emitter<PersonDTO> personEmitter;

    @Override
    public void sendToKafka(PersonDTO personDTO) {
        personEmitter.send(personDTO);
    }

    @Incoming(PERSON_TOPIC)
    @Outgoing(ID_SINK)
    @Broadcast
    @Override
    public long readFromKafka(PersonDTO personDTO) {

        logger.info(personDTO.toString());


        return personDTO.getId();
    }


}
