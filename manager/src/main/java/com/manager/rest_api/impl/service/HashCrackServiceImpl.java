package com.manager.rest_api.impl.service;

import static com.manager.rest_api.impl.domain.HashCrackEnum.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manager.dispatch_distribution.DispatchDistributionService;
import com.manager.docker.service.DockerService;
import com.manager.rest_api.api.HashCrackService;
import com.manager.rest_api.api.dto.ManagerDTO;
import com.manager.rest_api.api.dto.inside.CrackRequestInsideDTO;
import com.manager.rest_api.api.dto.inside.CrackResponseInsideDTO;
import com.manager.rest_api.api.dto.inside.CrackStatusResponseInsideDTO;
import com.manager.rest_api.api.dto.inside.ManagerStatusResponseDTO;
import com.manager.rest_api.api.dto.outside.CrackOutsideResponseDTO;
import com.manager.rest_api.impl.domain.service.Alphabet;
import com.manager.rest_api.impl.domain.service.HashCrackDomain;
import com.manager.mongo.HashCrackTasksDocument;
import com.manager.mongo.HashCrackTasksService;

import jakarta.annotation.PreDestroy;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HashCrackServiceImpl implements HashCrackService{
    private static final Logger log = LoggerFactory.getLogger(HashCrackServiceImpl.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final HashCrackTasksService tasksService;
    private final DispatchDistributionService distributionService;

    @Override
    public ManagerDTO SaveHashCrackTask(CrackRequestInsideDTO requestDTO, Integer numCase) {
        String requsestId = UUID.randomUUID().toString();

        log.info("Get task, id: " + requsestId);

        HashCrackTasksDocument document = HashCrackDomain.
            createHashCrackTasksDocument(requsestId, requestDTO);

        boolean taskSave = tasksService.saveHashCrackTask(document);

        
        if(taskSave){
            executorService.submit(() -> distributionService.sendRequestToWorker(document, numCase));
            
            return new CrackResponseInsideDTO(requsestId);
        }

        else{
            return new ManagerStatusResponseDTO(mongodb_status_save_false);
        }
    }

    @Override
    public ManagerDTO GetStatusHashCrackTask(String id) {
        Optional<HashCrackTasksDocument> document = tasksService.findByRequestId(id);
         
        if(document.isPresent()){
            String status = task_status_done;

            for(String s : document.get().getAnswerStatus()){
                if(task_status_in_progress.equals(s)) {
                    status = task_status_in_progress;
                    break;
                }

                else if(task_status_error.equals(s)) {
                    status = task_status_error;
                    break;
                }
            }

            
            int percent = 0;
            for(Integer i : document.get().getPercent()){
                percent += i;
            }

            percent /= document.get().getPercent().size();

            if(task_status_done.equals(status)){percent = 100;}

            return new CrackStatusResponseInsideDTO(status, 
                document.get().getData(), percent);
        }

        return null;
    }

    @Override
    public synchronized void SaveHashCrackTaskData(CrackOutsideResponseDTO requestDTO) {
        Optional<HashCrackTasksDocument> document = tasksService.findByRequestId(requestDTO.requestId());

        if(!document.isPresent() || task_status_done.
            equals(document.get().getAnswerStatus().get(requestDTO.partNumber() - 1))){
            return;
        }

        List<String> data = document.get().getData();

        document.get().setData(Alphabet.uniqConcatList(data, requestDTO.words()));      

        document.get().getAnswerStatus().set(requestDTO.partNumber() - 1, task_status_done);

        tasksService.saveHashCrackTask(document.get());
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}
