package com.ggwp.noticeservice.controller;

import com.ggwp.noticeservice.common.dto.RequestDto;
import com.ggwp.noticeservice.common.dto.RequestStatusDto;
import com.ggwp.noticeservice.common.dto.ResponseDto;
import com.ggwp.noticeservice.common.dto.RequestFeignDto;
import com.ggwp.noticeservice.dto.RequestUpdateNoticeDto;
import com.ggwp.noticeservice.dto.ResponseNoticeDto;
import com.ggwp.noticeservice.service.NoticeService;
import com.ggwp.noticeservice.service.NoticeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/notice-service/v1/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // health-check
    @RequestMapping("/")
    public String defaultmapping(){
        return "notice-service is available!";
    }

    // 알림 생성 post
    @PostMapping("/create")
    public ResponseDto<String> createNotice(@Valid @RequestBody RequestFeignDto requestFeignDto) {
        noticeService.createNotice(requestFeignDto);
        return ResponseDto.success("Create Notice!");
    }

    @PostMapping("/get/all")
    public ResponseDto<List<ResponseNoticeDto>> getNoticeAll(@Valid @RequestBody RequestDto requestDto) {
        return ResponseDto.success(noticeService.getAllNoticeList(requestDto));
    }

    @PostMapping("/get/status/list")
    public ResponseDto<List<ResponseNoticeDto>> getNoticeByStatus(@Valid @RequestBody RequestStatusDto requestStatusDto) {
        return ResponseDto.success(noticeService.getNoticesByStatus(requestStatusDto));
    }

    @PutMapping("/update")
    public ResponseDto<?> updateNotice(@Valid @RequestBody RequestUpdateNoticeDto noticeDto){
        noticeService.update(noticeDto);
        return ResponseDto.success("알림이 업데이트 되었습니다.");
    }

}
