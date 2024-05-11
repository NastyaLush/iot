package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
    public List<Sensor> findByType(String type);
}
