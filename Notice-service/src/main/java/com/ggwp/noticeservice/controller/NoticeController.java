package com.ggwp.noticeservice.controller;

import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.dto.RequestCreateMemberDto;
import com.ggwp.noticeservice.dto.RequestCreateNoticeDto;
import com.ggwp.noticeservice.dto.RequestUpdateNoticeDto;
import com.ggwp.noticeservice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v1/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // health-check
    @RequestMapping("/")
    public String defaultmapping(){
        return "notice-service is available!";
    }

    // 알림 테스트용 MEMBER 생성
    @PostMapping("member")
    public ResponseEntity<?> createMember(@RequestBody RequestCreateMemberDto memberDto){
        noticeService.createMember(memberDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    // 알림 생성 post
    @PostMapping("notice")
    public ResponseEntity<?> createNotice(@RequestBody RequestCreateNoticeDto noticeDto){
        noticeService.createNotice(noticeDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    // 알림 조회(찾기) @PathVariable Long id
    @GetMapping("{id}")
    public ResponseEntity<?> findNoticeById(@PathVariable Long id){
        return ResponseEntity.ok(noticeService.findNoticeById(id));
    }

    // 알림 조회(찾기) sender의 아이디로 알림 찾기
    @GetMapping("{senderId}/sender")
    public ResponseEntity<?> findBySenderId(@PathVariable Long senderId){
        List<Notice> notice = noticeService.findBySenderId(senderId);
        return ResponseEntity.ok(notice);
    }

    // 알림 조회(찾기) receiver의 아이디로 알림 찾기
    @GetMapping("{receiverId}/receiver")
    public ResponseEntity<?> findByReceiverId(@PathVariable Long receiverId){
        List<Notice> noticeList = noticeService.findByReceiverId(receiverId);
        return ResponseEntity.ok(noticeList);
    }

    // 알림 전체 찾기
    @GetMapping("findall")
    public ResponseEntity<?> findAllNotice(){
        List<Notice> noticeList = noticeService.findAllNotice();
        return ResponseEntity.ok(noticeList);
    }

    @PutMapping("update")
    public ResponseEntity<?> readNotice(@RequestBody RequestUpdateNoticeDto updateNoticeDto){
        noticeService.updateNotice(updateNoticeDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id){
        noticeService.deleteNotice(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
