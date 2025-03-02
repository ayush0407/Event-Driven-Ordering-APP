package com.event.order.config;

import com.event.order.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

/**
 * •	Kafka StringSerializer only works for simple String messages.
 * •	Our Order is a complex object, so it needs a JSON serializer.
 * •	The custom serializer ensures Kafka understands how to serialize and deserialize Order objects.
 */

public class OrderSerializer implements Serializer<Order> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Order data) {
        try {
            return data == null ? null : objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing Order object", e);
        }
    }
}
