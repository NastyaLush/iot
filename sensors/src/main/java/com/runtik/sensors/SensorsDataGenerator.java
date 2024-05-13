package com.runtik.sensors;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SensorsDataGenerator {
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<UUID, Sensor> sensors = new HashMap<>();
    private final Map<UUID, Double> distributionSummary = new HashMap<>();
    private final MeterRegistry meterRegistry;
    @PostConstruct
    public void init() {
        for (int i = 0; i < 3; i++) {
            UUID uuid = UUID.randomUUID();
            Type type = Type.values()[(int) (Math.random() * Type.values().length)];
            Sensor sensor = new Sensor(uuid, type.getValue());
            sensors.put(uuid, sensor);
            distributionSummary.put(sensor.id(), 0d);
            Gauge.builder(sensor.id().toString(), distributionSummary, (distributionSummar)->distributionSummary.get(sensor.id())).register(meterRegistry);
            // Register sensor
            restTemplate.postForObject("http://localhost:8082/sensor",
                    new Sensor(uuid, type.getValue()), Void.class);
        }

    }

    @Scheduled(fixedRate = 20000) // Send data every 20 seconds
    public void sendData() {
        for (Sensor sensor : sensors.values()) {
            Double sensorData = restTemplate.getForObject("http://localhost:8080/" + sensor.type(), Double.class);
            distributionSummary.put(sensor.id(), sensorData);
            // Send data to adapter asynchronously
            restTemplate.postForObject("http://localhost:8081/adapter",
                    new Message(sensor.id()
                                      .toString(), sensorData), Void.class);

        }

    }

    public void deleteSensor(UUID sensorId) {
        sensors.remove(sensorId);
    }
}
