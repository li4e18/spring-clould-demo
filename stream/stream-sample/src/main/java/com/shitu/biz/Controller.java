package com.shitu.biz;

import com.shitu.topic.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li4e
 */
@Slf4j
@RestController
public class Controller {

    @Autowired
    private MyTopic producer;

    @Autowired
    private GroupTopic groupTopicProducer;

    @Autowired
    private DelayedTopic delayedTopicProducer;

    @Autowired
    private ErrorTopic errorTopicProducer;

    @Autowired
    private RequeueTopic requeueTopicProducer;

    @Autowired
    private DlqTopic dlqTopicProducer;

    @Autowired
    private FallbackTopic fallbackTopicProducer;

    /**
     * 简单广播消息
     */
    @PostMapping("send")
    public void sendMessage(@RequestParam(value = "body") String body) {
        producer.output().send(MessageBuilder.withPayload(body).build());
    }

    /**
     * 消息分组和消息分区
     */
    @PostMapping("sendToGroup")
    public void sendMessageToGroup(@RequestParam(value = "body") String body) {
        groupTopicProducer.output().send(MessageBuilder.withPayload(body).build());
    }

    /**
     * 延迟消息
     */
    @PostMapping("sendToDelayedMessage")
    public void sendToDelayedMessage(@RequestParam(value = "body") String body, @RequestParam(value = "seconds") Integer seconds) {
        MessageBean msg = new MessageBean();
        msg.setPayload(body);

        log.info("ready to send delayed message");
        delayedTopicProducer.output().send(
                MessageBuilder.withPayload(msg).setHeader("x-delay", 1000 * seconds).build()
        );
    }

    /**
     * 异常重试（单机版）
     */
    @PostMapping("sendError")
    public void sendErrorMessage(@RequestParam(value = "body") String body) {
        MessageBean msg = new MessageBean();
        msg.setPayload(body);
        errorTopicProducer.output().send(MessageBuilder.withPayload(msg).build());
    }

    /**
     * 异常重试（联机版）
     */
    @PostMapping("requeue")
    public void sendErrorMessageByRequeue(@RequestParam(value = "body") String body) {
        MessageBean msg = new MessageBean();
        msg.setPayload(body);
        requeueTopicProducer.output().send(MessageBuilder.withPayload(msg).build());
    }

    /**
     * dead letter queue test
     */
    @PostMapping("dlq")
    public void sendMessageToDlq(@RequestParam(value = "body") String body) {
        MessageBean msg = new MessageBean();
        msg.setPayload(body);
        dlqTopicProducer.output().send(MessageBuilder.withPayload(msg).build());
    }

    @PostMapping("fallback")
    public void sendMessageToFallback(
            @RequestParam(value = "body") String body,
            @RequestParam(value = "version", defaultValue = "1.0") String version
    ) {
        MessageBean msg = new MessageBean();
        msg.setPayload(body);

        fallbackTopicProducer.output().send(
                MessageBuilder.withPayload(msg)
                        .setHeader("version", version)
                        .build()
        );
    }
}
