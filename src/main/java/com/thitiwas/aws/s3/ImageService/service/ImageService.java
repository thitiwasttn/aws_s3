package com.thitiwas.aws.s3.ImageService.service;

import java.io.IOException;

public interface ImageService {
    String uploadFile(String base64, String keyName) throws IOException;
}
