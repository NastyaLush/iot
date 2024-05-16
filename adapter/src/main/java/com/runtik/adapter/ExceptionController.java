package com.runtik.adapter;

import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Log4j2
public class ExceptionController {
    @ExceptionHandler(value = {MqttException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void resourceNotFoundException(MqttException ex, WebRequest request) {
        log.debug("mqtt exception", ex);
        ex.printStackTrace();
    }
}
