package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.Devices;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Devices, Integer> {
    List<Devices> findByDeviceUrlIsNotNull();
    Devices findByFunctionalType(String functionalType);
}
