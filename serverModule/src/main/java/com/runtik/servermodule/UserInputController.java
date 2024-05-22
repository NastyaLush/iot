package com.runtik.servermodule;

import com.runtik.servermodule.dto.UpdateRequest;
import com.runtik.servermodule.entity.CurrentSensorValue;
import com.runtik.servermodule.service.UserInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("value")
public class UserInputController {
    private final UserInputService userInputService;
    @GetMapping(value = "/bySensorId")
    public Double getCurrentValueBySensorId(@RequestParam String id) {
        return userInputService.getValueBySensorId(id);
    }
    @GetMapping(value = "/byType")
    public List<CurrentSensorValue> getCurrentValuesByType(@RequestParam String type) {
        return userInputService.getValueByType(type);
    }
    @PostMapping
    public void post(@RequestBody UpdateRequest updateRequest){
        userInputService.update(updateRequest);
    }
}
