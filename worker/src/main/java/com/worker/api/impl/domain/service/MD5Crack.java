package com.worker.api.impl.domain.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;

import com.worker.additional_services.rabbitmq.service.RabbitMqSendService;
import com.worker.api.specification.dto.CrackHashManagerDoTaskRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MD5Crack {
    private final RabbitMqSendService rabbitMqSendService;
    
    public List<String> generateWords(int maxLength, CrackHashManagerDoTaskRequest requestDTO) {
        List<String> alphabet = startAlphabet(requestDTO);
        Iterator<List<String>> generator = Generator.permutation(alphabet).withRepetitions(maxLength).iterator();
        double maxInt = Math.ceil((double) Math.pow(alphabet.size(), requestDTO.maxLength())
                    / (double) requestDTO.partCount());

        String lastWord = lastWord(maxLength, alphabet, requestDTO);

        List<String> words = new ArrayList<>();
        int count = 0;
        double percent;

        while (generator.hasNext()) {
            count += 1;

            if (Thread.currentThread().isInterrupted()) {
                return words;
            }

            if(count % 10 == 0){
                percent = Math.min((int)((double)count / (double)maxInt * 100.0), 100);
                rabbitMqSendService.sendStatusTask(WorkerDomainService.createCrackHashWorkerStatus(requestDTO, percent, maxLength));
            }

            String word = String.join("", generator.next());
            String hash = encodeToMD5(word);

            if (lastWord.equals(word)) {
                return words;
            }

            if (requestDTO.hash().equals(hash)) {
                words.add(word);
                return words;
            }
        }

        return words;
    }

    private List<String> startAlphabet(CrackHashManagerDoTaskRequest requestDTO) {
        int startIdx = Math.min((int) Math.ceil((double) (requestDTO.alphabet().size() * (requestDTO.partNumber() - 1))
                / (double) requestDTO.partCount()), requestDTO.alphabet().size());

        List<String> newAlphabet = new ArrayList<>(requestDTO.alphabet());

        Collections.rotate(newAlphabet, -startIdx);

        return newAlphabet;
    }

    private static String lastWord(int maxLength, List<String> alphabet, CrackHashManagerDoTaskRequest requestDTO) {
        String lastSymbol = null;
        String lastWord = "";

        if (requestDTO.partCount() == requestDTO.partNumber()) {
            lastSymbol = requestDTO.alphabet().get(0);
        } 
        
        else {
            int maxIterInt = Math.min((int) Math.ceil((double) (alphabet.size() * requestDTO.partNumber())
                    / (double) requestDTO.partCount()), alphabet.size());

            lastSymbol = requestDTO.alphabet().get(maxIterInt);
        }

        for (int i = 0; i < maxLength; i += 1) {
            lastWord += lastSymbol;
        }

        return lastWord;
    }

    private String encodeToMD5(String word) {
        try {
            byte[] inputBytes = word.getBytes();

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(inputBytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }

        catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
