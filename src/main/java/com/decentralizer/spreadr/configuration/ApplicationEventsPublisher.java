package com.decentralizer.spreadr.configuration;

public interface ApplicationEventsPublisher {
    void publish(ApplicationMessage loginModuleEvent);
}
