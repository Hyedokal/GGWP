package com.ggwp.commentservice.controller;

import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.request.RequestPageDto;
import com.ggwp.commentservice.dto.response.ResponseCommentDto;
import com.ggwp.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


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

}
