package com.worker.api.impl.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.worker.api.specification.WorkerService;
import com.worker.api.specification.dto.CrackHashManagerDoTaskRequest;
import com.worker.additional_services.rabbitmq.port.RabbitMQConsumer;
import com.worker.api.impl.domain.service.MD5Crack;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final MD5Crack md5Crack;
    private static final Logger log = LoggerFactory.getLogger(WorkerServiceImpl.class);
    @Override
    public List<String> crackHash(CrackHashManagerDoTaskRequest requestDTO) {
        
        List<String> words = new ArrayList<>();
    
        for(int i = 1; i < requestDTO.maxLength() + 1; i += 1){
            log.info("crackHash: " + Integer.toString(i) + " " + requestDTO.requestId() + " " + requestDTO.partNumber());
            List<String> currenWords = md5Crack.generateWords(i, requestDTO);

            for(String s: currenWords){
                words.add(s);
            }
        }

        return words;
    }
}
