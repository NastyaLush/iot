package com.runtik.servermodule;

import com.runtik.servermodule.dto.DeviceDTO;
import com.runtik.servermodule.entity.Device;
import com.runtik.servermodule.service.DeviceService;
import com.runtik.servermodule.service.UserInputService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@Log4j2
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    public List<DeviceDTO> getAvailableCharacteristics(){
        log.info("Get available characteristics");
        return deviceService.getAviableDevices();
    }
    @PostMapping
    public void registerNewDevice(@RequestBody Device device){
        log.info("Registering new device {} ", device);
        deviceService.save(device);
    }
}
