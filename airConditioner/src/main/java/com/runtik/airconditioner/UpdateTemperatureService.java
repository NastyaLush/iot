package com.runtik.airconditioner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Log4j2
public class UpdateTemperatureService {
    private final AtomicReference<CompletableFuture<Void>> executingTask = new AtomicReference<>(null);
    private final RoomService roomService;

    public void startChanging(Double step, Integer iterations, WebClient client, PostDeviceRequest postDeviceRequest) {
        log.info("new task: starting changing step " + step + " iterations " + iterations);
        CompletableFuture<Void> existingTask = executingTask.getAndSet(null);
        if (existingTask != null) {
            existingTask.complete(null);
        }
        CompletableFuture<Void> newTask = executeCommandNTimes(step, iterations);
        newTask.thenRun(() -> {client.post()
                .uri("/changeDeviceMode")
                .body(Mono.fromCallable(() -> postDeviceRequest), PostDeviceRequest.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();});
        executingTask.set(newTask);

    }

    @Async
    public CompletableFuture<Void> executeCommandNTimes(Double step, int times) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        new Thread(() -> {
            for (int i = 0; i < times; i++) {
                try {
                    log.info("executing command " + step);
                    roomService.updateTemp(step);
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    future.cancel(true);
                    return;
                }
            }
            future.complete(null);
        }).start();
        return future;
    }
}
