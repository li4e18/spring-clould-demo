package com.shitu.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author li4e
 */
@RestController
@Slf4j
public class Controller {

    @Autowired
    private LoadBalancerClient client;

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping("/hello")
    public String hello(@RequestBody Friend friend) {
        ServiceInstance instance = client.choose("eureka-client");

        if (instance == null) {
            return "No available instance";
        }

        String target = String.format("http://%s:%s/sayHi", instance.getHost(), instance.getPort());
        log.info("url is {}", target);

        Friend fri = restTemplate.postForObject(target, friend, Friend.class);
        assert fri != null;
        return fri.toString();
    }

}
