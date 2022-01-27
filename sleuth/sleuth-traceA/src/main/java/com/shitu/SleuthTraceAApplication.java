package com.shitu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author li4e
 */
@Slf4j
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SleuthTraceAApplication {

    @LoadBalanced
    @Bean
    public RestTemplate lb() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/traceA")
    public String traceA() {
        log.info("...Trace A...");
        return restTemplate.getForObject("http://sleuth-traceB/traceB", String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SleuthTraceAApplication.class, args);
    }

}
