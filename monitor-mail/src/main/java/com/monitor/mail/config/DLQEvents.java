/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monitor.mail.config;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author 1555 MX
 */
@Component
public class DLQEvents {

    private static final Logger logger = Logger.getLogger(DLQEvents.class);

    private static final long MAX_ATTEMPTS = 10;

    @RabbitListener(queues="monitor.async.process.DLQ")
    public void billingNotify(Message message){
        saveDLQ(message, Object.class);

    }

    private <T> void saveDLQ(Message message, Class<T> clazz) {
        try {
            logger.error("Exception Message: " + message.getMessageProperties().getHeaders().get("x-exception-stacktrace"));
        } catch (Exception e) {
            if (message.getMessageProperties().getDeliveryTag() >= MAX_ATTEMPTS) {
                logger.info("DLQ name: " + message.getMessageProperties().getHeaders().get("x-original-routingKey"));
                logger.info("Original Queue: " + message.getMessageProperties().getReceivedRoutingKey());
                logger.info("Original Exchange: " + message.getMessageProperties().getHeaders().get("x-original-exchange"));
                logger.info("Exception Message: " + message.getMessageProperties().getHeaders().get("x-exception-stacktrace"));
                logger.info("Content-Type: " + message.getMessageProperties().getContentType());
                logger.info("Body " + new String(message.getBody()));
                logger.error("Nao foi possivel processar a mensagem na DLQ");
            } else {
                logger.warn("Falha ao processar a mensagem na DLQ: " + e.getMessage()
                        + ". Tenantiva " + message.getMessageProperties().getDeliveryTag() + "/" + MAX_ATTEMPTS);
                throw e;
            }
        }
    }
}