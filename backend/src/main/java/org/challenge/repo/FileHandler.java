package org.challenge.repo;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.exceptions.TemplateAssertionException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FileHandler {

    private static final String FOLDER_NAME = "pictures";
    private static File FOLDER;
    private static final int THUMBNAIL_HEIGHT = 200;
    private static final int THUMBNAIL_WIDTH = 300;

    private FileHandler(){
        init();
    }

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
        verifyFolder();
    }

    private static void verifyFolder() {
        getFileNames().parallelStream().forEach(fileName ->{
            if(!getFileNames().contains(createThumbnailName(fileName)) && !fileName.contains("-thumbnail")){
                try {
                    Thumbnails.of(filePath(fileName))
                            .size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                            .toFiles(FOLDER, Rename.SUFFIX_HYPHEN_THUMBNAIL );
                } catch (IOException e) {
                    log.warn("There was an issue creating a thumbnail while verifying existing files.\n{}", e.getMessage());
                }
            }
        });
    }

    public static List<String> getFileNames(){
        return Arrays.asList(Objects.requireNonNull(FOLDER.list()));
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

    public static void uploadFile(MultipartFile file) throws IOException {
        String fileNameToWrite = retrieveNameFromFile(file);

        createThumbnailFromMultipartFile(file);

        try(FileOutputStream fileOutputStream = new FileOutputStream(filePath(fileNameToWrite))) {
            fileOutputStream.write(file.getBytes());
            fileOutputStream.flush();
        } catch (IOException e) {
            log.warn("Something went wrong while creating file. {}", e.getMessage());
        }
    }

    private static String createThumbnailName(String givenFileName){
        return givenFileName.replace(".", "-thumbnail.");
    }

    private static void createThumbnailFromMultipartFile(MultipartFile file) throws IOException {
        String fileNameToWrite = retrieveNameFromFile(file);
        String [] name = fileNameToWrite.split("\\.");
        String thumbnailName = String.format("%s-thumbnail.%s", name[0], name[1]);
        createThumbnail(file.getInputStream(), thumbnailName, name[1]);
    }

    private static void createThumbnail(InputStream givenStream, String fileName, String fileFormat) throws IOException {
        BufferedImage inputImage = ImageIO.read(givenStream);

        BufferedImage thumbnailImage = Thumbnails.of(inputImage)
                .size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                .asBufferedImage();

        ImageIO.write(thumbnailImage, fileFormat, new File(filePath(fileName)));
    }

    public static synchronized byte[] retrievePictureAsBytes(String pictureName) throws IOException {
        File f = retrieveFile(pictureName);
        return FileUtils.readFileToByteArray(f);
    }

    public static boolean removeFile(String fileName){
        File f = retrieveFile(fileName);

        if(fileName.contains("-thumbnail-thumbnail"))
            return true;
        if(f.exists() && getFileNames().contains(fileName)){{
            return f.delete() && removeFile(createThumbnailName(fileName));
        }}

        return false;
    }
    
    private static String retrieveNameFromFile(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String type = (originalFilename.contains(".")) ? originalFilename.split("\\.")[1] : ".png";
        String fileName = (originalFilename.contains(".")) ? originalFilename.split("\\.")[0] : originalFilename;

        return  (getFileNames().contains(originalFilename))
                ? String.format("%s_%s.%s", fileName, UUID.randomUUID(), type)
                : originalFilename;
    }

    private static String filePath(String name){
        return String.format("%s%s%s", FOLDER_NAME, File.separator, name);
    }

    private static File retrieveFile(String name){
        return new File(Paths.get(String.format("%s%s%s", FOLDER_NAME, File.separator , name)).toString());
    }

    public static synchronized void uploadFiles(List<MultipartFile> files) throws IOException {
        for(MultipartFile file : files){
            uploadFile(file);
        }
    }
}
