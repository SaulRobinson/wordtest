package com.synalogik.wordtest.service;

import com.synalogik.wordtest.exception.EmptyFileException;
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
    // constructs the response in the format requested
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
    // works out the average word length of every word in the file. Due to checking file is not empty in the multipartToString() method
    // will not through NumberFormatException but it is tested for anyway
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
    // works out the number of words present in the file
    List<String> numberOfWordsArray(Map<Integer,Integer> wordCountWordLengthMap){
        List<String> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : wordCountWordLengthMap.entrySet()) {
            result.add("Number of words of length "+entry.getKey()+" is "+entry.getValue()+"");
        }
        return result;
    }
    // works out how many occurrences of the most frequent word lengths are present in the file
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

    //Converts the multipart file collected by the controller into string so the file can be analyzed checks if file is empty and throws exception if is.
    List<String> multipartToString(MultipartFile file){
        if(file.getSize() == 0){
            throw new EmptyFileException();
        }
        try{
            String[] result = new String(file.getBytes(), StandardCharsets.UTF_8).split(" ");
            return Arrays.stream(result).map(this::removeSpecialCharacters).collect(Collectors.toList());
        }catch (IOException e){
            throw new com.synalogik.wordtest.exception.IOException(e.getMessage());
        }
    }

    //Method removes special characters but keeps the / and & symbol
    String removeSpecialCharacters(String word){
        return word.replaceAll("[^a-zA-Z0-9/&]+","");
    }
}
