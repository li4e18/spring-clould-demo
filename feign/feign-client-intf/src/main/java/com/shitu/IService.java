package com.shitu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author li4e
 */
@FeignClient(value = "feign-client", primary = false)
public interface IService {

    @GetMapping("/sayHi")
    String sayHi();

    @PostMapping("/sayHi")
    Friend sayHiFriend(@RequestBody Friend friend);

    @GetMapping("/error")
    String error();

    @GetMapping("/retry/{timeout}")
    String retry(@PathVariable(value = "timeout") Integer timeout);

}
