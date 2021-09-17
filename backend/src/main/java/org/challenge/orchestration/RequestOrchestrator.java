package org.challenge.orchestration;

import lombok.extern.slf4j.Slf4j;
import org.challenge.repo.FileHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class RequestOrchestrator {

    private static final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    private RequestOrchestrator(){
        throw new AssertionError();
    }

    public static Future<ResponseEntity<byte []>> getPicture(String fileName){
        return threadPool.submit(() -> {

            byte [] fileBytes = FileHandler.retrievePictureAsBytes(fileName);

            return ResponseEntity.ok(fileBytes);

        });
    }

    public static Future<ResponseEntity> uploadPicture(List<MultipartFile> fileList){
        return threadPool.submit(() ->{

            FileHandler.uploadFiles(fileList);
            return ResponseEntity.ok().build();

        });
    }

}
