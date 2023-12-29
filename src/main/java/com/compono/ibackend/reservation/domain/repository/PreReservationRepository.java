package com.compono.ibackend.reservation.domain.repository;

import com.compono.ibackend.reservation.domain.PreReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreReservationRepository extends JpaRepository<PreReservation, Long> {}
