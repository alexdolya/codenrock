package com.legends.taskFlow.service.Impl;

import com.legends.taskFlow.exception.EntityNotExistsException;
import com.legends.taskFlow.model.entity.Status;
import com.legends.taskFlow.model.enums.StatusEnum;
import com.legends.taskFlow.repositorty.StatusRepository;
import com.legends.taskFlow.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public Status findProjectStatus(StatusEnum statusEnum) {
        return statusRepository.findByName(statusEnum.toString()).orElseThrow(() -> new EntityNotExistsException("No status \"" + statusEnum + "\" in database"));
    }

}
