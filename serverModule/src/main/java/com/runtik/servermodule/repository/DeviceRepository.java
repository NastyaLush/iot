package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
    List<Device> findByDeviceUrlIsNotNull();
    @Override
    Optional<Device> findById(String id);
    Optional<Device> findByType(String type);
}
