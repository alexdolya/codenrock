package com.legends.taskFlow.service;

import java.time.LocalDateTime;

public interface EstimateTimeConverterService {
    String generateEstimateTimeString(LocalDateTime start, LocalDateTime end);
}
