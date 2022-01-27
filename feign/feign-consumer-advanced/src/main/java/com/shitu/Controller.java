package com.shitu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li4e
 */
@RestController
@Slf4j
public class Controller {

    @Autowired
    private IService iService;

    @GetMapping("/sayHi")
    public String sayHi() {
        return iService.sayHi();
    }


    @PostMapping("/sayHi")
    public Friend sayHiFriend() {
        Friend friend = new Friend();
        friend.setName("lishiyu");
        return iService.sayHiFriend(friend);
    }

    @GetMapping("/retry")
    public String retry(Integer timeout) {
        return iService.retry(timeout);
    }
}
