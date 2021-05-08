package org.challenge.repo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class FileHandler {

    private static final String FOLDER_NAME = "pictures";
    private static File FOLDER;

    public static void init(){
        if(!Files.isDirectory(Paths.get(FOLDER_NAME))){

            try {

                Files.createDirectories(Paths.get(FOLDER_NAME));

            } catch (IOException e) {

                log.warn("Failed to create directory. {}", e.getMessage());
                return;

            }
        }

        FOLDER = Paths.get(FOLDER_NAME).toFile();
    }

    public static List<String> getFileNames(){
        return Arrays.asList(FOLDER.list());
    }

    public static void setFolder(File folder){
        FOLDER = folder;
    }

    public static String getFileNamesJson(){
        return getFileNamesJson(getFileNames().size());
    }

    public static String getFileNamesJson(int range){
        return new JSONObject().put("pictures", new JSONArray(getFileNames().subList(0, range))).toString();
    }

    public static void uploadFile(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String type = (originalFilename.contains(".")) ? originalFilename.split("\\.")[1] : ".png";
        String fileName = (originalFilename.contains(".")) ? originalFilename.split("\\.")[0] : originalFilename;

        String fileNameToWrite = (getFileNames().contains(originalFilename))
                ? String.format("%s_%s.%s", fileName, UUID.randomUUID(), type)
                : originalFilename;

        try(FileOutputStream fileOutputStream = new FileOutputStream(filePath(fileNameToWrite))) {
            fileOutputStream.write(file.getBytes());
            fileOutputStream.flush();
        } catch (IOException e) {
            log.warn("Something went wrong while creating file. {}", e.getMessage());
        }
    }

    public static byte[] retrievePictureAsBytes(String pictureName) throws IOException {
        File f = retrieveFile(pictureName);
        return FileUtils.readFileToByteArray(f);
    }

    public static boolean removeFile(String fileName){
        File f = retrieveFile(fileName);

        if(f.exists() && getFileNames().contains(fileName)){{
            return f.delete();
        }}

        return false;
    }

    private static String filePath(String name){
        return String.format("%s%s%s", FOLDER_NAME, File.separator, name);
    }

    private static File retrieveFile(String name){
        return new File(Paths.get(String.format("%s%s%s", FOLDER_NAME, File.separator , name)).toString());
    }

    public static void uploadFiles(List<MultipartFile> files) {
        for(MultipartFile file : files){
            uploadFile(file);
        }
    }
}
