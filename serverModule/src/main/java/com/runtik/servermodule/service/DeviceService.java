package com.runtik.servermodule.service;

import com.runtik.servermodule.dto.PostDeviceRequest;
import com.runtik.servermodule.entity.Devices;
import com.runtik.servermodule.repository.DeviceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public List<String> getAviableDevices() {
        return deviceRepository.findByDeviceUrlIsNotNull()
                               .stream()
                               .map(Devices::getFunctionalType)
                               .toList();
    }

    public String getDeviceUrlByType(String type) {
        return deviceRepository.findByFunctionalType(type)
                               .getDeviceUrl();
    }

    public void save(PostDeviceRequest postDeviceRequest) {
        deviceRepository.save(Devices.builder()
                                     .deviceUrl(postDeviceRequest.deviceUrl())
                                     .functionalType(postDeviceRequest.functionalType())
                                     .build());
    }
}
