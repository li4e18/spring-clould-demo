package com.shitu;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author li4e
 */
@Slf4j
@Service
public class DubboService implements IDubboService {

    @Override
    public Product publish(Product prod) {
        log.info("Publishing prod {}", prod.getName());
        return prod;
    }
}
