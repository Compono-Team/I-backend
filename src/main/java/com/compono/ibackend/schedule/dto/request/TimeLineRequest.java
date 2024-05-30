package com.compono.ibackend.schedule.dto.request;

import java.time.LocalDateTime;

public record TimeLineRequest(Long userId, LocalDateTime specificTime) {}
