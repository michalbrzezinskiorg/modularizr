package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import lombok.Value;

@Value
public class NewControllerFound implements ApplicationMessage {
    private final Controller controller;
    private final Boolean compensation;

    @Override
    public Boolean isCompensation() {
        return this.compensation;
    }
}
