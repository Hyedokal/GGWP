package com.ggwp.squadservice.controller;

import com.ggwp.squadservice.dto.request.RequestCommentPageDto;
import com.ggwp.squadservice.dto.request.RequestSquadDto;
import com.ggwp.squadservice.dto.request.RequestSquadPageDto;
import com.ggwp.squadservice.dto.response.ResponseCommentDto;
import com.ggwp.squadservice.dto.response.ResponseSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import com.ggwp.squadservice.feign.CommentFeignClient;
import com.ggwp.squadservice.service.SquadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/squads")
@RequiredArgsConstructor
public class SquadController {

    private final SquadService squadService;
    private final CommentFeignClient commentFeignClient;

    //헬스 체크
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Squad-service is available");
    }

    //게시글 하나 가져오는 메서드
    @GetMapping("/{sId}")
    public ResponseEntity<ResponseSquadDto> getOneSquad(@PathVariable Long sId,
                                                        @RequestParam int page) {
        ResponseSquadDto dto = squadService.getOneSquad(sId);
        //sId = 현재 게시글 번호, page = 0, size = 10 (디폴트 값)이 들어감.
        RequestCommentPageDto.Search search = new RequestCommentPageDto.Search().setSId(sId)
                .setPage(page);
        Page<ResponseCommentDto> comments = commentFeignClient.getPagedComment(search);
        dto.setCommentPage(comments);

        return ResponseEntity.ok().body(dto);
    }

    //페이징처리할 게시글 전체 조회
    @PostMapping("/search")
    public ResponseEntity<Page<ResponseSquadDto>> getPagedSquads(@RequestBody RequestSquadPageDto.Search search) {
        Page<ResponseSquadDto> dtoList = squadService.searchPagedSquad(search);
        return ResponseEntity.ok(dtoList);
    }

    //게시글 작성하는 메서드
    @PostMapping
    public ResponseEntity<String> writeSquad(@RequestBody RequestSquadDto dto) {
        squadService.writeSquad(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved Successfully");
    }

    //게시글 수정하는 메서드
    @PutMapping("/{sId}")
    public ResponseEntity<String> editSquad(@PathVariable Long sId, @RequestBody RequestSquadDto dto) {
        squadService.editSquad(sId, dto);
        return ResponseEntity.ok().body("Modified Successfully");
    }

    //게시글 삭제하는 메서드
    @DeleteMapping("/{sId}")
    public ResponseEntity<String> deleteAnnounce(@PathVariable Long sId) {
        squadService.deleteSquad(sId);
        return ResponseEntity.ok().body("Deleted Successfully");
    }

    //게시글 필터조회
    @GetMapping("/filter")
    public ResponseEntity<List<ResponseSquadDto>> filterSquads(
            @RequestParam(required = false) Position myPos,
            @RequestParam(required = false) QType qType,
            @RequestParam(required = false) String rank
    ) {
        List<ResponseSquadDto> filteredSquads = squadService.getSquadWithFilters(myPos, qType, rank);
        return ResponseEntity.ok(filteredSquads);
    }


}
