package com.shitu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author li4e
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

    /*
    * localhost:60000/{label}/{application}-{profile}.json (.yml .properties)
    * localhost:60000/{application}/{profile}/{label}
    *
    * Controller类在EnvironmentController中
    * */
}
