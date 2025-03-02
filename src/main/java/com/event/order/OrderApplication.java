package com.event.order;

import com.event.order.controller.OrderController;
import com.event.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableKafka
public class OrderApplication implements CommandLineRunner {

    @Autowired
    private OrderController orderController;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        System.out.println("🚀 Order Application is running...");
    }

    @Override
    public void run(String... args) throws Exception {
        IntStream.range(0, 100) // Generate 100 random orders
                .forEach(i -> {
                    Order order = generateRandomOrder(i);
                    orderController.placeOrder(order);
                    System.out.println("Sent Order: " + order);
                    try {
                        Thread.sleep(100); // Simulate delay for async processing
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
    }

    private Order generateRandomOrder(int id) {
        Order order = new Order();
        order.setId((long) id);
        order.setQuantity(ThreadLocalRandom.current().nextInt(1, 10));
        order.setProductName(getRandomProduct());
        return order;
    }

    private String getRandomProduct() {
        String[] products = {"Laptop", "Phone", "Toothpaste", "Shoes", "Backpack", "Headphones"};
        return products[ThreadLocalRandom.current().nextInt(products.length)];
    }
}