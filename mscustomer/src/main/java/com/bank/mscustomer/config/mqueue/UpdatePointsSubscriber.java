package com.bank.mscustomer.config.mqueue;

import com.bank.mscustomer.entity.Customer;
import com.bank.mscustomer.entity.UpdatePoints;
import com.bank.mscustomer.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePointsSubscriber {

    private final CustomerRepository repository;

    @RabbitListener(queues = "${mq.queues.update-points}")
    public void updatePoints(@Payload String payload){
        try {
            ObjectMapper mapper = new ObjectMapper();
            UpdatePoints updatePoints = mapper.readValue(payload, UpdatePoints.class);

            Customer customer = repository.findById(updatePoints.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + updatePoints.getCustomerId()));

            customer.setPoints(customer.getPoints() + updatePoints.getPoints());
            repository.save(customer);

            System.out.println("Points updated successfully for customer with id: " + updatePoints.getCustomerId());
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
