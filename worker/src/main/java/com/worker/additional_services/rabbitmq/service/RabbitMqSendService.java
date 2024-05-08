package com.worker.additional_services.rabbitmq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.worker.api.specification.dto.CrackHashWorkerDoneTaskResponse;
import com.worker.api.specification.dto.CrackHashWorkerStatusTaskResponse;

import static com.worker.additional_services.rabbitmq.config.RabbitMqEnum.*;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RabbitMqSendService {

    private RabbitTemplate template;

    public void sendDoneTask(final CrackHashWorkerDoneTaskResponse message) {
        String str = new Gson().toJson(message);
        template.convertAndSend(rabbitmq_exchange, rabbitmq_worker_output_queue, str);
    }

    public void sendStatusTask(final CrackHashWorkerStatusTaskResponse message) {
        String str = new Gson().toJson(message);
        template.convertAndSend(rabbitmq_exchange, rabbitmq_worker_status_queue, str);
    }
}
