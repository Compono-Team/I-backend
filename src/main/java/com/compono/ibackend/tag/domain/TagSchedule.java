package com.compono.ibackend.tag.domain;

import static lombok.AccessLevel.PROTECTED;

import com.compono.ibackend.schedule.domain.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "`tag_schedule`")
public class TagSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public TagSchedule(Tag tag, Schedule schedule) {
        this.setTag(tag);
        this.setSchedule(schedule);
    }

    private void setTag(Tag tag) {
        this.tag = tag;
        tag.getTagSchedules().add(this);
    }

    private void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.getTagSchedules().add(this);
    }
}
