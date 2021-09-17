package org.challenge;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestHelperClass {

    public static List<MultipartFile> oneElementFileList() throws IOException {
        BufferedImage result = new BufferedImage(600, 300, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(result, "jpg", baos);
        MultipartFile multipartFile = new MockMultipartFile( "test.jpg", "test.jpg", null,baos.toByteArray());
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(multipartFile);
        return  fileList;
    }

}
