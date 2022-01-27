package com.shitu.diy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class CustomEventListener {

    @Value("${server.port}")
    private String port;

    @EventListener
    public void onCustomRemoteApplicationEvent(CustomEvent event) {
        System.out.printf("CustomRemoteApplicationEven - post: %s, Source: %s, originService: %s, destinationService: %s \n",
                port, event.getSource(), event.getOriginService(), event.getDestinationService());
    }
}
