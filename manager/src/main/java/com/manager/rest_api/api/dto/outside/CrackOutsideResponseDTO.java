package com.manager.rest_api.api.dto.outside;

import java.util.List;

import com.manager.rest_api.api.dto.ManagerDTO;

public record CrackOutsideResponseDTO(String requestId, int partNumber, List<String> words) implements ManagerDTO
{}
