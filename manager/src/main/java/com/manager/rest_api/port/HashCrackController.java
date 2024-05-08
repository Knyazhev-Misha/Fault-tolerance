package com.manager.rest_api.port;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manager.rest_api.api.HashCrackService;
import com.manager.rest_api.api.dto.ManagerDTO;
import com.manager.rest_api.api.dto.inside.CaseDTO;
import com.manager.rest_api.api.dto.inside.CrackRequestInsideDTO;
import com.manager.rest_api.api.dto.outside.CrackOutsideResponseDTO;
import com.manager.rest_api.impl.domain.service.HashCrackDomain;

import static com.manager.rest_api.port.HashCrackUrl.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class HashCrackController {
    
    private final HashCrackService hashCrackService;

    @PostMapping(HASH_CRACK_URL)
    public ResponseEntity<ManagerDTO> crackHash(@RequestBody CrackRequestInsideDTO requestDTO) {
        ManagerDTO responseDTO = hashCrackService.SaveHashCrackTask(requestDTO, null);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(HASH_STATUS_URL)
    public ResponseEntity<ManagerDTO> getRequestStatus(@RequestParam String requestId) {
        ManagerDTO responseDTO = hashCrackService.GetStatusHashCrackTask(requestId);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(HASH_REQUEST_URL)
    public void getCrackHash(@RequestBody CrackOutsideResponseDTO requestDTO) {
        
        hashCrackService.SaveHashCrackTaskData(requestDTO);
    }

    @PostMapping(HASH_REQUEST_CASE_URL)
    public ResponseEntity<ManagerDTO> getCrackHash(@RequestBody CaseDTO requestDTO) {
        CrackRequestInsideDTO ptrDTO = HashCrackDomain.createCrackRequestInsideDTO(requestDTO);
        ManagerDTO responseDTO = hashCrackService.SaveHashCrackTask(ptrDTO, requestDTO.numCase());

        return ResponseEntity.ok(responseDTO);
    }

}
