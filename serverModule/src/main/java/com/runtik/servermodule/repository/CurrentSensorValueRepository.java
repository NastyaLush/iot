package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.CurrentSensorValue;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentSensorValueRepository extends JpaRepository<CurrentSensorValue, String> {

    Optional<CurrentSensorValue> findBySensorId(String id);
}
