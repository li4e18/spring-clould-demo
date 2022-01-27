package com.shitu.diy;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author li4e
 */
@Configuration
@RemoteApplicationEventScan(basePackageClasses = CustomEvent.class)
public class BusConfiguration {
}
