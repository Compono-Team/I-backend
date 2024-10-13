package com.compono.ibackend.model.timeline.domain;


import com.compono.ibackend.model.constants.GoingStatus;
import com.compono.ibackend.model.constants.Type;
import com.compono.ibackend.model.location.domain.Location;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private Long id;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private String summary;

	@OneToOne(fetch = FetchType.LAZY)
	private Location location;

	@Enumerated(value = EnumType.STRING)
	private GoingStatus goingStatus;

	@Enumerated(value = EnumType.STRING)
	private Type type;
}
