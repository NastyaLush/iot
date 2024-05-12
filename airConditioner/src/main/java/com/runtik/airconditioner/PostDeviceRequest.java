package com.runtik.airconditioner;

public record PostDeviceRequest(String id, String type,
                                String deviceUrl) {
}
