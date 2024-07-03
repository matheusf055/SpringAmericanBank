package com.bank.mscalculate.config.queue;

import com.bank.mscalculate.entity.UpdatePoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePointsPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void updatePoints(UpdatePoints points) throws JsonProcessingException {
        var json = convertoIntoJson(points);
        rabbitTemplate.convertAndSend(queue.getName(), json);
    }

    private String convertoIntoJson(UpdatePoints points) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(points);
    }
}
