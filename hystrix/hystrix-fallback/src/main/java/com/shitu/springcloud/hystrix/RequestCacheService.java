package com.shitu.springcloud.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.shitu.Friend;
import com.shitu.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RequestCacheService
 * @author li4e
 */
@Slf4j
@Service
public class RequestCacheService {

    @Autowired
    private IService iService;

    @CacheResult
    @HystrixCommand(commandKey = "cacheKey")
    public Friend requestCache(@CacheKey String name) {
        log.info("request cache " + name);
        Friend friend = new Friend();
        friend.setName(name);

        friend = iService.sayHiFriend(friend);
        log.info("after requesting cache " + name);
        return friend;
    }

}
