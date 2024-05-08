package com.worker.api.specification;

import java.util.List;

import com.worker.api.specification.dto.CrackHashManagerDoTaskRequest;

public interface WorkerService {
    public List<String> crackHash(CrackHashManagerDoTaskRequest requestDTO);
}
