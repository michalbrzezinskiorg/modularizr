package com.decentralizer.spreadr.modules.appconfig;

import com.decentralizer.spreadr.modules.appconfig.events.NewControllerFound;
import com.decentralizer.spreadr.modules.appconfig.events.UserLoggedInEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public
class AppconfigEventsListener {

    private final AppconfigPostgresPort postgresPort;

    public void handleMessage(UserLoggedInEvent message) {
        postgresPort.save(message.getUser());
    }

    public void handleMessage(NewControllerFound message) {
        postgresPort.addNewControllerToDatabase(message.getController());
    }
}