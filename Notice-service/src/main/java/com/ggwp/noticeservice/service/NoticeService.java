package com.ggwp.noticeservice.service;


import com.ggwp.noticeservice.domain.Member;
import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.dto.RequestCreateMemberDto;
import com.ggwp.noticeservice.dto.RequestCreateNoticeDto;
import com.ggwp.noticeservice.dto.RequestUpdateNoticeDto;
import com.ggwp.noticeservice.dto.ResponseFindNoticeDto;
import com.ggwp.noticeservice.exception.NoticeNotFoundException;
import com.ggwp.noticeservice.repository.MemberRepository;
import com.ggwp.noticeservice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final MemberRepository memberRepository;  // 이후 feign으로 바꾸어야 함

    // 알림 생성 (보낸 사람, 받는 사람, 상태)
    @Transactional
    public void createNotice(RequestCreateNoticeDto noticeDto){
        // 알림 생성
        Notice notice = noticeDto.toEntity(noticeDto.getSenderId(), noticeDto.getReceiverId());
        // 알림 저장
        noticeRepository.save(notice);
    }
    @Transactional
    public void createMember(RequestCreateMemberDto memberDto){
        Member member = memberDto.toEntity();
        memberRepository.save(member);
    }

    // 알림 찾기(알림의 ID)
    public ResponseFindNoticeDto findNoticeById(Long id){
        Notice notice = noticeRepository.findNoticeById(id)
                .orElseThrow(() -> new NoticeNotFoundException("해당 ID값의 알림은 존재하지 않습니다."));

        return new ResponseFindNoticeDto(notice);
    }

    // 받는 사람으로 알림 찾기
    public List<Notice> findByReceiverId(Long receiverId) {
        List<Notice> noiceList = noticeRepository.findNoticeByReceiverId(receiverId);

        if(noiceList.isEmpty()){
            throw new NoticeNotFoundException("해당하는 receiverId값을 가진 알림이 존재하지 않습니다.");
        }
        return noiceList;
    }


    // 보낸 사람으로 알림 찾기
    public List<Notice> findBySenderId(Long senderId) {
        List<Notice> noticeList = noticeRepository.findNoticeBySenderId(senderId);

        if(noticeList.isEmpty()){
            throw new NoticeNotFoundException("해당하는 receiverId값을 가진 알림이 존재하지 않습니다.");
        }
        return noticeList;
    }

    @Transactional //알림 업데이트하기
    public void updateNotice(RequestUpdateNoticeDto updateNoticeDto){
        Notice notice = noticeRepository.findNoticeById(updateNoticeDto.getNoticeId())
                        .orElseThrow(() -> new NoticeNotFoundException("해당 ID값의 알림은 존재하지 않습니다."));
        notice.updateNotice(updateNoticeDto.getCode());
    }

    @Transactional //알림 삭제하기
    public void deleteNotice(Long id){
        Notice notice = noticeRepository.findNoticeById(id)
                .orElseThrow(() -> new NoticeNotFoundException("해당 ID값의 알림은 존재하지 않습니다."));
        noticeRepository.delete(notice);
    }

    // 전체 알림 조회 (관리자)
    public List<Notice> findAllNotice(){
        return noticeRepository.findAll();
    }

}
