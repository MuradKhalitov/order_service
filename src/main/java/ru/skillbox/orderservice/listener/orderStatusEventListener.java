package ru.skillbox.orderservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.skillbox.orderservice.dto.OrderStatusEvent;

import java.util.UUID;

@Component
@Slf4j
public class orderStatusEventListener {

    @KafkaListener(topics = "order-status-topic", groupId = "order-group")
    public void listenOrderStatusTopic(OrderStatusEvent message,
                                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Получено сообщение: {}", message);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
    }
}
