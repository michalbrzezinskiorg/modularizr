package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserLoggedInEvent implements ApplicationMessage {
    private User user;
    private boolean compensation;
    private ZonedDateTime published;

    @Override
    public ZonedDateTime publishedDate() {
        return published;
    }

    @Override
    public boolean isCompensation() {
        return compensation;
    }
}
