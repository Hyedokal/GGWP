package com.ggwp.squadservice.controller;

import com.ggwp.squadservice.dto.request.RequestSquadDto;
import com.ggwp.squadservice.dto.response.ResponseSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import com.ggwp.squadservice.service.SquadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/squads")
@RequiredArgsConstructor
public class SquadController {

    @Autowired
    private final SquadService squadServiceImpl;

    //게시글 전체 조회하는 메서드
    @GetMapping
    public ResponseEntity<List<ResponseSquadDto>> getSquadList() {
        List<ResponseSquadDto> squadList = squadServiceImpl.getAllSquad();
        return ResponseEntity.ok().body(squadList);
    }

    //게시글 하나 가져오는 메서드
    @GetMapping("/{sId}")
    public ResponseEntity<ResponseSquadDto> getOneSquad(@PathVariable Long sId) {
        ResponseSquadDto dto = squadServiceImpl.getOneSquad(sId);
        return ResponseEntity.ok().body(dto);
    }

    //게시글 작성하는 메서드
    @PostMapping
    public ResponseEntity<String> writeSquad(@RequestBody RequestSquadDto dto) {
        squadServiceImpl.writeSquad(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved Successfully");
    }

    //게시글 수정하는 메서드
    @PutMapping("/{sId}")
    public ResponseEntity<String> editSquad(@PathVariable Long sId, @RequestBody RequestSquadDto dto) {
        squadServiceImpl.editSquad(sId, dto);
        return ResponseEntity.ok().body("Modified Successfully");
    }

    //게시글 삭제하는 메서드
    @DeleteMapping("/{sId}")
    public ResponseEntity<String> deleteAnnounce(@PathVariable Long sId) {
        squadServiceImpl.deleteSquad(sId);
        return ResponseEntity.ok().body("Deleted Successfully");
    }

    //게시글 필터조회
    @GetMapping("/filter")
    public ResponseEntity<List<ResponseSquadDto>> filterSquads(
            @RequestParam(required = false) Position myPos,
            @RequestParam(required = false) QType qType
    ) {
        List<ResponseSquadDto> filteredSquads = squadServiceImpl.getSquadWithFilters(myPos, qType);
        return ResponseEntity.ok(filteredSquads);
    }


}
