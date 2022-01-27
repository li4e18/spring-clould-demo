package com.shitu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author li4e
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@SpringBootApplication
@ComponentScan(basePackages = {"com.shitu.springcloud.hystrix"})
public class HystrixFallbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixFallbackApplication.class, args);
    }

}
