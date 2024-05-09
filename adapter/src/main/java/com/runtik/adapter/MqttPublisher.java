package com.runtik.adapter;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MqttPublisher {
    @Value("${broker.qos}")
    private int qos;
    @Value("${broker.url}")
    private String broker;
    private final String clientId = UUID.randomUUID().toString();
    MqttClient client;
    private final Gson gson = new Gson();
    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(broker, clientId);
            client.connect();
        } catch (MqttException e) {
            log.info("unable to connect to broker");
            throw new RuntimeException(e);
        }
    }
    public void publish( String data) throws MqttException {
        MqttMessage message = new MqttMessage(data.getBytes());
        message.setQos(qos);
        client.publish("topic123", message);
    }
}
