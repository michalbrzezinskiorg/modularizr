package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class UserAccountCreated implements ApplicationMessage<User> {
    private boolean compensation;
    private User payload;
    private ZonedDateTime published;

    public UserAccountCreated(User user) {
        this.payload = user;
        user.setActive(true);
        user.setActivated(LocalDateTime.now());
        user.setCreated(LocalDateTime.now());
        published = ZonedDateTime.now();
    }

}
