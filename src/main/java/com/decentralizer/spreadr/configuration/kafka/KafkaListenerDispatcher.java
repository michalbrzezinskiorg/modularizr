package com.decentralizer.spreadr.configuration.kafka;

import com.decentralizer.spreadr.modules.appconfig.AppconfigEventsListener;
import com.decentralizer.spreadr.modules.appconfig.events.NewControllerFound;
import com.decentralizer.spreadr.modules.appconfig.events.UserAccountCreated;
import com.decentralizer.spreadr.modules.appconfig.events.UserLoggedInEvent;
import com.decentralizer.spreadr.modules.cms.CmsEventHandler;
import com.decentralizer.spreadr.modules.cms.events.ArticleCreatedMessage;
import com.decentralizer.spreadr.modules.cms.events.ArticleUpdatedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.decentralizer.spreadr.SpreadrApplication.APPLICATION_LINEAR_HISTORY_KAFKA_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = APPLICATION_LINEAR_HISTORY_KAFKA_TOPIC, containerFactory = "kafkaListenerContainerFactory")
public class KafkaListenerDispatcher {

    private final AppconfigEventsListener appconfigEventsListener;
    private final CmsEventHandler cmsEventHandler;

    @KafkaHandler
    public void listen(UserLoggedInEvent message) {
        appconfigEventsListener.handleMessage(message);
    }

    @KafkaHandler
    public void listen(UserAccountCreated message) {
        appconfigEventsListener.handleMessage(message);
    }

    @KafkaHandler
    public void listen(NewControllerFound message) {
        appconfigEventsListener.handleMessage(message);
    }

    @KafkaHandler
    public void listen(ArticleUpdatedMessage message) {
        cmsEventHandler.handleMessage(message);
    }

    @KafkaHandler
    public void listen(ArticleCreatedMessage message) {
        cmsEventHandler.handleMessage(message);
    }

}
