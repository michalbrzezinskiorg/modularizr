package com.decentralizer.spreadr.modules.appconfig.events;

import com.decentralizer.spreadr.configuration.ApplicationMessage;
import com.decentralizer.spreadr.modules.appconfig.domain.Controller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewControllerFound implements ApplicationMessage {
    private Controller controller;
    private boolean compensation;

    @Override
    public boolean isCompensation() {
        return this.compensation;
    }
}
