package com.runtik.servermodule.dto;

import java.io.Serializable;
import lombok.Data;


public record DeviceDTO(String id, String type) implements Serializable {
}
