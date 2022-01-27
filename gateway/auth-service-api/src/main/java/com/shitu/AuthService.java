package com.shitu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author li4e
 */
@FeignClient("auth-service")
public interface AuthService {

    @PostMapping("/login")
    @ResponseBody
    AuthResponse login(@RequestParam("username") String username, @RequestParam("password") String password);

    @GetMapping("/verify")
    @ResponseBody
    AuthResponse verify(@RequestParam("token") String token, @RequestParam("username") String username);

    @PostMapping("/refresh")
    @ResponseBody
    AuthResponse refresh(@RequestParam("refresh") String refresh);
}
