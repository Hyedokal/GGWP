package com.ggwp.announceservice.service.impl;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.request.RequestDto;
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

    //mhlee: 이부분은 생성자 주입을 사용하셨는데, 생성자 주입을 사용하시면 @Autowired를 사용하지 않아도 됩니다.
    //@Autowired
    private final AnnounceRepository announceRepository;

    //@Autowired
    private final EntityManager entityManager;

    //공지사항 작성 후 저장하기
    public Announce writeAnnounce(RequestAnnounceDto dto) {
        Announce announce = dto.toEntity();
        return announceRepository.save(announce);
    }

    //공지사항 수정하기
    public void editAnnounce(Long aId, RequestAnnounceDto dto) {
        Announce announce = announceRepository.findById(aId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.ANNOUNCE_ID_NOT_FOUND));
        announce.updateAnnounce(dto);
        announceRepository.save(announce);
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

    @Override
    public Page<ResponseAnnounceDto> searchPagedAnnounce(RequestDto.Search dto) {
        var qAnnounce = QAnnounce.announce;
        var where = where();
        if (dto.getTitle() != null) {
            where.and(qAnnounce.aTitle.contains(dto.getTitle()));
        }

        var total = query().select(qAnnounce.count())
                .from(qAnnounce)
                .where(where)
                .fetchOne();

        total = (total == null) ? 0 : total;

        List<ResponseAnnounceDto> list = new ArrayList<>();

        if (total > 0) {
            list = query().select(qAnnounce)
                    .from(qAnnounce)
                    .where(where)
                    .orderBy(qAnnounce.aId.desc())
                    .offset((long) dto.getPage() * dto.getSize())
                    .limit(dto.getSize())
                    .fetch()
                    .stream()
                    .map(ResponseAnnounceDto::fromEntity)
                    .toList();
        }


        return new PageImpl<>(list, PageRequest.of(dto.getPage(), dto.getSize()), total);
    }



    //공지사항 상세 조회하기
    @Transactional(readOnly = true)
    public ResponseAnnounceDto getOneAnnounce(Long aId) {
        Announce announce = announceRepository.findById(aId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMsg.ANNOUNCE_ID_NOT_FOUND));
        return ResponseAnnounceDto.fromEntity(announce);
    }

    private BooleanBuilder where() {
        return new BooleanBuilder();
    }


    private JPAQueryFactory query() {
        return new JPAQueryFactory(entityManager);
    }


}
