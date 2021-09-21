package org.challenge.repo;

import org.challenge.TestHelperClass;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {
    @Test
    void getFileNames() {
        FileHandler.init();
        assertNotNull(FileHandler.getFileNames());
    }

    @Test
    void getFileNamesJson() {
        try {
            JSONObject js = new JSONObject(FileHandler.getFileNamesJson());
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void uploadFile() {
        FileHandler.init();
        try {

            FileHandler.uploadFiles(TestHelperClass.oneElementFileList());

            assertTrue(FileHandler.getFileNames().contains("test.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void removeFile() {
        assertTrue(FileHandler.getFileNames().contains("test.jpg"));
        if(FileHandler.getFileNames().contains("test.jpg")){
            FileHandler.removeFile("test.jpg");
        }
        assertFalse(FileHandler.getFileNames().contains("test.jpg"));
    }

    @Test
    void uploadFiles() {
    }
}