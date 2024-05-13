package com.runtik.servermodule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String deviceUrl;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String mode;
}
