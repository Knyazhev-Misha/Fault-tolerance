package com.worker.api.specification.dto;

import java.util.List;

public record CrackHashWorkerDoneTaskResponse(
                String requestId,
                int partNumber,
                List<String> words) {
}
