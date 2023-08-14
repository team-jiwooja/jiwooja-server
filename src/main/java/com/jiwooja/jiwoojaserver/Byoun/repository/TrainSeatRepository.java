package com.jiwooja.jiwoojaserver.Byoun.repository;

import com.jiwooja.jiwoojaserver.Byoun.entity.TrainSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainSeatRepository extends JpaRepository<TrainSeat, Long> {
    Optional<TrainSeat> findBySeatCode(String seatCode);
}
