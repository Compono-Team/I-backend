package com.compono.ibackend.schedule.domain;

import static lombok.AccessLevel.PROTECTED;

import com.compono.ibackend.common.converter.TimestampConverter;
import com.compono.ibackend.schedule.enumType.TaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "task_name", nullable = false, length = 100)
    private String taskName;

    @Column(name = "is_routine", nullable = false)
    private Boolean isRoutine;

    @Column(name = "start_date", nullable = false)
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime endDate;

    @Column(name = "memo", nullable = false, columnDefinition = "TEXT", length = 1000)
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    private Point point;

    @Column(name = "schedule_order", nullable = false)
    private int order;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(
            mappedBy = "schedule",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ScheduleTime> scheduleTimes = new ArrayList<>();

    @Column(name = "is_marked", nullable = false)
    private Boolean isMarked;

    public static Schedule of(
            String taskName,
            Boolean isRoutine,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String memo,
            TaskStatus taskStatus,
            int order,
            Boolean isMarked,
            Long userId) {
        Schedule schedule = new Schedule();
        schedule.taskName = taskName;
        schedule.isRoutine = isRoutine;
        schedule.startDate = startDate;
        schedule.endDate = endDate;
        schedule.memo = memo;
        schedule.taskStatus = taskStatus;
        schedule.order = order;
        schedule.isMarked = isMarked;
        schedule.userId = userId;
        return schedule;
    }
}
