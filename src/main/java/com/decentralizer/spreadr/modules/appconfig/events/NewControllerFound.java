package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewControllerFound implements ApplicationMessage {
    private Controller controller;
    private boolean compensation;
    private ZonedDateTime published;

    public NewControllerFound(Controller controller) {
        this.controller = controller;
    }

    public NewControllerFound(Controller controller, boolean compensation) {
        this.controller = controller;
        this.compensation = compensation;
    }

    @Override
    public ZonedDateTime publishedDate() {
        return published;
    }

    @Override
    public boolean isCompensation() {
        return this.compensation;
    }
}
