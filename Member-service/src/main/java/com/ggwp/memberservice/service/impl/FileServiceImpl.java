package com.ggwp.memberservice.service.impl;



import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ggwp.memberservice.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


//
//    @Value("${file.path}")
//    private String filePath;
//    @Value("${file.url}")
//    private String fileUrl;

    public FileServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

//    @Override
//    public String upload(MultipartFile file) {
//        if (file.isEmpty()) return null; // description: 빈 파일인지 검증 //
//        String originalFileName = file.getOriginalFilename();// description: 원본 파일명 //
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));// description: 파일 확장자 구하기 //
//        String uuid = UUID.randomUUID().toString();// description: UUID 형식의 임의의 파일명 생성 //
//        String saveFileName = uuid + extension;// description: UUID 형식의 파일명 + 확장자로 새로운 파일명 생성 //
//        String savePath = filePath + saveFileName;// description: 저장할 경로 생성 //
//        try {
//            file.transferTo(new java.io.File(savePath)); // description: 파일 저장 //
//        } catch (Exception exception) {
//            exception.printStackTrace(); // description: 파일을 불러올 수 있는 경로 반환 //
//            return null;
//        }
//        String url = fileUrl + saveFileName; // description: 파일을 불러올 수 있는 경로 반환 //
//        return url;
//
//    }
//
//
//
//    @Override
//    public Resource getFile(String fileName) {
//        Resource resource = null;
//
//        try {
//            resource = new UrlResource("file:" + filePath + fileName);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return null;
//        }
//
//        return resource;
//
//    }
@Override
public String uploadToS3(MultipartFile file) {
    if (file.isEmpty()) return null;

    String originalFileName = file.getOriginalFilename();
    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
    String uuid = UUID.randomUUID().toString();
    String keyName = uuid + extension;

    try {
        s3Client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), null));
        // 로컬 서버 URL 형식으로 반환
        return "http://localhost:8000/member-service/file/" + keyName;
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}


    @Override
    public Resource getFileFromS3(String fileName) {
        try {
            // Get an object representing the file from S3
            S3Object s3Object = s3Client.getObject(bucketName, fileName);

            // Return a resource representing the S3 object
            return new InputStreamResource(s3Object.getObjectContent());
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
