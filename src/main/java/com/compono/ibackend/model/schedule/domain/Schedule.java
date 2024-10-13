package com.compono.ibackend.model.schedule.domain;


import com.compono.ibackend.model.constants.GoingStatus;
import com.compono.ibackend.model.constants.ScheduleStatus;
import com.compono.ibackend.model.constants.Type;
import com.compono.ibackend.model.location.domain.Location;
import com.compono.ibackend.model.scheduleTime.domain.ScheduleTime;
import com.compono.ibackend.model.tag.domain.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ScheduleStatus status;

	@Enumerated(value = EnumType.STRING)
	private GoingStatus goingStatus;

	private String summary;
	private String description;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	private LocalDateTime createDateTime;
	private LocalDateTime updateDateTime;

	private String recurrence;

	@OneToOne(fetch = FetchType.LAZY)
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	private Tag tag;

	@OneToMany
	private List<ScheduleTime> scheduleTime;

	private boolean displayCalendar;
	private LocalDateTime totalTime;

	@Enumerated(value = EnumType.STRING)
	private Type type;

	private int priority;
}