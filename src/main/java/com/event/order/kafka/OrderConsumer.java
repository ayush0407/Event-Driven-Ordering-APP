package com.event.order.kafka;

import com.event.order.model.Order;
import com.event.order.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private final OrderRepository orderRepository;

    public OrderConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "order_topic", groupId = "order_group")
    public void processOrder(Order order){
        order.setStatus("PROCESSED");
        orderRepository.save(order);
        System.out.println("Processed order: " + order);

    }

}
