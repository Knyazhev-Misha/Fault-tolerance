package com.worker.additional_services.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;

import static com.worker.additional_services.rabbitmq.config.RabbitMqEnum.*;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.host:localhost}")
    private String host;

    @Value("${rabbitmq.port:5672}")
    private int port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.virtual-host:/}")
    private String virtualHost;

    @Getter
    @Value("${replica.index}")
    private String replicaIndex;
    
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        return cachingConnectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setRecoveryInterval(20000L);
        return factory;
    }
    
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue rabbitmqWorkerInputQueue() {
        return new Queue(rabbitmq_worker_input_queue);
    }

    @Bean
    public Queue rabbitmqWorkerOutputQueue() {
        return new Queue(rabbitmq_worker_output_queue);
    }

    @Bean
    public Queue rabbitmqWorkerStatusQueue() {
        return new Queue(rabbitmq_worker_status_queue);
    }

    @Bean
    DirectExchange rabbitmqExchange() {
        return new DirectExchange(rabbitmq_exchange, true, false);
    }

    @Bean
    Binding bindingRabbitmqWorkerInputQueue(Queue rabbitmqWorkerInputQueue, DirectExchange rabbitmqExchange) {
        return BindingBuilder.bind(rabbitmqWorkerInputQueue).to(rabbitmqExchange).with(rabbitmq_worker_input_queue);
    }

    @Bean
    Binding bindingRabbitmqWorkerOutputQueue(Queue rabbitmqWorkerOutputQueue, DirectExchange rabbitmqExchange) {
        return BindingBuilder.bind(rabbitmqWorkerOutputQueue).to(rabbitmqExchange).with(rabbitmq_worker_output_queue);
    }

    @Bean
    Binding bindingRabbitmqWorkerStatusQueue(Queue rabbitmqWorkerStatusQueue, DirectExchange rabbitmqExchange) {
        return BindingBuilder.bind(rabbitmqWorkerStatusQueue).to(rabbitmqExchange).with(rabbitmq_worker_status_queue);
    }
}