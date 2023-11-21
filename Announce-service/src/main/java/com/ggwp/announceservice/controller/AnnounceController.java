package com.ggwp.announceservice.controller;

import com.ggwp.announceservice.dto.RequestAnnounceDto;
import com.ggwp.announceservice.entity.Announce;
import com.ggwp.announceservice.service.AnnounceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/announce")
@RequiredArgsConstructor
public class AnnounceController {

    @Autowired
    private final AnnounceService announceService;

    //공지사항 리스트 가져오는 메서드
    @GetMapping("/list")
    public ResponseEntity<List<Announce>> getAnnounceList(){
        List<Announce> announceList = announceService.getAllAnnounce();
        return ResponseEntity.ok().body(announceList);
    }

    //공지사항 하나 가져오는 메서드
    @GetMapping("/list/{aId}")
    public ResponseEntity<Announce> getOneAnnounce(@PathVariable Long aId){
        Announce announce = announceService.getOneAnnounce(aId);
        return ResponseEntity.ok().body(announce);
    }

    //공지사항 작성하는 메서드
    @PostMapping("/write")
    public ResponseEntity<?> writeAnnounce(String aTitle, String aContent){
        RequestAnnounceDto dto = new RequestAnnounceDto(aTitle, aContent);
        announceService.writeAnnounce(dto);
        return ResponseEntity.created(URI.create("/list")).build();
    }

    //공지사항 수정하는 메서드
    @PutMapping("/edit/{aId}")
    public ResponseEntity<?> editAnnounce(@PathVariable Long aId, String aTitle, String aContent){
        RequestAnnounceDto dto = new RequestAnnounceDto(aTitle, aContent);
        announceService.editAnnounce(aId, dto);
        return ResponseEntity.ok().build();
    }

    //공지사항 삭제하는 메서드
    @DeleteMapping("/delete/{aId}")
    public ResponseEntity<?> deleteAnnounce(@PathVariable Long aId){
        announceService.deleteAnnounce(aId);
        return ResponseEntity.ok().build();
    }
}
