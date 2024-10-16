package com.compono.ibackend.timeline.domain;


import com.compono.ibackend.constants.GoingStatus;
import com.compono.ibackend.location.domain.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Timeline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "start_time")
	private LocalDateTime startTime;
	@Column(name = "end_time")
	private LocalDateTime endTime;

	@Column(name = "summary")
	private String summary;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "going_status")
	private GoingStatus goingStatus;

}
