package com.ggwp.announceservice.controller;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.request.RequestDto;
import com.ggwp.announceservice.dto.response.ResponseAnnounceDto;
import com.ggwp.announceservice.service.AnnounceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/announces")
@RequiredArgsConstructor
public class AnnounceController {


    private final AnnounceService announceServiceImpl;

    //공지사항 리스트 가져오는 메서드
    //mhlee: 혹시 페이징 수업시간에 안배웠나요?
    //페이징은 너무 당연하게 사용되어, 시나리오상 공지사항은 3~4개다 하더라도 연습삼아 페이징 처리하는게 좋을것 같습니다.
    @GetMapping
    public ResponseEntity<List<ResponseAnnounceDto>> getAnnounceList() {
        List<ResponseAnnounceDto> announceDtoList = announceServiceImpl.getAllAnnounce();
        return ResponseEntity.ok().body(announceDtoList);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ResponseAnnounceDto>> getPagedAnnounce(@RequestBody RequestDto.Search dto) {
        List<ResponseAnnounceDto> announceDtoList = announceServiceImpl.getAllAnnounce();
        return null;
    }

    //공지사항 하나 가져오는 메서드
    @GetMapping("/{aId}")
    public ResponseEntity<ResponseAnnounceDto> getOneAnnounce(@PathVariable Long aId) {
        ResponseAnnounceDto dto = announceServiceImpl.getOneAnnounce(aId);
        return ResponseEntity.ok().body(dto);
    }

    //공지사항 작성하는 메서드
    @PostMapping
    public ResponseEntity<String> writeAnnounce(@RequestBody RequestAnnounceDto dto) {
        announceServiceImpl.writeAnnounce(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved Successfully");
    }

    //공지사항 수정하는 메서드
    @PutMapping("/{aId}")
    public ResponseEntity<String> editAnnounce(@PathVariable Long aId, @RequestBody RequestAnnounceDto dto) {
        announceServiceImpl.editAnnounce(aId, dto);
        return ResponseEntity.ok().body("Modified Successfully");
    }

    //공지사항 삭제하는 메서드
    @DeleteMapping("/{aId}")
    public ResponseEntity<String> deleteAnnounce(@PathVariable Long aId) {
        announceServiceImpl.deleteAnnounce(aId);
        return ResponseEntity.ok().body("Deleted Successfully");
    }
}
