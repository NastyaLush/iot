package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.CurrentSensorValue;
import java.util.Optional;

import com.runtik.servermodule.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentSensorValueRepository extends JpaRepository<CurrentSensorValue, Sensor> {

    Optional<CurrentSensorValue> findFirstBySensorOrderByCreatedAtDesc(Sensor sensor);
}
