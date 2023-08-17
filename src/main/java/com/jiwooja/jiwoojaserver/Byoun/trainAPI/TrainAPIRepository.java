package com.jiwooja.jiwoojaserver.Byoun.trainAPI;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TrainAPIRepository extends JpaRepository<TrainAPI, Long> {
    Optional<TrainAPI> findByTrainNum(String trainNum);
}