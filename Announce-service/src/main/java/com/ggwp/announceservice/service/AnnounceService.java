package com.ggwp.announceservice.service;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.response.ResponseAnnounceDto;
import com.ggwp.announceservice.entity.Announce;
import com.ggwp.announceservice.exception.ErrorMsg;
import com.ggwp.announceservice.repository.AnnounceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnounceService {

    @Autowired
    private final AnnounceRepository announceRepository;

    //공지사항 작성 후 저장하기
    @Transactional
    public void writeAnnounce(RequestAnnounceDto dto) {
        Announce announce = dto.toEntity();
        announceRepository.save(announce);
    }

    //공지사항 수정하기
    @Transactional
    public void editAnnounce(Long aId, RequestAnnounceDto dto) {
        Announce announce = announceRepository.findById(aId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.ANNOUNCE_ID_NOT_FOUND));
        announce.updateAnnounce(dto);
        announceRepository.save(announce);
    }

    //공지사항 삭제하기
    @Transactional
    public void deleteAnnounce(Long aId) {
        Announce announce = announceRepository.findById(aId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.ANNOUNCE_ID_NOT_FOUND));
        announceRepository.delete(announce);
    }

    //공지사항 전체 조회하기
    public List<ResponseAnnounceDto> getAllAnnounce() {
        List<Announce> announceList = announceRepository.findAll();
        return announceList.stream()
                .map(ResponseAnnounceDto::fromEntity)
                .collect(Collectors.toList());
    }

    //공지사항 상세 조회하기
    public ResponseAnnounceDto getOneAnnounce(Long aId) {
        Announce announce = announceRepository.findById(aId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.ANNOUNCE_ID_NOT_FOUND));
        return ResponseAnnounceDto.fromEntity(announce);
    }
}
