package com.compono.ibackend.reservation.repository.querydsl;

import com.compono.ibackend.reservation.domain.PreReservation;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PreReservationRepositoryCustomImpl extends QuerydslRepositorySupport
        implements PreReservationRepositoryCustom {

    public PreReservationRepositoryCustomImpl(Class<?> domainClass) {
        super(PreReservation.class);
    }
}
