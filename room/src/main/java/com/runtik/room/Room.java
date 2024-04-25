package com.runtik.room;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Room {
    private Double temperature=0d;
    private Double humidity;
    private Double pressure;
    private Double light;
    private Double oxygen;
}
