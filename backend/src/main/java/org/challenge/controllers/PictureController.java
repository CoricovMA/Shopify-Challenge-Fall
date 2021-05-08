package org.challenge.controllers;

import lombok.extern.slf4j.Slf4j;
import org.challenge.repo.FileHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class PictureController {


    @PostMapping(value = "/upload")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity handleUploadPicture(List<MultipartFile> files){
        FileHandler.uploadFiles(files);

        return ResponseEntity
                .ok()
                .build();
    }

    /**
     *
     * @return JSON object containing all pictures
     */

    @GetMapping(value = "/pictures", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String retrievePictureNames(){
        return FileHandler.getFileNamesJson();
    }

    @GetMapping(value = "/pictures/{range}", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String retrievePicturesFromRange(@PathVariable int range){

        if(range < 0 || range > FileHandler.getFileNames().size()){
            return FileHandler.getFileNamesJson();
        }

        return FileHandler.getFileNamesJson(range);
    }


    @DeleteMapping(value = "/picture/{name}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Boolean> removePicture(@PathVariable String name){
        boolean deleted = FileHandler.removeFile(name);

        if(deleted){
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/picture/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte []> getPicture(@PathVariable String name){
        try{

            byte [] fileBytes = FileHandler.retrievePictureAsBytes(name);

            return ResponseEntity.ok(fileBytes);

        } catch (IOException e) {

            log.error("There was an issue retrieving {}", name);

            return ResponseEntity.notFound().build();

        }
    }
}
