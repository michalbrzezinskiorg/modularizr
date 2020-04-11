package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.Data;

@Data
public class UserLoggedInEvent implements ApplicationMessage {
    private User user;
    private boolean compensation;

    @Override
    public Boolean isCompensation() {
        return compensation;
    }
}
