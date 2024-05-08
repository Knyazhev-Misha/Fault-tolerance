package com.manager.dispatch_distribution.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.manager.rest_api.api.dto.outside.CrackOutsideRequestDTO;

import static com.manager.rest_api.impl.domain.HashCrackEnum.*;

import java.io.IOException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RabbitMqSendService {
    private static final Logger log = LoggerFactory.getLogger(RabbitMqSendService.class);
    
    public void sendMessageToWorker(CrackOutsideRequestDTO message) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq"); 
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(rabbitmq_exchange, "direct", true);

            String jsonMessage = new Gson().toJson(message);
            channel.basicPublish(rabbitmq_exchange, rabbitmq_worker_input_queue, 
            MessageProperties.PERSISTENT_TEXT_PLAIN, jsonMessage.getBytes("UTF-8"));

            log.info("Message send to RabbitMQ with id: " + message.requestId() + 
                " partNumber: " + message.partNumber());

        } 
        
        catch (IOException e) {
            log.warn("Message not send to RabbitMQ: " + message);
            throw new Exception("Error send", e);
        }
    }
}
