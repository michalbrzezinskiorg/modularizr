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
    private Controller payload;
    private boolean compensation;
    private ZonedDateTime published;

}
