package com.compono.ibackend.reservation.repository;

import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.reservation.repository.querydsl.PreReservationRepositoryCustom;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreReservationRepository
        extends JpaRepository<PreReservation, Long>, PreReservationRepositoryCustom {

    Page<PreReservation> findByCreatedAtBetween(
            LocalDate startDate, LocalDate endDate, Pageable pageable);
}
