package org.abondar.experimental.quarkusdemo.util;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class KafkaUtil {

    public static final String PERSON_SINK = "person-sink";

    public static final String PERSON_TOPIC = "persontopic";

    public static final String ID_SINK = "id-sink";

    public static final String ID_TOPIC = "idtopic";

    private KafkaUtil() {
    }
}
