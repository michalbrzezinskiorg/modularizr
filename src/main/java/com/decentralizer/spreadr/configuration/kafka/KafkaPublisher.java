package com.decentralizer.spreadr.configuration.kafka;

import com.decentralizer.spreadr.configuration.ApplicationEventsPublisher;
import com.decentralizer.spreadr.configuration.ApplicationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.decentralizer.spreadr.SpreadrApplication.APPLICATION_LINEAR_HISTORY_KAFKA_TOPIC;

@Component
@RequiredArgsConstructor
public class KafkaPublisher implements ApplicationEventsPublisher {

    private final KafkaTemplate<String, ApplicationMessage> template;

    @Override
    public void publish(ApplicationMessage applicationMessage) {
        template.send(APPLICATION_LINEAR_HISTORY_KAFKA_TOPIC, applicationMessage);
    }
}
