package com.runtik.servermodule.dto;

import lombok.Data;

@Data
public class UpdateRequest {
    private String type;
    private Double value;
}
