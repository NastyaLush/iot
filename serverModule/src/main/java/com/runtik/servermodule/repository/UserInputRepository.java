package com.runtik.servermodule.repository;

import com.runtik.servermodule.entity.UserInput;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInputRepository extends JpaRepository<UserInput, Integer> {
    public Optional<UserInput> findFirstByDeviceIdOrderByCreatedAtDesc(String id);
}
