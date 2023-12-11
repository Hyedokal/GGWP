package com.ggwp.noticeservice.service;


import com.ggwp.noticeservice.common.dto.RequestDto;
import com.ggwp.noticeservice.common.dto.RequestFeignDto;
import com.ggwp.noticeservice.common.enums.MessageEnum;
import com.ggwp.noticeservice.common.enums.NoticeEnum;
import com.ggwp.noticeservice.common.exception.CustomException;
import com.ggwp.noticeservice.common.exception.ErrorCode;
import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.dto.*;
import com.ggwp.noticeservice.feign.NoticeToCommentFeign;
import com.ggwp.noticeservice.feign.NoticeToSquadFeign;
import com.ggwp.noticeservice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final NoticeToCommentFeign commentFeign;
    private final NoticeToSquadFeign squadFeign;

    private final AlarmService alarmService;

    // 알림 생성 (보낸 사람, 받는 사람, 상태)

    public void createNotice(RequestFeignDto requestFeignDto){
        ResponseCommentDto responseCommentDto = commentFeign.getComment(requestFeignDto.getCId());
        ResponseSquadDto responseSquadDto = squadFeign.getOneSquad(requestFeignDto.getSId());
        Notice notice = Notice.builder()
                .senderName(responseSquadDto.getSummonerName())
                .senderTag(responseSquadDto.getTagLine())
                .status(NoticeEnum.UNREAD)
                .msg(MessageEnum.APPLY)
                .receiverName(responseCommentDto.getSummonerName())
                .receiverTag(responseCommentDto.getTagLine())
                .build();
        // 알림 저장
        noticeRepository.save(notice);
        alarmService.alarmByMessage(noticeToDto(notice));
    }

    private ResponseNoticeDto noticeToDto(Notice notice){
        return ResponseNoticeDto.builder()
                .id(notice.getId())
                .status(notice.getStatus())
                .senderName(notice.getSenderName())
                .senderTag(notice.getSenderTag())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .receiverName(notice.getReceiverName())
                .receiverTag(notice.getReceiverTag())
                .msg(notice.getMsg())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ResponseNoticeDto> getNoticeList(RequestDto requestDto){
        List<Notice> noticeList = noticeRepository.findNoticesByReceiverNameAndReceiverTag(requestDto.getRecceiverName(),requestDto.getTagLine())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFeginException));
        List<ResponseNoticeDto> findNoticeDtoList = new ArrayList<>();
        for (Notice notice : noticeList) {
            findNoticeDtoList.add(noticeToDto(notice));
        }
        return findNoticeDtoList;
    }

    public Notice findNotice(Long id){
        return noticeRepository.findNoticeById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFindNotice));
    }
    public void update(RequestUpdateNoticeDto noticeDto){
        Notice notice = findNotice(noticeDto.getNoticeId());
        notice.update(noticeDto.getCode());
        noticeRepository.save(notice);
    }

}
