package com.runtik.devices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Log4j2
public class UpdateValueService {
    private final RoomService roomService;
    private final HashMap<String, AtomicReference<CompletableFuture<Void>>> currentDevicesTasks = new HashMap<>();
    private final ArrayList<String> typeOfDevices = new ArrayList<>(Arrays.asList("temperature", "humidity", "light", "oxygen"));
    @PostConstruct
    public void init() {
        for (String type : typeOfDevices) {
            currentDevicesTasks.put(type, new AtomicReference<>(null));
        }
    }


    public void startChanging(String type, String uuid, Double step, Integer iterations, WebClient client) {
        log.info("new task: starting changing step " + step + " iterations " + iterations);
        CompletableFuture<Void> existingTask = currentDevicesTasks.get(type).getAndSet(null);
        if (existingTask != null) {
            existingTask.complete(null);
        }
        client.post()
              .uri("/changeDeviceMode")
              .body(Mono.fromCallable(() -> new PostDeviceRequest(uuid, type, "http://localhost:9080/"+type, "work")), PostDeviceRequest.class)
              .retrieve()
              .bodyToMono(Void.class)
              .block();
        CompletableFuture<Void> newTask = executeCommandNTimes(step, iterations, type);
        newTask.thenRun(() -> {
            client.post()
                  .uri("/changeDeviceMode")
                  .body(Mono.fromCallable(() -> new PostDeviceRequest(uuid, type, "http://localhost:9080/"+type, "stop")), PostDeviceRequest.class)
                  .retrieve()
                  .bodyToMono(Void.class)
                  .block();
            System.out.println("finished changing step " + step + " iterations " + iterations);
        });
        currentDevicesTasks.get(type).set(newTask);

    }

    @Async
    public CompletableFuture<Void> executeCommandNTimes(Double step, int times, String type) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        new Thread(() -> {
            for (int i = 0; i < times; i++) {
                try {
                    log.info("executing command " + step);
                    roomService.updateValue(step, type);
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    future.complete(null);
                    return;
                }
            }
            future.complete(null);
        }).start();
        return future;
    }
}
