package com.legends.taskFlow.service;

import com.legends.taskFlow.model.entity.Status;
import com.legends.taskFlow.model.enums.StatusEnum;

public interface StatusService {
    Status findProjectStatus(StatusEnum statusEnum);
}
