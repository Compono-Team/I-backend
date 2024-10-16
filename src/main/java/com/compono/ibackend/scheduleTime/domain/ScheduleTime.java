package com.compono.ibackend.scheduleTime.domain;

import com.compono.ibackend.common.converter.TimestampConverter;
import com.compono.ibackend.schedule.domain.Schedule;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ScheduleTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = TimestampConverter.class)
    private LocalDateTime startTime;

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
