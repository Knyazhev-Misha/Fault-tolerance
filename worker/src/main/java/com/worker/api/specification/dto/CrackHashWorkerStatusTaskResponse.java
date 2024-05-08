package com.worker.api.specification.dto;

public record CrackHashWorkerStatusTaskResponse(
    String requestId,
    int partNumber,
    double percent,
    int length
    )
{
}
