package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.Data;

@Data
public class UserAccountCreated implements ApplicationMessage {
    private boolean componsation;
    private User user;

    public UserAccountCreated(User user) {
        this.user = user;
    }

    @Override
    public boolean isCompensation() {
        return componsation;
    }
}
