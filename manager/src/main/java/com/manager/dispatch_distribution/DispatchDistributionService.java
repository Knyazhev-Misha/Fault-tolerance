package com.manager.dispatch_distribution;

import static com.manager.rest_api.impl.domain.HashCrackEnum.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.manager.dispatch_distribution.rabbitmq.RabbitMqSendService;
import com.manager.docker.service.DockerService;
import com.manager.rest_api.api.dto.outside.CrackOutsideRequestDTO;
import com.manager.rest_api.impl.domain.service.HashCrackDomain;
import com.manager.mongo.HashCrackTasksDocument;
import com.manager.mongo.HashCrackTasksService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DispatchDistributionService {
    private static final Logger log = LoggerFactory.getLogger(DispatchDistributionService.class);

    private final RabbitMqSendService rabbitMqSendService;
    private final HashCrackTasksService hashCrackTasksService;
    private final DockerService dockerService;

    public void sendRequestToWorker(HashCrackTasksDocument documentForSend, Integer numCase){
        for(int part = 1; part <= documentForSend.getPartCount(); part += 1) {
            if(documentForSend.getStatusSend().get(part - 1) == task_send_status_true){
                continue;
            }
            
            CrackOutsideRequestDTO requestDTO = HashCrackDomain.
                createCrackOutsideRequestDTO(documentForSend, part, numCase);

            try{
                rabbitMqSendService.sendMessageToWorker(requestDTO);

                documentForSend.getStatusSend().set(part - 1, task_send_status_true);
                hashCrackTasksService.saveHashCrackTask(documentForSend);
            } 
            
            catch(Exception e){

                if(e.toString().contains("Error send")){
                    Optional<HashCrackTasksDocument> documentForSetStatus = hashCrackTasksService.
                        findByRequestId(requestDTO.requestId());

                    if(documentForSetStatus.isPresent()){
                        documentForSetStatus.get().getStatusSend().set(part - 1, task_send_status_fail);
                        hashCrackTasksService.saveHashCrackTask(documentForSetStatus.get());
                    }
                }
            }
        }

        dockerService.testCase(numCase);
    }

    @Scheduled(initialDelay = 2000, fixedRate = 6000)
    private void sendFailMessage(){
        log.info("Start send fail message");

        List<HashCrackTasksDocument> documents = hashCrackTasksService.findByStatusSend(task_send_status_fail);
        
        for(HashCrackTasksDocument document : documents){
           sendRequestToWorker(document, null);
        }
    }
}
