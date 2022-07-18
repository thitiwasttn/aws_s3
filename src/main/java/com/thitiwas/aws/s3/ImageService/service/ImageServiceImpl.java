package com.thitiwas.aws.s3.ImageService.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${filesystem.aws.bucketName}")
    private String fileSystemAwsBucketName;

    @Value("${filesystem.aws.accessKey}")
    private String fileSystemAwsAccessKey;

    @Value("${filesystem.aws.secretKey}")
    private String fileSystemAwsSecretKey;

    @Override
    public String uploadFile(String base64, String keyName) throws IOException {
        String finalKeyName = "images/" + keyName;
        String contentType = "image/jpeg";

        byte[] data = com.amazonaws.util.Base64.decode(base64);
        if (data.length > 5000000) {
            return "Error request data too long:" + data.length;
        }
        InputStream file = new ByteArrayInputStream(data);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(data.length);
        metadata.setContentType(contentType);


        return uploadFile(finalKeyName.concat(".jpg"), file, metadata);
    }

    private String uploadFile(String keyName, InputStream file, ObjectMetadata metadata) {
        PutObjectRequest putRequest = new PutObjectRequest(fileSystemAwsBucketName, keyName, file, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(fileSystemAwsAccessKey, fileSystemAwsSecretKey));
        s3client.putObject(putRequest);
        return s3client.getUrl(fileSystemAwsBucketName, keyName).toExternalForm();
    }
}
