package com.runtik.servermodule.service;

import com.runtik.servermodule.dto.UpdateRequest;
import com.runtik.servermodule.entity.*;
import com.runtik.servermodule.repository.*;

import java.time.OffsetDateTime;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserInputService {
    public static final int ITERATIONS = 6;
    private final UserInputRepository userInputRepository;
    private final CurrentSensorValueRepository currentSensorValueRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceClientService deviceClientService;
    private final SensorRepository sensorRepository;
    private final InitUserValuesRepository initUserValuesRepository;

    public void correctValueIfNecessary(String type) {
        log.info("check value for sensor with type {} " , type);
        double valueChange = getValueChange(type, getLastUserValue(type));
        Optional<Device> deviceByType = deviceRepository.findByType(type);
        if (deviceByType.isEmpty()) {
            //throw new IllegalArgumentException("this device does not exist");
            return;
        }
        changeValue(deviceByType.get(), valueChange);
    }

    public Double getValueBySensorId(String id) {
        log.info("get value for sensor with id {} " , id);
        Optional<CurrentSensorValue> currentById = currentSensorValueRepository.findBySensorId(id);
        if (currentById.isEmpty()) {
            throw new IllegalArgumentException("this sensor does not send any data");
        }
        return currentById.get()
                          .getValue();
    }

    public void update(UpdateRequest updateRequest) {
        log.info("update request " + updateRequest);
        Optional<Device> deviceById = deviceRepository.findById(updateRequest.getDeviceId());
        if (deviceById.isEmpty()) {
            throw new IllegalArgumentException("this device does not exist");
        }
        userInputRepository.save(UserInput.builder()
                                          .value(updateRequest.getValue())
                                          .device(deviceById.get())
                                          .createdAt(OffsetDateTime.now())
                                          .build());
        double valueChange = getValueChange(deviceById.get().getType(), updateRequest.getValue());
        changeValue(deviceById.get(), valueChange);
    }

    private void changeValue(Device device, double valueChange){
        deviceClientService.update(device.getId(), valueChange / ITERATIONS, ITERATIONS);
    }

    private double getLastUserValue(String type) {
        Optional<Device> currentDevice = deviceRepository.findByType(type);
        if (currentDevice.isEmpty()) {
            throw new IllegalArgumentException("no devices found for type " + type + " of values");
        }
        Optional<UserInput> lastUserInput = userInputRepository.findTopByDeviceId(currentDevice.get().getId());
        if (lastUserInput.isEmpty()) {
            InitUserValues initUserValues = initUserValuesRepository.findByType(type);
            return initUserValues.getValue();
        }
        return lastUserInput.get().getValue();
    }

    private double getValueChange(String type, double lastUserValue) {
        InitUserValues initUserValues = initUserValuesRepository.findByType(type);

        double currentValue = getValueFromSensors(type);

        if (currentValue > lastUserValue + initUserValues.getDelta()) {
            return currentValue - (lastUserValue - initUserValues.getDelta() + 0.5);
        }

        if (currentValue < lastUserValue - initUserValues.getDelta()) {
            return lastUserValue + initUserValues.getDelta() - 0.5 - currentValue;
        }
        return 0;
    }

    private double getValueFromSensors(String type){
        List<Sensor> currentSensors = sensorRepository.findByType(type);
        List<String> currentSensorsIds = currentSensors.stream().map(Sensor::getId).toList();
        List<Optional<CurrentSensorValue>> currentValues = new ArrayList<>();
        for (String id : currentSensorsIds) {
            Optional<CurrentSensorValue> currentById = currentSensorValueRepository.findBySensorId(id);
            currentValues.add(currentById);
        }

        OptionalDouble currentAverageValue = currentValues.stream().mapToDouble(e -> {
            if (e.isPresent()) {
                return e.get().getValue();
            }
            return 0;    //todo или не 0 дефолтно возвращать? по факту такое может быть только в самом начале, когда еще рне все отправили первые показания
        }).average();

        if (currentAverageValue.isEmpty()) {
            throw new IllegalArgumentException("No information about this type of sensors");
        }
        return currentAverageValue.getAsDouble();
    }
}
