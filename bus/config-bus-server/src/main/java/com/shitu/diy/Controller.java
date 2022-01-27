package com.shitu.diy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private BusProperties busProperties;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping("/bus/event/publish/custom")
    public boolean publishUserEvent() {
        String instanceId = busProperties.getId();
        CustomEvent customEvent = new CustomEvent("lishiyu", instanceId, "dashan");
        eventPublisher.publishEvent(customEvent);
        return true;
    }

}
