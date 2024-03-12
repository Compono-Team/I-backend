package com.compono.ibackend.schedule.domain;

import static lombok.AccessLevel.PROTECTED;

import com.compono.ibackend.common.converter.TimestampConverter;
import com.compono.ibackend.schedule.enumType.RoutinePeriod;
import com.compono.ibackend.schedule.enumType.SchedulePriority;
import com.compono.ibackend.schedule.enumType.TaskStatus;
import com.compono.ibackend.tag.domain.TagSchedule;
import com.compono.ibackend.user.domain.User;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "`schedule_info`")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "task_name", nullable = false, length = 100)
    private String taskName;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private SchedulePriority priority;

    @Column(name = "start_date", nullable = false)
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @Convert(converter = TimestampConverter.class)
    private LocalDateTime endDate;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    private Point point;

    @Column(name = "is_routine", nullable = false)
    private Boolean isRoutine;

    @Enumerated(EnumType.STRING)
    @Column(name = "routine_period", nullable = false)
    private RoutinePeriod routinePeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus;

    @Column(name = "is_marked", nullable = false)
    private Boolean isMarked;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @OneToMany(
            mappedBy = "schedule",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<TagSchedule> tagSchedules = new ArrayList<>();

    public Schedule(
            User user,
            String taskName,
            SchedulePriority priority,
            LocalDateTime startDate,
            LocalDateTime endDate,
            boolean isRoutine,
            RoutinePeriod routinePeriod,
            boolean isMarked) {
        this.user = user;
        this.taskName = taskName;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRoutine = isRoutine;
        this.routinePeriod = routinePeriod;
        this.isMarked = isMarked;

        this.isDeleted = false;
        this.taskStatus = TaskStatus.IN_PROGRESS;
    }

    public static Schedule of(
            User user,
            String taskName,
            SchedulePriority priority,
            LocalDateTime startDate,
            LocalDateTime endDate,
            boolean isRoutine,
            RoutinePeriod routinePeriod,
            boolean isMarked) {
        return new Schedule(
                user, taskName, priority, startDate, endDate, isRoutine, routinePeriod, isMarked);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
