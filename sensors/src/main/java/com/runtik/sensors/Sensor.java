package com.runtik.sensors;


import java.io.Serializable;
import java.util.UUID;

public record Sensor(
        UUID id, String type
) implements Serializable {

}
