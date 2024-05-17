package ru.skillbox.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.orderservice.dto.Order;
import ru.skillbox.orderservice.dto.OrderEvent;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        try {
            OrderEvent orderEvent = new OrderEvent(order.getProduct(), order.getQuantity());
            log.info("order-service создает заказ: {}", orderEvent);
            kafkaTemplate.send("order-topic", orderEvent);

            return ResponseEntity.status(HttpStatus.CREATED).body("Заказ успешно отправлен в Kafka");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось отправить заказ в Kafka");
        }
    }
}
