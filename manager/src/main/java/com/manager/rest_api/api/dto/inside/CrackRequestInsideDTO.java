package com.manager.rest_api.api.dto.inside;

import com.manager.rest_api.api.dto.ManagerDTO;

public record CrackRequestInsideDTO(String hash, int maxLength) implements ManagerDTO
{}