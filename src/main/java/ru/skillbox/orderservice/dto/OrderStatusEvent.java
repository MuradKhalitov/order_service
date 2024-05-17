package ru.skillbox.orderservice.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class OrderStatusEvent {
    private String status;
    private Instant date;
}
