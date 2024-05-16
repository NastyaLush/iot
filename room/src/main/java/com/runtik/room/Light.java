package com.runtik.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("light")
@RequiredArgsConstructor
public class Light {
    private final Room room;

    @GetMapping
    public Double getLight() {
        log.info("Get light request");
        return room.getLight();
    }

    @PostMapping
    public void postLight(@RequestBody Double step) {
        log.info("Post light request, added light{}", step.toString());
        room.setLight(room.getLight() + step);
    }


}
