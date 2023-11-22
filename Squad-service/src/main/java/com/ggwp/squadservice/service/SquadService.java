package com.ggwp.squadservice.service;

import com.ggwp.squadservice.domain.Comment;
import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.dto.RequestSquadDto;
import com.ggwp.squadservice.dto.ResponseFindSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import com.ggwp.squadservice.feign.CommentFeignClient;
import com.ggwp.squadservice.repository.SquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SquadService {
    
    @Autowired
    private final SquadRepository squadRepository;
    private final CommentFeignClient commentFeignClient;
    
    //게시글 작성 후 저장하기
    public void writeSquad(RequestSquadDto dto){
        Squad squad = dto.toEntity();
        squadRepository.save(squad);
    }

    @Transactional
    //게시글 수정하기
    public void editSquad(Long sId, RequestSquadDto dto){
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new NoSuchElementException("해당 번호에 대한 게시글이 없습니다."));
        squad.updateSquad(dto);
        squadRepository.save(squad);
    }

    @Transactional
    //게시글 삭제하기
    public void deleteSquad(Long sId){
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new NoSuchElementException("해당 번호에 대한 게시글이 없습니다."));
        squadRepository.delete(squad);
    }
    
    //게시글 전체 조회하기 (수정 날짜 기준 최신 순)
    public List<Squad> findAllSquad(){
        return squadRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
    }
    
    //게시글 상세 조회하기
    public Squad findOneSquad(Long sId){
        return squadRepository.findById(sId)
                .orElseThrow(() -> new NoSuchElementException("해당 번호에 대한 게시글이 없습니다."));
    }

    //게시글 필터 별 조회하기
    public List<Squad> findSquadWithFilters(Position myPos, QType qType){
        Specification<Squad> spec = Specification.where(null);

        if (myPos != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("myPos"), myPos));
        }

        if (qType != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("qType"), qType));
        }

        return squadRepository.findAll(spec);
    }

    //게시글 별 댓글 리스트를 받아오는 메서드
    public ResponseFindSquadDto findSquadCommentList(Long sId){
        Squad squad = squadRepository.findById(sId)
                .orElseThrow(() -> new NoSuchElementException("해당 번호의 게시글이 없습니다."));
        ResponseFindSquadDto dto = new ResponseFindSquadDto(squad);
        List<Comment> commentList = commentFeignClient.getCommentList(sId);
        dto.setCommentList(commentList);
        return dto;
    }
}
