package com.decentralizer.spreadr.configuration;

import java.time.ZonedDateTime;

public interface ApplicationMessage {
    ZonedDateTime publishedDate();

    boolean isCompensation();
}
