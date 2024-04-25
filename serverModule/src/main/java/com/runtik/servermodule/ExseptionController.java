package com.runtik.servermodule;

import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Log4j2
public class ExseptionController {
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void resourceNotFoundException(IllegalArgumentException ex, WebRequest request) {
        log.debug("illegal argument exception", ex.getMessage());
        ex.printStackTrace();
    }
}
