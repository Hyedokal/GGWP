package com.ggwp.noticeservice.controller;

import com.ggwp.noticeservice.common.dto.RequestDto;
import com.ggwp.noticeservice.common.dto.RequestStatusDto;
import com.ggwp.noticeservice.common.dto.ResponseDto;
import com.ggwp.noticeservice.common.dto.RequestFeignDto;
import com.ggwp.noticeservice.dto.RequestUpdateNoticeDto;
import com.ggwp.noticeservice.dto.ResponseNoticeDto;
import com.ggwp.noticeservice.service.NoticeService;
import com.ggwp.noticeservice.service.NoticeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/notice-service/v1/notice")
@RequiredArgsConstructor
@Tag(name = "Notice", description = "Notice API")
public class NoticeController {

    private final NoticeService noticeService;

    // health-check
    @RequestMapping("/")
    @Operation(summary = "알람 Health-check", description = "알림 헬스 체크 엔드포인트")
    public String defaultmapping(){
        return "notice-service is available!";
    }

    // 알림 생성 post
    @PostMapping("/create")
    @Operation(summary = "알람 생성", description = "알림 생성 - 프론트에서 값을 보낸다.")
    public ResponseDto<String> createNotice(@Valid @RequestBody RequestFeignDto requestFeignDto) {
        noticeService.createNotice(requestFeignDto);
        return ResponseDto.success("Create Notice!");
    }

    @PostMapping("/get/all")
    @Operation(summary = "알람 전체 가져오기", description = "관리자입장에서 알림 전체 가져오기")
    public ResponseDto<List<ResponseNoticeDto>> getNoticeAll(@Valid @RequestBody RequestDto requestDto) {
        return ResponseDto.success(noticeService.getAllNoticeList(requestDto));
    }

    @PostMapping("/get/status/list")
    @Operation(summary = "알림 상태에 따른 알림 분류해서 가져오기", description = "상태별로 알림 히스토리 가져오기")
    public ResponseDto<List<ResponseNoticeDto>> getNoticeByStatus(@Valid @RequestBody RequestStatusDto requestStatusDto) {
        return ResponseDto.success(noticeService.getNoticesByStatus(requestStatusDto));
    }

    @PutMapping("/update")
    @Operation(summary = "알림 업데이트할 때 사용", description = "코드 번호를 같이줘서 업데이트 할 때 사용")
    public ResponseDto<?> updateNotice(@Valid @RequestBody RequestUpdateNoticeDto noticeDto){
        noticeService.update(noticeDto);
        return ResponseDto.success("알림이 업데이트 되었습니다.");
    }

}
