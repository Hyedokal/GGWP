package com.ggwp.memberservice.controller;

import com.ggwp.memberservice.service.FileService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("member-service/file")
@RequiredArgsConstructor
public class FileController {



    private final FileService fileService;


@PostMapping("/upload")
public String upload(@RequestParam("file") MultipartFile file) {
    // AWS S3에 파일 업로드
    return fileService.uploadToS3(file);
}

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        Resource resource = fileService.getFileFromS3(fileName);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}

//    @PostMapping("/upload")
//    public String upload(
//            @RequestParam("file") MultipartFile file
//
//    ) {
//        String url = fileService.upload(file);
//        return url;
//    }
//
//    @GetMapping(value="{fileName}", produces={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
//    public Resource getFile(
//        @PathVariable("fileName") String fileName
//    ) {
//        Resource resource = fileService.getFile(fileName);
//        return resource;
//    }