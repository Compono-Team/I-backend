package com.compono.ibackend.reservation.repository;

import com.compono.ibackend.reservation.domain.BetaReservation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetaReservationRepository extends JpaRepository<BetaReservation, Long> {

    Page<BetaReservation> findAll(Pageable pageable);

    Optional<BetaReservation> findByEmail(String email);
}
