package com.decentralizer.spreadr.configuration;

import java.time.ZonedDateTime;

public interface ApplicationMessage<T> {
    T getPayload();

    ZonedDateTime getPublished();

    boolean isCompensation();
}
