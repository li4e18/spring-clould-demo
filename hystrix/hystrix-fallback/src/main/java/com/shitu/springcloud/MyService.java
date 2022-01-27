package com.shitu.springcloud;

import com.shitu.IService;
import com.shitu.springcloud.hystrix.Fallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "feign-client",
        fallback = Fallback.class,
        primary = true
)
public interface MyService extends IService {
}
