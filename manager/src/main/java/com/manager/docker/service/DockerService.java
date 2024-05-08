package com.manager.docker.service;

import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.StopContainerCmd;
import lombok.AllArgsConstructor;

import static com.manager.docker.config.DockerEnum.*;

@Service
@AllArgsConstructor
public class DockerService {
    private DockerClient dockerClient;

    public void stopRabbitMqContainer() {
        StopContainerCmd stopContainerCmd = dockerClient.stopContainerCmd(RABBITMQ);
        stopContainerCmd.exec();
    }
    
    public void stopMongoDbPrimaryContainer() {
        StopContainerCmd stopContainerCmd = dockerClient.stopContainerCmd(MONGO);
        stopContainerCmd.exec();
    }

    public void stopManagerContainer() {
        StopContainerCmd stopContainerCmd = dockerClient.stopContainerCmd(MANAGER);
        stopContainerCmd.exec();
    }

    public void stopWorkerContainer() {
        StopContainerCmd stopContainerCmd = dockerClient.stopContainerCmd(WORKER);
        stopContainerCmd.exec();
    }

    public void testCase(Integer numCase){
        if(numCase == null){
            return;
        }

        else if(numCase == 1){
            //stopManagerContainer();
            System.exit(0);
        }

        else if(numCase == 2){
            stopMongoDbPrimaryContainer();
        }

        else if(numCase == 3){
            stopRabbitMqContainer();
        }

        else if(numCase == 4){
           // stopWorkerContainer();
        }
    }
}
