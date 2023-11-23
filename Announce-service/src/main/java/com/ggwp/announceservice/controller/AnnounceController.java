package com.ggwp.announceservice.controller;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.response.ResponseAnnounceDto;
import com.ggwp.announceservice.service.impl.AnnounceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/announces")
@RequiredArgsConstructor
public class AnnounceController {

    @Autowired
    private final AnnounceServiceImpl announceService;

    //공지사항 리스트 가져오는 메서드
    @GetMapping
    public ResponseEntity<List<ResponseAnnounceDto>> getAnnounceList() {
        List<ResponseAnnounceDto> announceDtoList = announceService.getAllAnnounce();
        return ResponseEntity.ok().body(announceDtoList);
    }

    //공지사항 하나 가져오는 메서드
    @GetMapping("/{aId}")
    public ResponseEntity<ResponseAnnounceDto> getOneAnnounce(@PathVariable Long aId) {
        ResponseAnnounceDto dto = announceService.getOneAnnounce(aId);
        return ResponseEntity.ok().body(dto);
    }

    //공지사항 작성하는 메서드
    @PostMapping
    public ResponseEntity<String> writeAnnounce(@RequestBody RequestAnnounceDto dto) {
        announceService.writeAnnounce(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved Successfully");
    }

    //공지사항 수정하는 메서드
    @PutMapping("/{aId}")
    public ResponseEntity<String> editAnnounce(@PathVariable Long aId, @RequestBody RequestAnnounceDto dto) {
        announceService.editAnnounce(aId, dto);
        return ResponseEntity.ok().body("Modified Successfully");
    }

    //공지사항 삭제하는 메서드
    @DeleteMapping("/{aId}")
    public ResponseEntity<String> deleteAnnounce(@PathVariable Long aId) {
        announceService.deleteAnnounce(aId);
        return ResponseEntity.ok().body("Deleted Successfully");
    }
}
