package com.bank.mscalculate.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.update-points}")
    private String updatesPointsQueue;

    @Bean
    public Queue queueUpdatePoints(){
        return new Queue(updatesPointsQueue, true);
    }
}
