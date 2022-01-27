package com.shitu.filter;

import com.shitu.AuthResponse;
import com.shitu.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class AuthFilter implements GatewayFilter, Ordered {

    private static final String AUTH = "Authorization";

    private static final String USENAME = "SHITU-USERNAME";

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Auth start...");

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String authorization = headers.getFirst(AUTH);
        String username = headers.getFirst(USENAME);

        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isEmpty(authorization)) {
            log.info("token not found");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        AuthResponse authResponse = authService.verify(authorization, username);
        if (authResponse.getCode() != 1L) {
            log.info("invalid token");
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        // TODO 将用户信息存放在请求header中传递给下游业务
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header(USENAME, "li4e");
        ServerHttpRequest build = mutate.build();

        // TODO 如果响应中需要放数据，也可以放在response的header中
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(USENAME, username);
        return chain.filter(exchange.mutate().request(build).response(response).build());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
