package org.challenge.controllers;

import org.challenge.TestHelperClass;
import org.challenge.repo.FileHandler;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

@AutoConfigureMockMvc
@SpringBootTest(classes = {PictureController.class, PingController.class})
public class PictureControllerTest {

    private static final PictureController testController = new PictureController();

    @BeforeEach
    void setUp() {
        FileHandler.init();
    }

    @Test
    void handleUploadPicture() {
        try {
            assertEquals(ResponseEntity.ok().build(),testController.handleUploadPicture(TestHelperClass.oneElementFileList()));
            assertTrue(FileHandler.getFileNames().contains("test.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void retrievePictureNames() {
        try {
            JSONObject jsonObject = new JSONObject(testController.retrievePictureNames());
            assertTrue(jsonObject.has("pictures"));
            assertTrue(jsonObject.toString().contains("test.jpg"));
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void retrievePicturesFromRange() {
    }

    @Test
    void removePicture() {
    }

    @Test
    void getPicture() {
    }
}