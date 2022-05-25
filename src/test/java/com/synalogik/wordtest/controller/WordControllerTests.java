package com.synalogik.wordtest.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WordControllerTests {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void fileUploadTest() throws Exception{
        MockPart filePart = new MockPart("file", "TestTest", "Hello world & good morning. The date is 18/05/2016".getBytes());

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/file/analyzeFile").part(filePart);

        mockMvc.perform(multipartRequest.part(filePart))
                .andExpect(status().isOk());
    }

    @Test
    public void fileUploadTesIncorrectRequest() throws Exception{
        MockPart filePart = new MockPart("name", "TestTest", "Hello world & good morning. The date is 18/05/2016".getBytes());

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/file/analyzeFile").part(filePart);

        mockMvc.perform(multipartRequest.part(filePart))
                .andExpect(status().isBadRequest());
    }



}
