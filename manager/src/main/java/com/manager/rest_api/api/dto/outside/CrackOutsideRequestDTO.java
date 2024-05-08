package com.manager.rest_api.api.dto.outside;

import java.util.List;

import com.manager.rest_api.api.dto.ManagerDTO;

public record CrackOutsideRequestDTO(String requestId, int partNumber, int partCount, 
String hash, int maxLength, List<String> alphabet, Integer numCase) implements ManagerDTO
{}
