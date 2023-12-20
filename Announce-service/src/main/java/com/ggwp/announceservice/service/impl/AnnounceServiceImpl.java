package com.ggwp.announceservice.service.impl;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.request.RequestPageDto;
import com.ggwp.announceservice.dto.response.ResponseAnnounceDto;
import com.ggwp.announceservice.entity.Announce;
import com.ggwp.announceservice.entity.QAnnounce;
import com.ggwp.announceservice.exception.ErrorMsg;
import com.ggwp.announceservice.repository.AnnounceRepository;
import com.ggwp.announceservice.service.AnnounceService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnounceServiceImpl implements AnnounceService {

    private final AnnounceRepository announceRepository;
    private final EntityManager entityManager;


        //공지사항 작성 후 저장하기
        public Announce writeAnnounce(RequestAnnounceDto dto) {
            Announce announce = dto.toEntity();
            return announceRepository.save(announce);
        }

    //공지사항 수정하기
    public Announce editAnnounce(Long aId, RequestAnnounceDto dto) {
        Announce announce = announceRepository.findById(aId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.ANNOUNCE_ID_NOT_FOUND));
        announce.updateAnnounce(dto);
        return announceRepository.save(announce);
    }

    //공지사항 삭제하기
    public void deleteAnnounce(Long aId) {
        Announce announce = announceRepository.findById(aId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.ANNOUNCE_ID_NOT_FOUND));
        announceRepository.delete(announce);
    }

    //공지사항 전체 조회하기
    @Transactional(readOnly = true)
    public List<ResponseAnnounceDto> getAllAnnounce() {
        List<Announce> announceList = announceRepository.findAll();
        return announceList.stream()
                .map(ResponseAnnounceDto::fromEntity)
                .toList();
    }

    //공지사항 상세 조회하기
    @Transactional(readOnly = true)
    public ResponseAnnounceDto getOneAnnounce(Long aId) {
        Announce announce = announceRepository.findById(aId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.ANNOUNCE_ID_NOT_FOUND));
        return ResponseAnnounceDto.fromEntity(announce);
    }

    //페이징 처리된 공지사항 조회하기
    public Page<ResponseAnnounceDto> searchPagedAnnounce(RequestPageDto.Search dto) {
        QAnnounce qAnnounce = QAnnounce.announce;
        BooleanBuilder where = where();
        if (dto.getTitle() != null) {   //제목 검색 기능 추가.
            where.and(qAnnounce.title.contains(dto.getTitle()));
        }
        Long total = query().select(qAnnounce.count()) //SQL의 count(*) 값을 구하는 것과 같다.
                .from(qAnnounce)
                .where(where)
                .fetchOne();
        total = (total == null) ? 0 : total;
        List<ResponseAnnounceDto> dtoList = new ArrayList<>();

        if (total > 0) {
            dtoList = query().select(qAnnounce)
                    .from(qAnnounce)
                    .where(where)
                    .orderBy(qAnnounce.id.desc())
                    .offset((long) dto.getPage() * dto.getSize())
                    .limit(dto.getSize())
                    .fetch()
                    .stream()
                    .map(ResponseAnnounceDto::fromEntity)
                    .toList();
        }
        return new PageImpl<>(dtoList, PageRequest.of(dto.getPage(), dto.getSize()), total);
    }

    private BooleanBuilder where() {
        return new BooleanBuilder();
    }

    private JPAQueryFactory query() {
        return new JPAQueryFactory(entityManager);
    }
}
