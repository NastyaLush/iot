package com.runtik.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("humidity")
@RequiredArgsConstructor
public class Humidity {
    private final Room room;

    @GetMapping
    public Double getHumidity() {
        log.info("Get humidity request");
        return room.getHumidity();
    }

    @PostMapping
    public void postHumidity(@RequestBody Double step) {
        log.info("Post humidity request, added humidity{}", step.toString());
        room.setHumidity(room.getHumidity() + step);
    }


}
