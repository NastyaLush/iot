package com.runtik.servermodule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne(optional = false)
    private Sensor sensor;
    private OffsetDateTime createdAt;
}
