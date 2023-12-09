package com.ggwp.noticeservice.controller;

import com.ggwp.noticeservice.common.dto.RequestDto;
import com.ggwp.noticeservice.common.dto.ResponseDto;
import com.ggwp.noticeservice.common.dto.RequestFeignDto;
import com.ggwp.noticeservice.dto.RequestUpdateNoticeDto;
import com.ggwp.noticeservice.dto.ResponseNoticeDto;
import com.ggwp.noticeservice.service.NoticeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v1/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeServiceImpl noticeService;

    // health-check
    @RequestMapping("/")
    public String defaultmapping(){
        return "notice-service is available!";
    }

    // 알림 생성 post
    @PostMapping("/create") // 매치 5개 api 불러와서 저장
    public ResponseDto<String> createNotice(@Valid @RequestBody RequestFeignDto requestFeignDto) {
        noticeService.createNotice(requestFeignDto);
        return ResponseDto.success("Create Notice!");
    }

    @PostMapping("/get")
    public ResponseDto<List<ResponseNoticeDto>> getNotice(@Valid @RequestBody RequestDto requestDto) {
        return ResponseDto.success(noticeService.getNoticeList(requestDto));
    }

    @PutMapping("/update")
    public ResponseDto<?> updateNotice(@Valid @RequestBody RequestUpdateNoticeDto noticeDto){
        noticeService.update(noticeDto);
        return ResponseDto.success("알림이 업데이트 되었습니다.");
    }

}
