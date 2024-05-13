package com.runtik.servermodule.service;

import com.runtik.servermodule.dto.DeviceDTO;
import com.runtik.servermodule.entity.Device;
import com.runtik.servermodule.repository.DeviceRepository;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public List<DeviceDTO> getAviableDevices() {
        return deviceRepository.findByDeviceUrlIsNotNull()
                               .stream()
                               .map(device -> new DeviceDTO(device.getId(), device.getType()))
                               .toList();
    }

    public String getDeviceUrlBuId(String id) {
        return deviceRepository.findById(id).get().getDeviceUrl();
    }

    public void save(Device device) {
        deviceRepository.save(device);
    }

    public void update(Device device) {
        Optional<Device> currentDevice = deviceRepository.findById(device.getId());
        if (currentDevice.isEmpty()) {
            throw new IllegalArgumentException("No device found with id " + device.getId());
        }
        currentDevice.get().setMode(device.getMode());
        deviceRepository.save(currentDevice.get());
    }
}
