package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.InitUserValues;
import com.runtik.servermodule.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InitUserValuesRepository extends JpaRepository<InitUserValues, Integer> {
    public InitUserValues findByType(String type);
}
