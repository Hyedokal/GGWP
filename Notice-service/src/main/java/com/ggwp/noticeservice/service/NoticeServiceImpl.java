package com.ggwp.noticeservice.service;


import com.ggwp.noticeservice.common.dto.RequestDto;
import com.ggwp.noticeservice.common.dto.RequestFeignDto;
import com.ggwp.noticeservice.common.dto.RequestStatusDto;
import com.ggwp.noticeservice.common.enums.MessageEnum;
import com.ggwp.noticeservice.common.enums.NoticeEnum;
import com.ggwp.noticeservice.common.exception.CustomException;
import com.ggwp.noticeservice.common.exception.ErrorCode;
import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.domain.QNotice;
import com.ggwp.noticeservice.dto.*;
import com.ggwp.noticeservice.feign.NoticeToCommentFeign;
import com.ggwp.noticeservice.feign.NoticeToSquadFeign;
import com.ggwp.noticeservice.repository.NoticeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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

    private final EntityManager entityManager;

    // 알림 생성 (보낸 사람, 받는 사람, 상태)

    public Notice createNotice(RequestFeignDto requestFeignDto){
        ResponseCommentDto responseCommentDto = commentFeign.getComment(requestFeignDto.getCid());
        ResponseSquadDto responseSquadDto = squadFeign.getOneSquad(responseCommentDto.getSId());
        Notice notice = Notice.builder()
                .senderName(responseCommentDto.getSummonerName())
                .senderTag(responseCommentDto.getTagLine())
                .status(NoticeEnum.UNREAD)
                .msg(MessageEnum.APPLY.getMessage())
                .receiverName(responseSquadDto.getSummonerName())
                .receiverTag(responseSquadDto.getTagLine())
                .build();
        // 알림 저장
        notice = noticeRepository.save(notice);
        alarmService.alarmByMessage(noticeToDto(notice));

        return notice;
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
    public List<ResponseNoticeDto> getAllNoticeList(RequestDto requestDto){
        List<Notice> noticeList = findNoticeList(requestDto);
               return noticeListToDtoList(noticeList);
    }

    @Transactional(readOnly = true)
    public List<ResponseNoticeDto> getNoticesByStatus(RequestStatusDto requestStatusDto) {
        QNotice qNotice = QNotice.notice;

        // String 값을 NoticeEnum으로 변환
        NoticeEnum noticeEnum = NoticeEnum.valueOf(requestStatusDto.getStatus().toUpperCase());

        // QueryDSL을 사용하여 특정 조건에 맞는 Notice 리스트 가져오기
        List<Notice> notices = new JPAQueryFactory(entityManager)
                .selectFrom(qNotice)
                .where(
                        qNotice.receiverName.eq(requestStatusDto.getReceiverName())
                                .and(qNotice.receiverTag.eq(requestStatusDto.getReceiverTag()))
                                .and(qNotice.status.eq(noticeEnum))
                )
                .fetch();

        // Notice 엔터티를 ResponseNoticeDto로 변환
        return notices.stream()
                .map(this::noticeToDto)
                .toList();
    }

    private List<ResponseNoticeDto> noticeListToDtoList(List<Notice> noticeList) {
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

    private List<Notice> findNoticeList(RequestDto requestDto){
        return noticeRepository.findNoticesByReceiverNameAndReceiverTag(requestDto.getReceiverName(),requestDto.getReceiverTag())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFeginException));
    }
    public void update(RequestUpdateNoticeDto noticeDto){
        Notice notice = findNotice(noticeDto.getNoticeId());
        notice.update(noticeDto.getCode());
        noticeRepository.save(notice);
        alarmService.alarmByMessage(noticeToDto(notice));
    }

}
