package com.runtik.airconditioner;

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
    @Value("${room.url.temp}")
    public String roomTempUrl;

    public void updateTemp(Double temp) {
        log.info("Updating temperature: " + temp);
        WebClient client = WebClient.create(roomUrl);
        client.post()
              .uri(roomTempUrl)
              .body(BodyInserters.fromValue(temp))
              .retrieve()
              .bodyToMono(Void.class)
              .block();
    }
}
