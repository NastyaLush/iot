package com.runtik.adapter;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("adapter")
public class AdapterController {
    private final MqttPublisher mqttPublisher;
    private final Gson gson;

    @PostMapping
    public void getNewData(@RequestBody Message message) throws MqttException {
        log.info("Sending data to mqtt server {}, {}", message.id(), message.value());
        mqttPublisher.publish( gson.toJson(message));
    }
}
