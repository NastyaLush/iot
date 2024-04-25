package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.Current;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentRepository extends JpaRepository<Current, Integer> {

    Current findTopByTypeOrderByCreatedAtDesc(String type);
}
