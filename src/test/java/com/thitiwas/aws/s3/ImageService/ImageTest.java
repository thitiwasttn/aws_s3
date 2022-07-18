package com.thitiwas.aws.s3.ImageService;

import com.thitiwas.aws.s3.ImageService.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest(classes = ImageServiceApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ImageTest {

    @Autowired
    private ImageService imageService;
    private String imageLocation = "E://download//rsz_1167.jpg";

    @Test
    public void uploadImageTest() throws IOException {
        File file = new File(imageLocation);
        String dataString = encodeFileToBase64Binary(file);
        String url = imageService.uploadFile(dataString, "testImage");
        log.debug("url :: {}", url);
    }

    public static String encodeFileToBase64Binary(File file) {
        String encodedFile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedFile = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedFile;
    }
}
