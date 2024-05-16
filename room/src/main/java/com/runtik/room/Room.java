package com.runtik.room;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Room {
    private Double temperature = 0d;
    private Double humidity = 0d;
    private Double light = 0d;
    private Double oxygen = 0d;
}
