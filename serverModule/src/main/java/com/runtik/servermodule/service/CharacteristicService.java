package com.runtik.servermodule.service;

import com.runtik.servermodule.dto.UpdateRequest;
import com.runtik.servermodule.entity.Characteristics;
import com.runtik.servermodule.entity.Current;
import com.runtik.servermodule.repository.CharRepository;
import com.runtik.servermodule.repository.CurrentRepository;
import java.time.OffsetDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CharacteristicService {
    public static final int ITERATIONS = 6;
    private final CharRepository charRepository;
    private final CurrentRepository currentRepository;
    private final DeviceClientService deviceClientService;

    public Double temp(String characteristic) {
        log.info("get value for characteristic " + characteristic);
        Current topByTypeOrderByCreatedAtDesc = currentRepository.findTopByTypeOrderByCreatedAtDesc(characteristic);
        if (topByTypeOrderByCreatedAtDesc == null) {
            throw new IllegalArgumentException("this device does not exist");
        }
        return topByTypeOrderByCreatedAtDesc
                                .getTemperature();
    }

    public void update(UpdateRequest updateRequest) {
        log.info("update request " + updateRequest);
        charRepository.save(Characteristics.builder()
                                           .type(updateRequest.getType())
                                           .temperature(updateRequest.getValue())
                                           .createdAt(OffsetDateTime.now())
                                           .build());
        Current lastRecord = currentRepository.findTopByTypeOrderByCreatedAtDesc(updateRequest.getType());
        if (lastRecord == null) {
            throw new IllegalArgumentException("this device does not exist");
        }
        Double currentTemp = lastRecord.getTemperature();
        if (!Objects.equals(updateRequest.getValue(), currentTemp)) {
            deviceClientService.update(updateRequest.getType(),(updateRequest.getValue() - currentTemp) / ITERATIONS, ITERATIONS);
        }
    }
}
