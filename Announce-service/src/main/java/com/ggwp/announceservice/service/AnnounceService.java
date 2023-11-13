package com.ggwp.announceservice.service;

import com.ggwp.announceservice.dto.RequestAnnounceDto;
import com.ggwp.announceservice.entity.Announce;
import com.ggwp.announceservice.repository.AnnounceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AnnounceService {

    @Autowired
    private final AnnounceRepository announceRepository;

    //공지사항 작성 후 저장하기

    //공지사항 수정하기
    @Transactional
    public void editAnnounce(Long aId, String aTitle, String aContent){
        Announce announce = announceRepository.findByAId(aId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID값의 공지사항이 존재하지 않습니다."));
        RequestAnnounceDto dto = new RequestAnnounceDto(aTitle, aContent);
    }

    //공지사항 삭제하기
    @Transactional
    public void deleteAnnounce(Long aId){
        Announce announce = announceRepository.findByAId(aId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID값의 공지사항이 존재하지 않습니다."));
        announceRepository.delete(announce);
    }

    //공지사항 전체 조회하기
    public List<Announce> getAllAnnounce(){
        return announceRepository.findAll();
    }
}
