package com.example.noticeservice.service;

import com.example.noticeservice.domain.Notice;
import com.example.noticeservice.dto.RequestCreateNoticeDto;
import com.example.noticeservice.dto.ResponseFindNoticeDto;
import com.example.noticeservice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void createNotice(RequestCreateNoticeDto noticeDto){
        Notice notice = noticeDto.toEntity();
        noticeRepository.save(notice);
    }

    public ResponseFindNoticeDto findNoticeById(Long id){
        Notice notice = noticeRepository.findNoticeById(id);

        if(notice == null){
            throw new RuntimeException("알림이 존재하지 않습니다.");
        }
        return new ResponseFindNoticeDto(notice);
    }

    public List<Notice> findAllNotice(){
        return noticeRepository.findAll();
    }

}
