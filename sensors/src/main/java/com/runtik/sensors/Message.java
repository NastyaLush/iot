package com.runtik.sensors;

import java.io.Serializable;
import lombok.Data;


public record Message(String id, Double value) implements Serializable {
}
