package com.manager.rest_api.impl.domain;

public interface HashCrackEnum {
    //сборник всех названий

    //сколько воркеров всего
    int count_worker = 5;

    //на сколько частей делим таску
    int task_part_count = 5;

    //статус сохранения
    String mongodb_status_save_false = "Task can't save";
    String mongodb_status_save_true = "Task save";

    //статус выполнения таски
    String task_status_error = "Error";
    String task_status_in_progress = "In Progress";
    String task_status_done = "Done";

    //статус отправки таски рэббит мк
    String task_send_status_false = "Not_send";
    String task_send_status_fail = "Fail_send";
    String task_send_status_true = "Send";

    //сущности рэббит мк
    String rabbitmq_exchange = "tasks_exchange";
    String rabbitmq_manager_input_queue = "manager_input_queue";
    String rabbitmq_worker_input_queue = "worker_input_queue";
    String rabbitmq_worker_output_queue = "worker_output_queue";
}

