package com.runtik.sensors;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SensorsDataGenerator {
    private final RestTemplate restTemplate = new RestTemplate();
    private final List<Sensor> sensors = new ArrayList<>();

    @PostConstruct
    public void init() {
        for(int i=0;i<100;i++){
            UUID id = UUID.randomUUID();
            Type type = Type.values()[(int) (Math.random() * Type.values().length)];
            Sensor sensor = new Sensor(id, type.getValue());
            sensors.add(sensor);
            // Register sensor
            restTemplate.postForObject("http://localhost:8082/sensor",
                    new Sensor(id, type.getValue()), Void.class);
        }

    }

    @Scheduled(fixedRate = 10000) // Send data every 10 seconds
    public void sendData() {
        for(Sensor sensor : sensors) {
            Double sensorData = restTemplate.getForObject("http://localhost:8080/" + sensor.type(), Double.class);

            // Send data to adapter asynchronously
            restTemplate.postForObject("http://localhost:8081/adapter",
                    new Message(sensor.id().toString(), sensorData), Void.class);

        }

    }
}
