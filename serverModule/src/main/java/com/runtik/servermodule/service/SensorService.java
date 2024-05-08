package com.runtik.servermodule.service;

import com.runtik.servermodule.entity.Sensor;
import com.runtik.servermodule.repository.SensorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;

    public List<Sensor> getAvailableSensors() {
        return sensorRepository.findAll();
    }

    public void registerNewSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public void deleteSensor(String id) {
        sensorRepository.deleteById(id);
    }
}
