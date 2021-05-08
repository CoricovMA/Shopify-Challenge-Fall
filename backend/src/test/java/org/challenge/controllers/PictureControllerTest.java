package com.example.testingweb;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.challenge.controllers.PictureController;
import org.challenge.controllers.PingController;
import org.challenge.repo.FileHandler;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest(classes = {PictureController.class, PingController.class})
public class PictureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void pingTest() throws Exception {
        this.mockMvc.perform(get("/ping")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("pong")));
    }


    // Just check if we get pictures, even if the list is empty
    @Test
    public void getAllPicturesTest() throws Exception {
        FileHandler.init();
        this.mockMvc.perform(get("/pictures")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"pictures\":")));
    }

}