package org.challenge.controllers;

import org.challenge.TestHelperClass;
import org.challenge.repo.FileHandler;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
            fail();
        }
    }

    @Test
    void retrievePictureNames() {
        handleUploadPicture();
        try {
            JSONObject jsonObject = new JSONObject(testController.retrievePictureNames());
            assertTrue(jsonObject.has("pictures"));
            assertTrue(jsonObject.toString().contains("test.jpg"));
        }catch (Exception e){
            fail();
        }
        removePicture();
    }

    @Test
    void retrievePicturesFromRange() {
    }

    @Test
    void removePicture() {
        assertEquals(ResponseEntity.ok(true),testController.removePicture("test.jpg"));
        assertFalse(FileHandler.getFileNames().contains("test.jpg"));
    }

    @Test
    void removeNonexistentPictureFails(){
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).build(), testController.removePicture("nonexisting picture"));
    }

    @Test
    void getPicture() {
        handleUploadPicture();
        ResponseEntity<byte []> res = testController.getPicture("test.jpg");
        assertTrue(res.hasBody());
        assertNotNull(res.getBody());
        removePicture();
    }

    @Test
    void getNonexistentPicture(){
        assertEquals(ResponseEntity.notFound().build(), testController.getPicture("some picture somewhere"));
    }
}