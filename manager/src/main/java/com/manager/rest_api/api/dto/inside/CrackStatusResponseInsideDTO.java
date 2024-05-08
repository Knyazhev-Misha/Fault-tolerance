package com.manager.rest_api.api.dto.inside;

import java.util.List;

import com.manager.rest_api.api.dto.ManagerDTO;

public record CrackStatusResponseInsideDTO(String status, List<String> data, int percent) implements ManagerDTO
{}
