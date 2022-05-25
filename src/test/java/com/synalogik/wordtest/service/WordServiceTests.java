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
public class WordServiceTests {

    WordService wordService;

    @Test
    public void removeSpecialCharactersPositive(){
        wordService = new WordService();
        String string = "name.";
        string = wordService.removeSpecialCharacters(string);
        assertEquals("name", string);
    }

    @Test
    public void removeSpecialCharactersPreserveCharactersPositive(){
        wordService = new WordService();
        String string1 = "&";
        string1 = wordService.removeSpecialCharacters(string1);
        String string2 = "20/04/2020";
        string2 = wordService.removeSpecialCharacters(string2);
        assertEquals("&", string1);
        assertEquals("20/04/2020", string2);
    }

    @Test
    public void averageWordLengthPositive(){
        wordService = new WordService();
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,1);
        map.put(3,1);
        map.put(4,2);
        map.put(5,2);
        map.put(7,1);
        map.put(10,1);
        String s= wordService.averageWordLength(map);
        assertEquals("Average word length = 4.556",s);
    }

    @Test(expected = NumberFormatException.class)
    public void averageWordLengthNegative(){
        wordService = new WordService();
        Map<Integer,Integer> map = new HashMap<>();
        wordService.averageWordLength(map);
    }

    @Test
    public void numberOfWordsArrayPositive(){
        wordService = new WordService();
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,1);
        map.put(3,1);
        map.put(4,2);
        map.put(5,2);
        map.put(7,1);
        map.put(10,1);

        List<String> s = wordService.numberOfWordsArray(map);

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
        wordService = new WordService();
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,1);
        map.put(3,1);
        map.put(4,2);
        map.put(5,2);
        map.put(7,1);
        map.put(10,1);

        String s = wordService.mostFrequentWordLength(map);
        assertEquals(s,"The most frequently occurring word length is 2, for word lengths of 4 & 5");
    }

    @Test
    public void multipartToStringPositive(){
        wordService = new WordService();
        MockMultipartFile file
                = new MockMultipartFile(
                "TestTest",
                "TestTest.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello world & good morning. The date is 18/05/2016".getBytes()
        );

        List<String> s = wordService.multipartToString(file);

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
        wordService = new WordService();
        MockMultipartFile file
                = new MockMultipartFile(
                "TestTest",
                "TestTest.txt",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[0]
        );

        List<String> s = wordService.multipartToString(file);
    }
}
