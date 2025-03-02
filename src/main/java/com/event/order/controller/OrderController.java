package com.event.order.controller;

import com.event.order.kafka.OrderProducer;
import com.event.order.model.Order;
import com.event.order.repository.OrderRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;

    public OrderController(OrderProducer orderProducer, OrderRepository orderRepository) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        order.setStatus("PENDING");
//        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        orderProducer.sendOrder(order);
        return "Order placed successfully!";
    }
}
