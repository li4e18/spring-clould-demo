package com.shitu.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author li4e
 */
@FeignClient(value = "eureka-client")
public interface IService {

    @GetMapping("/sayHi")
    String sayHi();

}
