package com.ggwp.squadservice.controller;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.dto.RequestSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import com.ggwp.squadservice.service.SquadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/squad")
@RequiredArgsConstructor
public class SquadController {

    @Autowired
    private final SquadService squadService;

    //게시글 전체 가져오는 메서드
    @GetMapping("/list")
    public ResponseEntity<List<Squad>> getSquadList(){
        List<Squad> squadList = squadService.findAllSquad();
        return ResponseEntity.ok().body(squadList);
    }

    //게시글 하나 가져오는 메서드
    @GetMapping("/list/{sId}")
    public ResponseEntity<Squad> getOneSquad(@PathVariable Long sId){
        Squad squad = squadService.findOneSquad(sId);
        return ResponseEntity.ok().body(squad);
    }

    //게시글 작성하는 메서드
    @PostMapping("/write")
    public ResponseEntity<?> writeSquad(Position myPos,Position wantPos,
                                        QType qType, Boolean sMic, String sMemo){
        RequestSquadDto dto = new RequestSquadDto(myPos, wantPos, qType, sMic, sMemo);
        squadService.writeSquad(dto);
        return ResponseEntity.created(URI.create("/list")).build();
    }

    //게시글 수정하는 메서드
    @PutMapping("/edit/{sId}")
    public ResponseEntity<?> editSquad(@PathVariable Long sId, Position myPos, Position wantPos,
                                       QType qType, Boolean sMic, String sMemo){
        RequestSquadDto dto = new RequestSquadDto(myPos, wantPos, qType, sMic, sMemo);
        squadService.editSquad(sId, dto);
        return ResponseEntity.ok().build();
    }

    //게시글 삭제하는 메서드
    @DeleteMapping("/delete/{sId}")
    public ResponseEntity<?> deleteAnnounce(@PathVariable Long sId){
        squadService.deleteSquad(sId);
        return ResponseEntity.ok().build();
    }

    //게시글 필터조회
    @GetMapping("/filter")
    public ResponseEntity<List<Squad>> filterSquads(
            @RequestParam(required = false) Position myPos,
            @RequestParam(required = false) QType qType
    ) {
        List<Squad> filteredSquads = squadService.findSquadWithFilters(myPos, qType);
        return ResponseEntity.ok(filteredSquads);
    }
}
