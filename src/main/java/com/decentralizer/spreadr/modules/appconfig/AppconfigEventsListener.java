package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import com.decentralizer.spreadr.modules.appconfig.events.NewControllerFound;
import com.decentralizer.spreadr.modules.appconfig.events.UserAccountCreated;
import com.decentralizer.spreadr.modules.appconfig.events.UserLoggedInEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public
class AppconfigEventsListener {

    private final AppconfigPostgresPort postgresPort;

    public void handleMessage(UserLoggedInEvent message) {
        log.info("AppconfigEventsListener handleMessage [{}]", message);
        postgresPort.save(message.getUser());
    }

    public void handleMessage(UserAccountCreated message) {
        log.info("AppconfigEventsListener handleMessage [{}]", message);
        postgresPort.save(message.getUser());
    }

    public void handleMessage(NewControllerFound message) {
        log.info("AppconfigEventsListener handleMessage [{}]", message);
        if (message.isCompensation()) {
            handleCompensation(message.getController());
        } else {
            handleAction(message.getController());
        }
    }

    private void handleCompensation(Controller controller) {
        postgresPort.removeControllerFromDatabase(controller);
    }

    private void handleAction(Controller controller) {
        if (!postgresPort.existsControllerById(controller))
            postgresPort.addNewControllerToDatabase(controller);
        else
            log.error("event duplicate for [{}]", controller);
    }
}