package com.legends.taskFlow.service.Impl;

import com.legends.taskFlow.service.EstimateTimeConverterService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class EstimateTimeConverterServiceImpl implements EstimateTimeConverterService {

    public String generateEstimateTimeString(LocalDateTime start, LocalDateTime end) {
        long between = ChronoUnit.DAYS.between(start, end);
        if (between < 7) {
            return between+"d";

        }

        between = ChronoUnit.WEEKS.between(start, end);
        if (between < 4) {
            return between+"w";
        }
        return ChronoUnit.MONTHS.between(start, end) + "m";
    }

}
