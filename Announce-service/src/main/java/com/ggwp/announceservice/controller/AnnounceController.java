package com.ggwp.announceservice.controller;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.request.RequestPageDto;
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

    @Autowired
    private final AnnounceService announceService;

    //health check
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Announce-service is available");
    }

    //공지사항 리스트를 페이징처리하여 가져오는 메서드.
    @PostMapping("/search")
    public ResponseEntity<Page<ResponseAnnounceDto>> getPagedAnnounce(@RequestBody RequestPageDto.Search dto) {
        Page<ResponseAnnounceDto> dtoList = announceService.searchPagedAnnounce(dto);
        return ResponseEntity.ok().body(dtoList);
    }

    //공지사항 하나 가져오는 메서드
    @GetMapping("/{id}")
    public ResponseEntity<ResponseAnnounceDto> getOneAnnounce(@PathVariable Long id) {
        ResponseAnnounceDto dto = announceService.getOneAnnounce(id);
        return ResponseEntity.ok().body(dto);
    }

    //공지사항 작성하는 메서드
    @PostMapping
    public ResponseEntity<String> writeAnnounce(@RequestBody RequestAnnounceDto dto) {
        //로그
        System.out.println(dto);
        System.out.println(dto.getContent());
        announceService.writeAnnounce(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved Successfully");
    }

    //공지사항 수정하는 메서드
    @PutMapping("/{id}")
    public ResponseEntity<String> editAnnounce(@PathVariable Long id, @RequestBody RequestAnnounceDto dto) {
        announceService.editAnnounce(id, dto);
        return ResponseEntity.ok().body("Modified Successfully");
    }

    //공지사항 삭제하는 메서드
    @DeleteMapping("/{aId}")
    public ResponseEntity<String> deleteAnnounce(@PathVariable Long aId) {
        announceService.deleteAnnounce(aId);
        return ResponseEntity.ok().body("Deleted Successfully");
    }
}
