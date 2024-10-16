package com.compono.ibackend.schedule.domain;


import com.compono.ibackend.constants.GoingStatus;
import com.compono.ibackend.constants.ScheduleStatus;
import com.compono.ibackend.location.domain.Location;
import com.compono.ibackend.scheduleTime.domain.ScheduleTime;
import com.compono.ibackend.tag.domain.Tag;
import jakarta.persistence.Column;
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
	@Column(name = "id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ScheduleStatus status;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "going_status")
	private GoingStatus goingStatus;

	@Column(name = "summary")
	private String summary;
	@Column(name = "description")
	private String description;

	@Column(name = "start_date_time")
	private LocalDateTime startDateTime;
	@Column(name = "end_date_time")
	private LocalDateTime endDateTime;

	@Column(name = "create_date_time", updatable = false)
	private LocalDateTime createDateTime;
	@Column(name = "update_date_time")
	private LocalDateTime updateDateTime;

	@Column(name = "recurrence")
	private String recurrence;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;

	@OneToMany
	private List<ScheduleTime> scheduleTime;

	private boolean displayCalendar;
	private LocalDateTime totalTime;

	private Integer priority;
}