package com.compono.ibackend.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import com.compono.ibackend.factory.ScheduleFactory;
import com.compono.ibackend.factory.TagFactory;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.request.SchedulePointRequest;
import com.compono.ibackend.schedule.dto.request.ScheduleRequest;
import com.compono.ibackend.schedule.dto.response.ScheduleResponse;
import com.compono.ibackend.schedule.repository.ScheduleRepository;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.service.TagScheduleService;
import com.compono.ibackend.tag.service.TagService;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.compono.ibackend.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[비즈니스 로직] - 스케줄")
@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    private static final String EMAIL = "compono@test.com";
    @InjectMocks private ScheduleService scheduleService;
    @Mock private ScheduleRepository scheduleRepository;
    @Mock private PointService pointService;
    @Mock private TagService tagService;
    @Mock private TagScheduleService tagScheduleService;
    @Mock private UserService userService;

    @DisplayName("[정상 케이스] 스케쥴 생성한다.")
    @Test
    void addSchedule() {
        List<Tag> tags = createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        Schedule schedule = ScheduleFactory.createSchedule(user, tags);
        ScheduleRequest scheduleRequest = createScheduleRequest(schedule, tags);

        when(userService.findUserByEmail(EMAIL)).thenReturn(user);
        when(tagService.findAllTagById(scheduleRequest.tags())).thenReturn(tags);
        when(scheduleRepository.save(any(Schedule.class)))
                .thenReturn(scheduleRequest.toEntity(user));

        ScheduleResponse response = scheduleService.addSchedule(EMAIL, scheduleRequest);

        assertThat(response.taskName()).isEqualTo(schedule.getTaskName());
    }

    @DisplayName("[오류 케이스 - INVALID_SCHEDULE] 스케쥴 생성하지 못 한다.")
    @Test()
    void addSchedule_invalidSchedule() {
        List<Tag> tags = createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Schedule invalidDateSchedule = ScheduleFactory.createInvaildDateSchedule(user, tags);
        ScheduleRequest request1 = createScheduleRequest(invalidDateSchedule, tags);

        Schedule invaildPeriodSchedule = ScheduleFactory.createInvaildPeriodSchedule(user, tags);
        ScheduleRequest request2 = createScheduleRequest(invaildPeriodSchedule, tags);

        CustomException ex1 =
                assertThrows(
                        CustomException.class, () -> scheduleService.addSchedule(EMAIL, request1));
        assertThat(ex1.getErrorCode()).isEqualTo(ErrorCode.INVALID_SCHEDULE);

        CustomException ex2 =
                assertThrows(
                        CustomException.class, () -> scheduleService.addSchedule(EMAIL, request2));
        assertThat(ex2.getErrorCode()).isEqualTo(ErrorCode.INVALID_SCHEDULE);
    }

    @DisplayName("[오류 케이스 - NOT_FOUND_TAG_ID] 스케쥴 생성하지 못 한다.")
    @Test()
    void addSchedule_notFoundTagId() {
        List<Tag> tags = createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        Schedule schedule = ScheduleFactory.createSchedule(user, tags);

        ScheduleRequest invalidRequest =
                new ScheduleRequest(
                        schedule.getTaskName(),
                        schedule.getPriority(),
                        List.of(1L, 5L),
                        schedule.getStartDate(),
                        schedule.getEndDate(),
                        new SchedulePointRequest(
                                schedule.getPoint().getLongitude(),
                                schedule.getPoint().getLatitude()),
                        schedule.getIsRoutine(),
                        schedule.getRoutinePeriod(),
                        schedule.getIsMarked());

        CustomException ex =
                assertThrows(
                        CustomException.class,
                        () -> scheduleService.addSchedule(EMAIL, invalidRequest));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_TAG_ID);
    }

    public User createUser() {
        UserAddRequest request =
                new UserAddRequest(EMAIL, "test", OauthProvider.GOOGLE, null, true);
        return User.from(request);
    }

    public List<Tag> createTags() {
        Tag tag1 = TagFactory.createTag("공부");
        Tag tag2 = TagFactory.createTag("자기계발");
        ReflectionTestUtils.setField(tag1, "id", 1L);
        ReflectionTestUtils.setField(tag2, "id", 2L);

        return List.of(tag1, tag2);
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
