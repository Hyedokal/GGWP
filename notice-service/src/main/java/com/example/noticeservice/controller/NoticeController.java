package com.example.noticeservice.controller;

import com.example.noticeservice.domain.Notice;
import com.example.noticeservice.dto.RequestCreateNoticeDto;
import com.example.noticeservice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notice-service")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @RequestMapping("/")
    public String defaultmapping(){
        return "notice-service is available!";
    }

    @PostMapping("notice")
    public ResponseEntity<?> createNotice(@RequestBody RequestCreateNoticeDto noticeDto){
        noticeService.createNotice(noticeDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findNoticeById(@PathVariable Long id){
        return ResponseEntity.ok(noticeService.findNoticeById(id));
    }

    @GetMapping("findall")
    public ResponseEntity<?> findAllNotice(){
        List<Notice> noticeList = noticeService.findAllNotice();
        return ResponseEntity.ok(noticeList);
    }

//    @GetMapping("users/findall")
//    public ResponseEntity<?> findAllUser(){
//        List<User> users = userService.findAllUser();
//        return ResponseEntity.ok(users);
//    }

}
