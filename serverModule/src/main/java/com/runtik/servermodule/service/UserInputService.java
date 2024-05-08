package com.runtik.servermodule.service;

import com.runtik.servermodule.dto.UpdateRequest;
import com.runtik.servermodule.entity.UserInput;
import com.runtik.servermodule.entity.CurrentSensorValue;
import com.runtik.servermodule.entity.Device;
import com.runtik.servermodule.repository.UserInputRepository;
import com.runtik.servermodule.repository.CurrentSensorValueRepository;
import com.runtik.servermodule.repository.DeviceRepository;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
        Optional<UserInput> topByDevicesId = userInputRepository.findTopByDeviceId(updateRequest.getDeviceId());
        Double currentTemp = topByDevicesId.isPresent() ? topByDevicesId.get().getValue() : 0;
        if (!Objects.equals(updateRequest.getValue(), currentTemp)) {
            deviceClientService.update(updateRequest.getDeviceId(), (updateRequest.getValue() - currentTemp) / ITERATIONS, ITERATIONS);
        }
    }
}
