package com.shitu.springcloud.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.shitu.Friend;
import com.shitu.springcloud.MyService;
import lombok.Cleanup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    private MyService myServicel;

    @Resource
    private RequestCacheService requestCacheService;

    @GetMapping("/fallback")
    public String fallback() {
        return myServicel.error();
    }

    @GetMapping("/cache")
    public Friend cache(String name) {
        @Cleanup HystrixRequestContext context = HystrixRequestContext.initializeContext();

        Friend friend = requestCacheService.requestCache(name);
//        name = name + "1";
        friend = requestCacheService.requestCache(name);
        return friend;
    }

    @GetMapping("/timeout")
    public String timeout(Integer timeout) {
        return myServicel.retry(timeout);
    }


    @HystrixCommand(
            fallbackMethod = "timeoutFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            }
    )
    @GetMapping("/timeout2/{timeout}")
    public String timeout2(@PathVariable(value = "timeout") Integer timeout) {
        return myServicel.retry(timeout);
    }

    public String timeoutFallback(Integer timeout) {
        return "success!!!";
    }

}
