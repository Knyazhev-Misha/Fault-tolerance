package com.manager.mongo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HashCrackTasksService {
    private static final Logger log = LoggerFactory.getLogger(HashCrackTasksService.class);
    
    private final HashCrackTasksRepository taskRepository;

    public Boolean saveHashCrackTask(HashCrackTasksDocument task) {
        HashCrackTasksDocument saveTask = taskRepository.save(task);

        if(saveTask != null){
            return true;
        }

        else{
            return false;
        }
    }

    public Optional<HashCrackTasksDocument> findByRequestId(String requestId) {
        return taskRepository.findByRequestId(requestId);
    }

    public List<HashCrackTasksDocument> findByStatusSend(String statusSend) {
        return taskRepository.findByStatusSend(statusSend);
    }
}

