package com.synalogik.wordtest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordService {

    public List<String> analyzeFileContents(MultipartFile file){
        List<String> content = multipartToString(file);
        List<String> result = new ArrayList<>();
        result.add("Word count = "+content.size());
        Map<Integer,Integer> wordCountWordLengthMap = new HashMap<>();
        for(String word: content){
            Integer wordLength = word.length();
            if(!wordLength.equals(0)&&!word.equals("\n")) {
                wordCountWordLengthMap.merge(wordLength, 1, Integer::sum);
            }
        }
        result.add(averageWordLength((wordCountWordLengthMap)));
        result.addAll(numberOfWordsArray(wordCountWordLengthMap));
        result.add(mostFrequentWordLength((wordCountWordLengthMap)));
        return result;
    }

    String averageWordLength(Map<Integer,Integer> wordCountWordLengthMap){
        double divideBy = 0;
        double divide = 0;
        for (Map.Entry<Integer, Integer> entry : wordCountWordLengthMap.entrySet()) {
            divideBy = divideBy + entry.getValue();
            int wordDivideCount = entry.getKey()*entry.getValue();
            divide = divide+wordDivideCount;
        }
        BigDecimal bd = new BigDecimal(Double.toString(divide/divideBy));
        return "Average word length = "+bd.setScale(3, RoundingMode.HALF_UP);
    }

    List<String> numberOfWordsArray(Map<Integer,Integer> wordCountWordLengthMap){
        List<String> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : wordCountWordLengthMap.entrySet()) {
            result.add("Number of words of length "+entry.getKey()+" is "+entry.getValue()+"");
        }
        return result;
    }

    String mostFrequentWordLength(Map<Integer,Integer> wordCountWordLengthMap){

        Integer highestOccurrenceCount = null;
        List<Integer> lengthsMatchingOccurrenceCount = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : wordCountWordLengthMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if(highestOccurrenceCount == null){
                highestOccurrenceCount = value;
                lengthsMatchingOccurrenceCount.add(key);
            } else if (highestOccurrenceCount.equals(value)){
                lengthsMatchingOccurrenceCount.add(key);
            } else if( highestOccurrenceCount < value){
                highestOccurrenceCount = value;
                lengthsMatchingOccurrenceCount = new ArrayList<>();
                lengthsMatchingOccurrenceCount.add(key);
            }
        }
        StringBuilder lengthsMatchingOccurrenceString = new StringBuilder();
        for(Integer integer: lengthsMatchingOccurrenceCount){
            lengthsMatchingOccurrenceString.append(" & ").append(integer);
        }

        return "The most frequently occurring word length is "+highestOccurrenceCount+", for word lengths of"+lengthsMatchingOccurrenceString.substring(2);
    }

    List<String> multipartToString(MultipartFile file){
        if(file.getSize() == 0){
            //TODO Error handling
            throw new RuntimeException();
        }
        try{
            String[] result = new String(file.getBytes(), StandardCharsets.UTF_8).split(" ");
            return Arrays.stream(result).map(this::removeSpecialCharacters).collect(Collectors.toList());
        }catch (IOException e){
            //TODO Error handling
            throw new RuntimeException();
        }
    }

    String removeSpecialCharacters(String word){
        return word.replaceAll("[^a-zA-Z0-9/&]+","");
    }
}
