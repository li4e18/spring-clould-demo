package com.shitu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerShituApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerShituApplication.class, args);
    }

}
