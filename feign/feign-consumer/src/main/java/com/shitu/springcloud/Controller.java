package com.shitu.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li4e
 */
@RestController
public class Controller {

    @Autowired
    private IService iService;

    @GetMapping("/sayHi")
    public String sayHi() {
        return iService.sayHi();
    }
}
