package com.manager.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;

@Data
@Document(collection = "hash_crack_tasks")
public class HashCrackTasksDocument {
    @Id
    private String id;
    private String requestId;
    private int maxLength;
    private String hash;
    private List<String> data;
    private List<String> answerStatus;
    private List<Integer> percent;
    private int partCount;
    private List<String> statusSend;
    private LocalDateTime creationDateTime;

    public HashCrackTasksDocument(String requestId, int maxLength, String hash, 
        List<String> data, List<String> answerStatus, List<Integer> percent, int partCount, 
        List<String> statusSend, LocalDateTime creationDateTime){
            this.requestId = requestId;
            this.maxLength = maxLength;
            this.hash = hash;
            this.data = data;
            this.answerStatus = answerStatus;
            this.percent = percent;
            this.partCount = partCount;
            this.statusSend = statusSend;
            this. creationDateTime = creationDateTime;
        }
}

