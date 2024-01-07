package com.compono.ibackend.reservation.repository;

import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.reservation.repository.querydsl.PreReservationRepositoryCustom;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreReservationRepository
        extends JpaRepository<PreReservation, Long>, PreReservationRepositoryCustom {

    Page<PreReservation> findAll(Pageable pageable);

    Optional<PreReservation> findByEmail(String email);
}
