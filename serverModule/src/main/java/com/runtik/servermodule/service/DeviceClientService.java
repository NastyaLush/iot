package com.runtik.servermodule.service;

import com.runtik.servermodule.dto.Change;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeviceClientService {
    private final DeviceService deviceService;

    public void update(String id, Double step, Integer iterations) {
        String deviceUrlByType = deviceService.getDeviceUrlBuId(id);
        log.info("update temp step {} iterations{}", step, iterations);
        WebClient client = WebClient.create(deviceUrlByType);
        client.post()
              .body(BodyInserters.fromValue(new Change(step, iterations)))
              .retrieve()
              .bodyToMono(Void.class)
              .block();
    }
}
