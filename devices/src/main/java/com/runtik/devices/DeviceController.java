package com.runtik.devices;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Log4j2
public class DeviceController {
    private final UpdateValueService updateValueService;
    @Value("${my.server.url}")
    private String serverUrl;
    private final HashMap<String, String> currentDevices = new HashMap<>();
    private final ArrayList<String> typeOfDevices = new ArrayList<>(Arrays.asList("temperature", "humidity", "light", "oxygen"));


    @PostConstruct
    public void init() {
        WebClient client = WebClient.create(serverUrl);
        for (String type : typeOfDevices) {
            String uuid = UUID.randomUUID().toString();
            currentDevices.put(type, uuid);
            client.post()
                    .uri("/registerNewDevice")
                    .body(Mono.fromCallable(() -> new PostDeviceRequest(uuid, type, "http://localhost:9080/" + type, "stop")), PostDeviceRequest.class)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
    }


    @PostMapping(value = "/temperature")
    public void changeTemp(@RequestBody Change change) {
        log.info("start change temperature step {} iterations{}", change.step(), change.iterations());
        WebClient client = WebClient.create(serverUrl);
        updateValueService.startChanging("temperature", currentDevices.get("temperature"), change.step(), change.iterations(), client);
    }

    @PostMapping(value = "/humidity")
    public void changeHumidity(@RequestBody Change change) {
        log.info("start change humidity step {} iterations{}", change.step(), change.iterations());
        WebClient client = WebClient.create(serverUrl);
        updateValueService.startChanging("humidity", currentDevices.get("humidity"), change.step(), change.iterations(), client);
    }

    @PostMapping(value = "/oxygen")
    public void changeOxygen(@RequestBody Change change) {
        log.info("start change oxygen step {} iterations{}", change.step(), change.iterations());
        WebClient client = WebClient.create(serverUrl);
        updateValueService.startChanging("oxygen", currentDevices.get("oxygen"), change.step(), change.iterations(), client);
    }

    @PostMapping(value = "/light")
    public void changeLight(@RequestBody Change change) {
        log.info("start change light step {} iterations{}", change.step(), change.iterations());
        WebClient client = WebClient.create(serverUrl);
        updateValueService.startChanging("light", currentDevices.get("light"), change.step(), change.iterations(), client);
    }
}
