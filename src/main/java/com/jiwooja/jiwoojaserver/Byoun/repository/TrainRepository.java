package com.jiwooja.jiwoojaserver.Byoun.repository;

import com.jiwooja.jiwoojaserver.Byoun.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findByTrainCode(String trainCode);
}
