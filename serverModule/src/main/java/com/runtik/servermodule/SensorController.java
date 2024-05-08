package com.runtik.servermodule;

import com.runtik.servermodule.entity.Sensor;
import com.runtik.servermodule.service.SensorService;
import com.runtik.servermodule.service.UserInputService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensor")
@Log4j2
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @GetMapping
    public List<Sensor> getAvailableSensors() {
        log.info("Get available sensors");
        return sensorService.getAvailableSensors();
    }

    @PostMapping
    public void registerNewSensor(Sensor sensor) {
        sensorService.registerNewSensor(sensor);
    }

    @DeleteMapping
    public void deleteSensor(@RequestBody String id) {
        log.info("Delete sensor {}", id);
        sensorService.deleteSensor(id);
    }
}
