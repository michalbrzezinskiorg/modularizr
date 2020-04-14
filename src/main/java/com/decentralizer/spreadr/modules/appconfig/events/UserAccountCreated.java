package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class UserAccountCreated implements ApplicationMessage {
    private boolean componsation;
    private User user;
    private ZonedDateTime published;

    public UserAccountCreated(User user) {
        this.user = user;
    }

    @Override
    public ZonedDateTime publishedDate() {
        return published;
    }

    @Override
    public boolean isCompensation() {
        return componsation;
    }
}
