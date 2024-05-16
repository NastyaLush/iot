package com.runtik.devices;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
public class RoomService {
    @Value("${room.url}")
    public String roomUrl = "http://localhost:8080";


    public void updateValue(Double value, String type) {
        log.info("Updating " + type + ": " + value);
        WebClient client = WebClient.create(roomUrl);
        client.post()
                .uri(roomUrl + "/" + type)
                .body(BodyInserters.fromValue(value))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
