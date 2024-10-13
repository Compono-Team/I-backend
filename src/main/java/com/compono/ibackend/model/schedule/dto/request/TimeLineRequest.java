package com.compono.ibackend.model.schedule.dto.request;

import java.time.LocalDateTime;

public record TimeLineRequest(Long userId, LocalDateTime specificTime) {}
