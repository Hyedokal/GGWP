package com.ggwp.memberservice.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
//    String upload(MultipartFile file);
//    Resource getFile(String fileName);

    String uploadToS3(MultipartFile file);

    Resource getFileFromS3(String fileName);
}
