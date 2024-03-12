package com.compono.ibackend.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import com.compono.ibackend.factory.ScheduleFactory;
import com.compono.ibackend.factory.TagFactory;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.schedule.dto.request.SchedulePointRequest;
import com.compono.ibackend.schedule.dto.request.ScheduleRequest;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleDetailWithTagResponse;
import com.compono.ibackend.schedule.dto.response.ScheduleResponse;
import com.compono.ibackend.schedule.dto.response.TagDetailResponse;
import com.compono.ibackend.schedule.repository.ScheduleRepository;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.service.TagScheduleService;
import com.compono.ibackend.tag.service.TagService;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.compono.ibackend.user.service.UserService;
import java.util.List;
import java.util.Optional;
import org.hibernate.validator.internal.util.Contracts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[비즈니스 로직] - 스케줄")
@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    private static final String EMAIL = "compono@test.com";
    @InjectMocks private ScheduleService scheduleService;
    @Mock private ScheduleRepository scheduleRepository;
    @Mock private TagService tagService;
    @Mock private UserService userService;
    @Mock private PointService pointService;
    @Mock private TagScheduleService tagScheduleService;

    @DisplayName("[정상 케이스] 스케쥴 생성한다.")
    @Test
    void addSchedule() {
        List<Tag> tags = TagFactory.createTags();
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
        List<Tag> tags = TagFactory.createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);

        Schedule invalidDateSchedule = ScheduleFactory.createInvaildDateSchedule(user, tags);
        ScheduleRequest request1 = createScheduleRequest(invalidDateSchedule, tags);

        Schedule invaildRoutineSchedule = ScheduleFactory.createInvaildRoutineSchedule(user, tags);
        ScheduleRequest request2 = createScheduleRequest(invaildRoutineSchedule, tags);

        Schedule invaildNotRoutineSchedule =
                ScheduleFactory.createInvaildNotRoutineSchedule(user, tags);
        ScheduleRequest request3 = createScheduleRequest(invaildNotRoutineSchedule, tags);

        CustomException ex1 =
                assertThrows(
                        CustomException.class, () -> scheduleService.addSchedule(EMAIL, request1));
        assertThat(ex1.getErrorCode()).isEqualTo(ErrorCode.INVALID_SCHEDULE);

        CustomException ex2 =
                assertThrows(
                        CustomException.class, () -> scheduleService.addSchedule(EMAIL, request2));
        assertThat(ex2.getErrorCode()).isEqualTo(ErrorCode.INVALID_SCHEDULE);

        CustomException ex3 =
                assertThrows(
                        CustomException.class, () -> scheduleService.addSchedule(EMAIL, request3));
        assertThat(ex3.getErrorCode()).isEqualTo(ErrorCode.INVALID_SCHEDULE);
    }

    @DisplayName("[오류 케이스 - NOT_FOUND_TAG_ID] 스케쥴 생성하지 못 한다.")
    @Test()
    void addSchedule_notFoundTagId() {
        List<Tag> tags = TagFactory.createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        Schedule schedule = ScheduleFactory.createSchedule(user, tags);
        ScheduleRequest scheduleRequest = createScheduleRequest(schedule, tags);

        when(tagService.findAllTagById(scheduleRequest.tags())).thenReturn(tags.subList(0, 1));

        CustomException ex =
                assertThrows(
                        CustomException.class,
                        () -> scheduleService.addSchedule(EMAIL, scheduleRequest));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_TAG_ID);
    }

    @DisplayName("[정상 케이스] id로 스케쥴 단일 조회를 한다.")
    @Test()
    void findScheduleDetailById() {
        List<Tag> tags = TagFactory.createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        Schedule schedule = ScheduleFactory.createSchedule(user, tags);
        ScheduleDetailWithTagResponse scheduleDetailResponse =
                createScheduleDetailWithTagResponse(schedule, tags);

        when(userService.findUserByEmail(EMAIL)).thenReturn(user);
        when(scheduleRepository.findScheduleByUserAndScheduleId(user, schedule.getId()))
                .thenReturn(scheduleDetailResponse);

        ScheduleDetailWithTagResponse foundResponse =
                scheduleService.findScheduleDetailById(user.getEmail(), schedule.getId());

        assertNotNull(foundResponse);
        assertThat(scheduleDetailResponse.equals(foundResponse));
    }

    @DisplayName("[오류 케이스 - NOT_FOUND_SCHEDULE_ID] id로 스케쥴 단일 조회를 할 수 없다")
    @Test()
    void findScheduleDetailById_notFoundScheduleId() {
        List<Tag> tags = TagFactory.createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        Schedule schedule = ScheduleFactory.createSchedule(user, tags);

        when(userService.findUserByEmail(EMAIL)).thenReturn(user);
        when(scheduleRepository.findScheduleByUserAndScheduleId(user, schedule.getId()))
                .thenReturn(null);

        CustomException ex =
                assertThrows(
                        CustomException.class,
                        () ->
                                scheduleService.findScheduleDetailById(
                                        user.getEmail(), schedule.getId()));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_SCHEDULE_ID);
    }

    @DisplayName("[정상 케이스] id에 대한 Schedule 를 조회한다.")
    @Test
    void findScheduleById() {
        List<Tag> tags = TagFactory.createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        Schedule schedule = ScheduleFactory.createSchedule(user, tags);

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));

        Schedule foundSchedule = scheduleService.findScheduleById(schedule.getId());

        Contracts.assertNotNull(foundSchedule);
        assertThat(schedule.equals(foundSchedule));
    }

    @DisplayName("[오류 케이스 - NOT_FOUND_SCHEDULE_ID] id에 대한 Schedule 를 조회하지 못 한다.")
    @Test
    void findScheduleById_notFoundScheduleId() {
        List<Tag> tags = TagFactory.createTags();
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        Schedule schedule = ScheduleFactory.createSchedule(user, tags);

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());

        CustomException ex =
                assertThrows(
                        CustomException.class,
                        () -> scheduleService.findScheduleById(schedule.getId()));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_SCHEDULE_ID);
    }

    public User createUser() {
        UserAddRequest request =
                new UserAddRequest(EMAIL, "test", OauthProvider.GOOGLE, null, true);
        return User.from(request);
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

    public ScheduleDetailWithTagResponse createScheduleDetailWithTagResponse(
            Schedule schedule, List<Tag> tags) {
        ScheduleDetailResponse scheduleDetailResponse = ScheduleDetailResponse.of(schedule);

        List<TagDetailResponse> tagDetailResponses =
                tags.stream()
                        .map(
                                tag ->
                                        new TagDetailResponse(
                                                tag.getId(), tag.getName(), tag.getColor()))
                        .toList();
        return new ScheduleDetailWithTagResponse(scheduleDetailResponse, tagDetailResponses);
    }
}
