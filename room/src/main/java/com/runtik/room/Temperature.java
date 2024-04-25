package com.runtik.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("temperature")
@RequiredArgsConstructor
public class Temperature {
    private final Room room;

    @GetMapping
    public Double getTemperature() {
        log.info("Get temperature request");
        return room.getTemperature();
    }

    @PostMapping
    public void postTemperature(@RequestBody Double temp) {
        log.info("Post temperature request, added temp{}", temp.toString());
        room.setTemperature(room.getTemperature() + temp);
    }


}
