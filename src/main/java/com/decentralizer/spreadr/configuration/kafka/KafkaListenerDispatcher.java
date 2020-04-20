package com.decentralizer.spreadr.configuration.kafka;

import com.decentralizer.spreadr.modules.appconfig.AppconfigEventsListener;
import com.decentralizer.spreadr.modules.appconfig.events.NewControllerFound;
import com.decentralizer.spreadr.modules.appconfig.events.UserAccountCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

import static com.decentralizer.spreadr.SpreadrApplication.APPLICATION_LINEAR_HISTORY_KAFKA_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = APPLICATION_LINEAR_HISTORY_KAFKA_TOPIC, containerFactory = "kafkaListenerContainerFactory")
public class KafkaListenerDispatcher {

    private final AppconfigEventsListener appconfigEventsListener;

    @KafkaHandler
    public void listen(UserAccountCreated message) {
        message.setPublished(ZonedDateTime.now());
        appconfigEventsListener.handleMessage(message);
    }

    @KafkaHandler
    public void listen(NewControllerFound message) {
        message.setPublished(ZonedDateTime.now());
        appconfigEventsListener.handleMessage(message);
    }

}
