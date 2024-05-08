package com.worker.api.specification.dto;

import java.util.List;

public record CrackHashManagerDoTaskRequest(
                String requestId,
                int partNumber,
                int partCount,
                String hash,
                int maxLength,
                List<String> alphabet,
                Integer numCase) {
}
