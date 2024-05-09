package com.runtik.sensors;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SensorController {
    private final SensorsDataGenerator sensorsDataGenerator;

    @DeleteMapping
    public void delete(@RequestParam UUID id) {
        sensorsDataGenerator.deleteSensor(id);
    }
}
