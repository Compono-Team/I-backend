package com.compono.ibackend.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.compono.ibackend.factory.ScheduleFactory;
import com.compono.ibackend.factory.TagFactory;
import com.compono.ibackend.factory.UserFactory;
import com.compono.ibackend.schedule.domain.Point;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.request.SchedulePointRequest;
import com.compono.ibackend.schedule.dto.request.ScheduleRequest;
import com.compono.ibackend.schedule.repository.PointRepository;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[비즈니스 로직] - 포인트 (위치)")
@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    private static final String EMAIL = "compono@test.com";
    @InjectMocks private PointService pointService;
    @Mock private PointRepository pointRepository;

    @DisplayName("[정상 케이스] 포인트를 생성한다.")
    @Test
    void addPoint() {
        List<Tag> tags = createTags();
        Schedule schedule = createSchedule(tags);
        ScheduleRequest scheduleRequest = createScheduleRequest(schedule, tags);
        SchedulePointRequest schedulePointRequest = scheduleRequest.point();

        when(pointRepository.save(any(Point.class)))
                .thenReturn(schedulePointRequest.toEntity(schedule));

        Point point = pointService.addPoint(schedule, schedulePointRequest);

        assertThat(point.getId()).isEqualTo(schedule.getPoint().getId());
    }

    public List<Tag> createTags() {
        Tag tag1 = TagFactory.createTag("공부");
        Tag tag2 = TagFactory.createTag("자기계발");
        ReflectionTestUtils.setField(tag1, "id", 1L);
        ReflectionTestUtils.setField(tag2, "id", 2L);

        return List.of(tag1, tag2);
    }

    public Schedule createSchedule(List<Tag> tags) {
        User user = UserFactory.createUser(EMAIL);
        ReflectionTestUtils.setField(user, "id", 1L);

        return ScheduleFactory.createSchedule(user, tags);
    }

    public ScheduleRequest createScheduleRequest(Schedule schedule, List<Tag> tags) {
        return new ScheduleRequest(
                schedule.getTaskName(),
                schedule.getPriority(),
                tags.stream().map(tag -> tag.getId()).toList(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                new SchedulePointRequest(
                        schedule.getPoint().getLongitude(), schedule.getPoint().getLatitude()),
                schedule.getIsRoutine(),
                schedule.getRoutinePeriod(),
                schedule.getIsMarked());
    }
}
