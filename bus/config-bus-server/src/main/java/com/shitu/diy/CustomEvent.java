package com.shitu.diy;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author li4e
 */
public class CustomEvent extends RemoteApplicationEvent {

    public CustomEvent() {
    }

    public CustomEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }
}
