package com.synalogik.wordtest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@SpringBootTest
public class WordtestServiceTests {

    WordtestService wordtestService;

    @Test
    public void removeSpecialCharactersPositive(){
        wordtestService = new WordtestService();
        String string = "name.";
        string = wordtestService.removeSpecialCharacters(string);
        assertEquals("name", string);
    }

    @Test
    public void removeSpecialCharactersPreserveCharactersPositive(){
        wordtestService = new WordtestService();
        String string1 = "&";
        string1 = wordtestService.removeSpecialCharacters(string1);
        String string2 = "20/04/2020";
        string2 = wordtestService.removeSpecialCharacters(string2);
        assertEquals("&", string1);
        assertEquals("20/04/2020", string2);
    }

    @Test
    public void averageWordLengthPositive(){
        wordtestService = new WordtestService();
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,1);
        map.put(3,1);
        map.put(4,2);
        map.put(5,2);
        map.put(7,1);
        map.put(10,1);
        String s= wordtestService.averageWordLength(map);
        assertEquals("Average word length = 4.556",s);
    }

    @Test(expected = NumberFormatException.class)
    public void averageWordLengthNegative(){
        wordtestService = new WordtestService();
        Map<Integer,Integer> map = new HashMap<>();
        wordtestService.averageWordLength(map);
    }

    @Test
    public void numberOfWordsArrayPositive(){
        wordtestService = new WordtestService();
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,1);
        map.put(3,1);
        map.put(4,2);
        map.put(5,2);
        map.put(7,1);
        map.put(10,1);

        List<String> s = wordtestService.numberOfWordsArray(map);

        List<String> result = Arrays.asList("Number of words of length 1 is 1",
                "Number of words of length 2 is 1",
                "Number of words of length 3 is 1",
                "Number of words of length 4 is 2",
                "Number of words of length 5 is 2",
                "Number of words of length 7 is 1",
                "Number of words of length 10 is 1");

        assertEquals(s,result);
    }

    @Test
    public void mostFrequentWordLengthPositive(){
        wordtestService = new WordtestService();
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,1);
        map.put(3,1);
        map.put(4,2);
        map.put(5,2);
        map.put(7,1);
        map.put(10,1);

        String s = wordtestService.mostFrequentWordLength(map);
        assertEquals(s,"The most frequently occurring word length is 2, for word lengths of 4 & 5");
    }

    @Test
    public void analyzeFileContentsPositive(){
        wordtestService = new WordtestService();
        MockMultipartFile file
                = new MockMultipartFile(
                "TestTest",
                "TestTest.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello world & good morning. The date is 18/05/2016".getBytes()
        );

        List<String> s = wordtestService.analyzeFileContents(file);
        List<String> result = Arrays.asList("Word count = 9",
                "Average word length = 4.556",
                "Number of words of length 1 is 1",
                "Number of words of length 2 is 1",
                "Number of words of length 3 is 1",
                "Number of words of length 4 is 2",
                "Number of words of length 5 is 2",
                "Number of words of length 7 is 1",
                "Number of words of length 10 is 1",
                "The most frequently occurring word length is 2, for word lengths of 4 & 5");
        assertEquals(s,result);
    }

    @Test
    public void multipartToStringPositive(){
        wordtestService = new WordtestService();
        MockMultipartFile file
                = new MockMultipartFile(
                "TestTest",
                "TestTest.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello world & good morning. The date is 18/05/2016".getBytes()
        );

        List<String> s = wordtestService.multipartToString(file);

        List<String> result = Arrays.asList("Hello"
                ,"world"
                ,"&"
                ,"good"
                ,"morning"
                ,"The"
                ,"date"
                ,"is"
                ,"18/05/2016");

        assertEquals(s,result);
    }

    @Test(expected = RuntimeException.class)
    public void multipartToStringNoFileContentNegative(){
        wordtestService = new WordtestService();
        MockMultipartFile file
                = new MockMultipartFile(
                "TestTest",
                "TestTest.txt",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[0]
        );

        List<String> s = wordtestService.multipartToString(file);
    }
}
