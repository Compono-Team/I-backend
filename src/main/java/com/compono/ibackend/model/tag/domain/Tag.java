package com.compono.ibackend.model.tag.domain;

import com.compono.ibackend.model.schedule.domain.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String color;

	@OneToMany(mappedBy = "tag")
	private List<Schedule> schedules;
}
