package com.compono.ibackend.schedule.domain;

import com.compono.ibackend.common.converter.TimestampConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "`schedule_time`")
public class ScheduleTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_time", nullable = true)
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime startTime;

    @Column(name = "stop_time", nullable = true)
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime stopTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
