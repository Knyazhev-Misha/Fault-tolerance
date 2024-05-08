package com.manager.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashCrackTasksRepository extends MongoRepository<HashCrackTasksDocument, String> {
    Optional<HashCrackTasksDocument> findByRequestId(String requestId);

    List<HashCrackTasksDocument> findByStatusSend(String statusSend);
}
