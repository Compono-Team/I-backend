package com.compono.ibackend.tag.service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.compono.ibackend.factory.ScheduleFactory;
import com.compono.ibackend.factory.TagFactory;
import com.compono.ibackend.factory.UserFactory;
import com.compono.ibackend.schedule.domain.Schedule;
import com.compono.ibackend.tag.domain.Tag;
import com.compono.ibackend.tag.repository.TagScheduleRepository;
import com.compono.ibackend.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[비즈니스 로직] - 태그 스케줄")
@ExtendWith(MockitoExtension.class)
public class TagScheduleServiceTest {

    private static final String EMAIL = "compono@test.com";
    @InjectMocks private TagScheduleService tagScheduleService;
    @Mock private TagScheduleRepository tagScheduleRepository;

    @DisplayName("[정상 케이스] TagSchedule을 생성한다.")
    @Test
    void addTagSchedule() {
        List<Tag> tags = TagFactory.createTags();
        Schedule schedule = createSchedule(tags);

        tagScheduleService.addTagSchedule(tags, schedule);

        verify(tagScheduleRepository, times(1)).saveAll(anyList());
    }

    public Schedule createSchedule(List<Tag> tags) {
        User user = UserFactory.createUser(EMAIL);
        ReflectionTestUtils.setField(user, "id", 1L);

        return ScheduleFactory.createSchedule(user, tags);
    }
}
