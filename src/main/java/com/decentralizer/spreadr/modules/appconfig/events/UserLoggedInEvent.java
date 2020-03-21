package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.Value;

@Value
public class UserLoggedInEvent implements ApplicationMessage {
    private final User user;
    private final boolean compensation;

    @Override
    public Boolean isCompensation() {
        return compensation;
    }
}
