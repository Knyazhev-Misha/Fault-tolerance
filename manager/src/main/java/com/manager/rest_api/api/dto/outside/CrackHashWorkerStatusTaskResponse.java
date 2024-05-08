package com.manager.rest_api.api.dto.outside;

public record CrackHashWorkerStatusTaskResponse(
    String requestId,
    int partNumber,
    double percent,
    int length
    )
{
}
