package com.runtik.servermodule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Current {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Double temperature;
    @Column(nullable = false)
    private String type;
    private OffsetDateTime createdAt;
}
