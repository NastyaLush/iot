package com.runtik.servermodule.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentSensorValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Double value;
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    private Sensor sensor;
    private OffsetDateTime createdAt;
}
