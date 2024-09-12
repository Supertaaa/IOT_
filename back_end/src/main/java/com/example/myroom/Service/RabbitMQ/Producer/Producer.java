package com.example.myroom.Service.RabbitMQ.Producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class Producer {
    private static Logger logger = LoggerFactory.getLogger(Producer.class);
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier(value = "myRequestQueue")
    private Queue reqQueue;


    public void publishFeedback(LinkedHashMap<String, Object> s) {

        try {
            template.convertAndSend(reqQueue.getName(), s);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
