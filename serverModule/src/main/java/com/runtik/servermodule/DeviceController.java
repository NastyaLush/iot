package com.runtik.servermodule;

import com.runtik.servermodule.dto.PostDeviceRequest;
import com.runtik.servermodule.service.DeviceService;
import com.runtik.servermodule.service.CharacteristicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@Log4j2
@RequiredArgsConstructor
public class DeviceController {
    private final CharacteristicService characteristicService;
    private final DeviceService deviceService;

    @GetMapping
    public List<String> getAvailableCharacteristics(){
        log.info("Get available characteristics");
        return deviceService.getAviableDevices();
    }
    @PostMapping
    public void registerNewDevice(@RequestBody PostDeviceRequest postDeviceRequest){
        log.info("Registering new device {} {}", postDeviceRequest.functionalType(), postDeviceRequest.deviceUrl());
        deviceService.save(postDeviceRequest);
    }
}
