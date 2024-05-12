package com.runtik.airconditioner;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
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
public class AirController {
    private final UpdateTemperatureService updateTemperatureService;
    @Value("${my.server.url}")
    private String serverUrl;


    @PostConstruct
    public void init() {
        WebClient client = WebClient.create(serverUrl);
        client.post()
              .body(Mono.fromCallable(() -> new PostDeviceRequest(UUID.randomUUID().toString(), "TEMPERATURE", "http://localhost:9080")), PostDeviceRequest.class)
              .retrieve()
              .bodyToMono(Void.class)
              .block();
    }


    @PostMapping
    public void changeTemp(@RequestBody Change change) {
        log.info("start change temperature step {} iterations{}", change.step(), change.iterations());
        updateTemperatureService.startChanging(change.step(), change.iterations());
    }
}
