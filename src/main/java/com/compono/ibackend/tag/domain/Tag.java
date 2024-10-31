package com.compono.ibackend.tag.domain;

import com.compono.ibackend.schedule.domain.Schedule;
import jakarta.persistence.Column;
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
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "color")
	private String color;

	@OneToMany(mappedBy = "tag")
	private List<Schedule> schedules;
}
