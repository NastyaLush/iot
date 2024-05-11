package com.runtik.servermodule.repository;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.runtik.servermodule.dto.Message;
import com.runtik.servermodule.entity.CurrentSensorValue;
import com.runtik.servermodule.entity.Sensor;
import com.runtik.servermodule.service.SensorService;
import com.runtik.servermodule.service.UserInputService;
import jakarta.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class MtqqRepository {
    private final CurrentSensorValueRepository currentSensorValueRepository;
    private final SensorRepository sensorRepository;
    private final UserInputService userInputService;
    @Getter
    @Value("${broker.qos}")
    private int qos;
    @Value("${broker.url}")
    private String broker;
    private final String clientId = UUID.randomUUID()
                                        .toString();
    private final Gson gson = new Gson();

    @PostConstruct
    public void subscribe() throws MqttException {
        MqttClient client = new MqttClient(broker, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect(options);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                log.info("connectionLost: " + throwable.getMessage());
                throwable.printStackTrace();
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) {
                log.info("messageArrived: " + mqttMessage.toString());
                try {
                    Message message = gson.fromJson(mqttMessage.toString(), Message.class);
                    Optional<Sensor> byId = sensorRepository.findById(message.getId());
                    if(byId.isEmpty()){
                        log.info("sensor {} not found", message.getId());
                    }
                    currentSensorValueRepository.save(new CurrentSensorValue().builder()
                                                                              .value(message.getValue())
                                                                              .sensor(byId.get())
                                                                              .createdAt(OffsetDateTime.now())
                                                                              .build());
                    userInputService.correctValueIfNecessary(byId.get().getType());
                } catch (NumberFormatException | JsonSyntaxException e) {
                    log.info("Exception: " + e.getMessage());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                log.info("deliveryComplete: " + iMqttDeliveryToken.isComplete());
            }
        });
        client.subscribe("topic123");
    }

}
