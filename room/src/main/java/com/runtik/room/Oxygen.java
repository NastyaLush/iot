package com.runtik.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("oxygen")
@RequiredArgsConstructor
public class Oxygen {
    private final Room room;

    @GetMapping
    public Double getOxygen() {
        log.info("Get oxygen request");
        return room.getOxygen();
    }

    @PostMapping
    public void postOxygen(@RequestBody Double step) {
        log.info("Post oxygen request, added oxygen{}", step.toString());
        room.setOxygen(room.getOxygen() + step);
    }


}
