package com.manager.docker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;

@Configuration
public class DockerConfiguration {
    
    @Bean
    public DockerClient dockerClient() {
        DockerClient dockerClient = DockerClientBuilder.getInstance()
        .withDockerCmdExecFactory(new NettyDockerCmdExecFactory())
        .build();
        
        return dockerClient;
    }
}
