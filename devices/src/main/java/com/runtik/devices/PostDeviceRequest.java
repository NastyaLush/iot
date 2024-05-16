package com.runtik.devices;

public record PostDeviceRequest(String id, String type,
                                String deviceUrl, String mode) {
}
