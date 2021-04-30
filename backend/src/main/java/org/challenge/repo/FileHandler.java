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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    public static String getFileNamesJson(){
        return new JSONObject().put("pictures", new JSONArray(getFileNames())).toString();
    }

    public static void uploadFile(File file){
        String fileName = (getFileNames().contains(file.getName()))
                ? file.getName() + "_" + UUID.randomUUID()
                : file.getName();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(FileUtils.readFileToByteArray(file));
            fileOutputStream.flush();
            fileOutputStream.close();
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

    private static File retrieveFile(String name){
        return new File(Paths.get(String.format("%s%s%s", FOLDER_NAME, File.separator , name)).toString());
    }

    public static void uploadFiles(List<MultipartFile> files) {
        for(MultipartFile file : files){
            try(FileOutputStream fos = new FileOutputStream(file.getName())){
                fos.write(file.getBytes());
            } catch (IOException e) {
                log.error("Error uploading file {}.", file.getName());
            }
        }
    }
}
