package com.shitu.handler;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Mono;

public class BodyHackerHttpResponseDecorator extends ServerHttpResponseDecorator {

    /**
     * 负载具体写入Body内容的代理类
     */
    private BodyHandlerFunction delegate = null;

    public BodyHackerHttpResponseDecorator(BodyHandlerFunction bodyHandlerFunction, ServerHttpResponse delegate) {
        super(delegate);
        this.delegate = bodyHandlerFunction;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        return delegate.apply(getDelegate(), body);
    }
}
