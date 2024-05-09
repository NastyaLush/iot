package com.runtik.sensors;

public enum Type {
    TEMPERATURE("temperature");

    private final String value;
    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
