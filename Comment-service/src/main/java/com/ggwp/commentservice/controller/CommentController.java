package com.ggwp.commentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.memberFeign.request.RequestMatchDto;
import com.ggwp.commentservice.dto.memberFeign.response.ResponseMatchDto;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.request.RequestPageDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import com.ggwp.commentservice.repository.CommentRepository;
import com.ggwp.commentservice.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    //health check
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Comment-service is available");
    }

    //게시글별 댓글 페이징처리하여 가져오는 메서드
    @PostMapping("/search")
    public ResponseEntity<Page<ResponseCommentDto>> getPagedComment(@RequestBody RequestPageDto.Search dto) {
        Page<ResponseCommentDto> dtoList = commentService.searchPagedComment(dto);
        return ResponseEntity.ok(dtoList);
    }

    //댓글 작성하는 메서드
    @PostMapping
    public ResponseEntity<String> writeComment(@RequestBody RequestCommentDto dto) {
        commentService.writeComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Wrote Successfully");
    }

    //댓글 수정하는 메서드
    @PutMapping("/{cId}")
    public ResponseEntity<String> editComment(@PathVariable Long cId, @RequestBody RequestCommentDto dto) {
        commentService.editComment(cId, dto);
        return ResponseEntity.ok().body("Modified Successfully");
    }

    //댓글 삭제하는 메서드
    @DeleteMapping("/{cId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long cId) {
        commentService.deleteComment(cId);
        return ResponseEntity.ok().body("Deleted Successfully");
    }

    //Feign을 위한 메서드: 댓글 하나 가져오기
    @GetMapping("/feign/{cId}")
    public ResponseEntity<ResponseCommentDto> getOneComment(@PathVariable Long cId) {
        return ResponseEntity.ok(commentService.getOneComment(cId));
    }

    //승인 버튼 눌렀을 때 호출할 메서드
    @PutMapping("/feign/{cId}")
    public ResponseEntity<String> approveComment(@PathVariable Long cId) {
        Comment comment = commentService.approveComment(cId);
        return ResponseEntity.ok().body("Approved Successfully: " + comment.getSId());
    }


    @PostMapping("/feign/match")  // json으로 받아서 처리
    public ResponseEntity<List<ResponseMatchDto>> getSummonerDetails(@RequestBody RequestMatchDto requestDto) {
        List<Long> sIdList = requestDto.getIds();
        System.out.println("sIdList: " + sIdList);
        List<ResponseMatchDto> response = commentService.getSummonerDetails(requestDto);
        System.out.println("response: " + response);
        return ResponseEntity.ok().body(response);
    }
}
