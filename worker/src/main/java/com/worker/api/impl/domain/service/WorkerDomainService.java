package com.worker.api.impl.domain.service;

import java.util.List;

import com.worker.api.specification.dto.CrackHashManagerDoTaskRequest;
import com.worker.api.specification.dto.CrackHashWorkerDoneTaskResponse;
import com.worker.api.specification.dto.CrackHashWorkerStatusTaskResponse;

public class WorkerDomainService {
    public static CrackHashWorkerDoneTaskResponse createCrackHashWorkerDoneTaskResponse(
        CrackHashManagerDoTaskRequest requestDTO,
            List<String> data) {

        return new CrackHashWorkerDoneTaskResponse(
                requestDTO.requestId(),
                requestDTO.partNumber(),
                data);

    }

    public static CrackHashWorkerStatusTaskResponse createCrackHashWorkerStatus(
        CrackHashManagerDoTaskRequest requestDTO,
            double percent, int length) {

        return new CrackHashWorkerStatusTaskResponse(
                requestDTO.requestId(),
                requestDTO.partNumber(),
                percent,
                length
                );

    }
}
