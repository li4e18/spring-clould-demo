package com.shitu.springcloud;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.shitu.springcloud.rules.MyRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li4e
 *
 * 全局负载均衡策略
 */
@Configuration
@RibbonClient(name = "eureka-client", configuration = MyRule.class)
public class RibbonConfiguration {

//    @Bean
//    public IRule defaultLBStrategy() {
//        return new RandomRule();
//    }
}
