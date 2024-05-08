package com.manager.dispatch_distribution.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.manager.mongo.HashCrackTasksDocument;
import com.manager.mongo.HashCrackTasksService;
import com.manager.rest_api.api.HashCrackService;
import com.manager.rest_api.api.dto.outside.CrackHashWorkerStatusTaskResponse;
import com.manager.rest_api.api.dto.outside.CrackOutsideResponseDTO;

import lombok.AllArgsConstructor;

import static com.manager.dispatch_distribution.rabbitmq.config.RabbitMqEnum.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class RabbitMqConsumer {
    private static final Logger log = LoggerFactory.getLogger(RabbitMqConsumer.class);
    private final HashCrackService hashCrackService;
    private final HashCrackTasksService hashCrackTasksService;
    
    @RabbitListener(queues = rabbitmq_worker_output_queue)
    public void consumerWorkerOutputQueue(String message) {
        log.info("consumerWorkerOutputQueue" + message);
        CrackOutsideResponseDTO requestDTO = new Gson().
            fromJson(message, CrackOutsideResponseDTO.class);
        hashCrackService.SaveHashCrackTaskData(requestDTO);
    }

    @RabbitListener(queues = rabbitmq_worker_status_queue)
    public void consumerWorkerStatusQueue(String message) {
       /* log.info("WorkerOutputQueue" + message);

        CrackHashWorkerStatusTaskResponse requestDTO = new Gson().
            fromJson(message, CrackHashWorkerStatusTaskResponse.class);

        Optional<HashCrackTasksDocument> document = hashCrackTasksService.findByRequestId(requestDTO.requestId());

        if(document.isPresent()){
            int procent =  document.get().getPercent().get(requestDTO.partNumber() - 1);
            procent += (int)((requestDTO.percent() - (double)procent) / (double)document.get().getMaxLength());
            document.get().getPercent().set(requestDTO.partNumber() - 1, procent);
            hashCrackTasksService.saveHashCrackTask(document.get());
        }
        */
    }
}
