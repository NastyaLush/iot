package com.runtik.servermodule;

import com.runtik.servermodule.dto.UpdateRequest;
import com.runtik.servermodule.service.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("characteristic")
public class CharacteristicController {
    private final CharacteristicService characteristicService;
    @GetMapping
    public Double get(@RequestParam String characteristic) {
        return characteristicService.temp(characteristic);
    }
    @PostMapping
    public void post(@RequestBody UpdateRequest updateRequest){
        characteristicService.update(updateRequest);
    }
}
