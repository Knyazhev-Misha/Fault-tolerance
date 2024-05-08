package com.worker.additional_services.rabbitmq.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;

import com.rabbitmq.client.Channel;


import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.worker.api.specification.WorkerService;
import com.worker.api.specification.dto.CrackHashManagerDoTaskRequest;
import com.worker.additional_services.rabbitmq.config.RabbitMqConfig;
import com.worker.additional_services.rabbitmq.service.RabbitMqSendService;
import com.worker.api.impl.domain.service.WorkerDomainService;
import com.worker.api.specification.dto.CrackHashWorkerDoneTaskResponse;

import lombok.AllArgsConstructor;

import static com.worker.additional_services.rabbitmq.config.RabbitMqEnum.*;

@Controller
@AllArgsConstructor
public class RabbitMQConsumer {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final WorkerService workerService;
    private final RabbitMqSendService rabbitMqSendService;
    private final RabbitMqConfig rabbitmq;

    @RabbitListener(queues = rabbitmq_worker_input_queue)
    public void consumer1WorkerOutputQueue(String message, Channel channel) {
        
        CrackHashManagerDoTaskRequest requestDTO = new Gson().fromJson(message, CrackHashManagerDoTaskRequest.class);
    
        log.info("WorkerInputQueue get message from: " + requestDTO.requestId() + " with part num" + requestDTO.partNumber() + " case " + requestDTO.numCase() 
        + " replica " + rabbitmq.getReplicaIndex());

        if(requestDTO.numCase() != null && requestDTO.numCase() == 4 && "2".equals(rabbitmq.getReplicaIndex())){
            System.exit(0);
        }
        
        CrackHashWorkerDoneTaskResponse  response = WorkerDomainService.
            createCrackHashWorkerDoneTaskResponse(requestDTO, workerService.crackHash(requestDTO));

        log.info("Worker find word: " + requestDTO.requestId() + " with part num " + requestDTO.partNumber() + " data: " + response.words());

        rabbitMqSendService.sendDoneTask(response);
    }
}
