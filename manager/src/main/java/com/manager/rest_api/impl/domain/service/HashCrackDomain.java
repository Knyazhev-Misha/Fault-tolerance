package com.manager.rest_api.impl.domain.service;

import com.manager.rest_api.api.dto.ManagerDTO;
import com.manager.rest_api.api.dto.inside.CaseDTO;
import com.manager.rest_api.api.dto.inside.CrackRequestInsideDTO;
import com.manager.rest_api.api.dto.inside.ManagerStatusResponseDTO;
import com.manager.rest_api.api.dto.outside.CrackOutsideRequestDTO;
import com.manager.mongo.HashCrackTasksDocument;
import static com.manager.rest_api.impl.domain.HashCrackEnum.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HashCrackDomain {
    public static ManagerDTO createManagerStatusResponseDTO(String status){
        return new ManagerStatusResponseDTO(status);
    }

    public static CrackRequestInsideDTO createCrackRequestInsideDTO(CaseDTO requestDTO){
        return new CrackRequestInsideDTO(
            requestDTO.hash(),
            requestDTO.maxLength()
        );
    }

    public static HashCrackTasksDocument createHashCrackTasksDocument(String requestId, CrackRequestInsideDTO requestDTO){
        return new HashCrackTasksDocument(
            requestId,
            requestDTO.maxLength(),
            requestDTO.hash(),
            new ArrayList<>(),
            createListStatus(task_part_count, task_status_in_progress),
            createListPercent(task_part_count, 0),
            task_part_count,
            createListStatus(task_part_count, task_send_status_false),
            LocalDateTime.now()
        );
    }

    private static List<String> createListStatus(int length, String status){
        List<String> listStatus = new ArrayList<>();
        
        for(int i = 0; i < length; i += 1){
            listStatus.add(status);
        }

        return listStatus;
    }

    private static List<Integer> createListPercent(int length, int persent){
        List<Integer> listStatus = new ArrayList<>();
        
        for(int i = 0; i < length; i += 1){
            listStatus.add(persent);
        }

        return listStatus;
    }

    /* 
    public static CrackOutsideRequestDTO createCrackOutsideRequestDTO(String requestId, CrackRequestInsideDTO requestDTO){
        return new CrackOutsideRequestDTO(
            requestId,
            0,
            count_worker,
            requestDTO.hash(),
            requestDTO.maxLength(),
            Alphabet.toList(Alphabet.alphabet, Alphabet.number)            
        );
    }
    */

    public static CrackOutsideRequestDTO createCrackOutsideRequestDTO(HashCrackTasksDocument document, int partNumber, Integer numCase){
        return new CrackOutsideRequestDTO(
            document.getRequestId(),
            partNumber,
            document.getPartCount(),
            document.getHash(),
            document.getMaxLength(),
            Alphabet.toList(Alphabet.alphabet, Alphabet.number),
            numCase         
        );
    }

    /*
    public static CrackOutsideRequestDTO createCrackOutsideRequestDTO(CrackOutsideRequestDTO requestDTO, int partNumber){
        return new CrackOutsideRequestDTO(
            requestDTO.requestId(),
            partNumber,
            task_part_count,
            requestDTO.hash(),
            requestDTO.maxLength(),
            Alphabet.toList(Alphabet.alphabet, Alphabet.number)            
        );
    }
    */
}
