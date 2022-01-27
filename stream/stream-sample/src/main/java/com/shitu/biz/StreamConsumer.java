package com.shitu.biz;

import com.shitu.topic.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author li4e
 */
@Slf4j
@EnableBinding(value = {Sink.class, MyTopic.class, GroupTopic.class, DelayedTopic.class,
        ErrorTopic.class, RequeueTopic.class, DlqTopic.class, FallbackTopic.class})
public class StreamConsumer {

    private AtomicInteger count = new AtomicInteger(0);

    @StreamListener(Sink.INPUT)
    public void comsume(Object payload) {
        log.info("message consumed successfully, payload={}", payload);
    }

    @StreamListener(MyTopic.INPUT)
    public void comsumeMyMessage(Object payload) {
        log.info("My message consumed successfully, payload={}", payload);
    }

    @StreamListener(GroupTopic.INPUT)
    public void comsumeGroupMessage(Object payload) {
        log.info("Group message consumed successfully, payload={}", payload);
    }

    @StreamListener(DelayedTopic.INPUT)
    public void comsumeDelayedMessage(MessageBean payload) {
        log.info("Delayed message consumed successfully, payload={}", payload.getPayload());
    }

    @StreamListener(ErrorTopic.INPUT)
    public void comsumeErrorMessage(MessageBean payload) {
        log.info("Aren you OK?");

        if (count.incrementAndGet() % 3 == 0) {
            log.info("Fine, thank you. And you?");
            log.info("Delayed message consumed successfully, payload={}", payload.getPayload());
            count.set(0);
        } else {
            log.info("What's your problem?");
            throw new RuntimeException("I'm not OK");
        }
    }

    // 异常重试(联机版-重新入列)
    @StreamListener(RequeueTopic.INPUT)
    public void comsumeRequeueMessage(MessageBean payload) {
        log.info("Aren you OK?");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("I'm not OK");
    }

    // 死信队列
    @StreamListener(DlqTopic.INPUT)
    public void comsumeDlqMessage(MessageBean payload) {
        log.info("DLQ - Are you OK?");

        if (count.incrementAndGet() % 3 == 0) {
            log.info("DLQ - Fine, thank you. And you?");
            log.info("DLQ - message consumed successfully, payload={}", payload.getPayload());
        } else {
            log.info("DLQ - What's your problem?");
            throw new RuntimeException("I'm not OK");
        }
    }

    // Fallback + 升级版本
    @StreamListener(FallbackTopic.INPUT)
    public void goodbye(MessageBean payload, @Header("version") String version) {
        log.info("Fallback - Are you OK?");

        if ("1.0".equalsIgnoreCase(version)) {
            log.info("Fallback - Fine, thank you. And you?");
            log.info("Fallback - message consumed successfully, payload={}", payload.getPayload());
        } else if ("2.0".equalsIgnoreCase(version)) {
            log.info("unsupported version");
            throw new RuntimeException("I'm not OK");
        } else {
            log.info("fallback - version={}", version);
        }
    }

    @ServiceActivator(inputChannel = "fallback-topic.fallback-group.errors")
    public void fallback(Message<?> message) {
        log.info("fallback entered");
    }
}
