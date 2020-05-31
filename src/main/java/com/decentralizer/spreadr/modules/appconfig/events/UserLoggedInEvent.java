package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserLoggedInEvent implements ApplicationMessage<User> {
    private User payload;
    private boolean compensation;
    private ZonedDateTime published;

}
