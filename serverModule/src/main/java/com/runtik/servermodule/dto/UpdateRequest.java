package com.runtik.servermodule.dto;

import lombok.Data;

@Data
public class UpdateRequest {
    private String deviceId;
    private Double value;
}
