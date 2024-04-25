package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.Characteristics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharRepository extends JpaRepository<Characteristics, Integer> {
}
