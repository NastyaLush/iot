package com.runtik.sensors;

public enum Type {
    TEMPERATURE("temperature"),
    HUMIDITY("humidity"),
    LIGHT("light"),
    OXYGEN("oxygen");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
