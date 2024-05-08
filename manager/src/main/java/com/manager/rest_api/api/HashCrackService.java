package com.manager.rest_api.api;

import com.manager.rest_api.api.dto.ManagerDTO;
import com.manager.rest_api.api.dto.inside.CrackRequestInsideDTO;
import com.manager.rest_api.api.dto.outside.CrackOutsideResponseDTO;

public interface HashCrackService {
    ManagerDTO SaveHashCrackTask(CrackRequestInsideDTO requestDTO, Integer numCase);

    ManagerDTO GetStatusHashCrackTask(String id);

    void SaveHashCrackTaskData(CrackOutsideResponseDTO requestDTO);
}
