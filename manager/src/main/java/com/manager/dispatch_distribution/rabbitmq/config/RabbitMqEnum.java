package com.manager.dispatch_distribution.rabbitmq.config;

public interface RabbitMqEnum {
    String rabbitmq_exchange = "tasks_exchange";
    String rabbitmq_worker_input_queue = "worker_input_queue";
    String rabbitmq_worker_output_queue = "worker_output_queue";
    String rabbitmq_worker_status_queue = "worker_status_queue";
}
