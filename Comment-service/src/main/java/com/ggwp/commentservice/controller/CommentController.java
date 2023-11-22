package com.ggwp.commentservice.controller;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.RequestCommentDto;
import com.ggwp.commentservice.enums.Position;
import com.ggwp.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private final CommentService commentService;

    //게시글별 댓글 가져오는 메서드
    @GetMapping("/{sId}")
    public ResponseEntity<?> getCommentList(@PathVariable Long sId){
        List<Comment> commentList = commentService.listCommentBySquad(sId)
                .stream().toList();
        return  ResponseEntity.ok(commentList);
    }

    //댓글 작성하는 메서드
    @PostMapping("/write")
    public ResponseEntity<?> writeComment(Position cMyPos, Boolean cMic,
                                          String cMemo){
        RequestCommentDto dto = new RequestCommentDto(cMyPos, cMic, cMemo);
        commentService.writeComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Wrote Successfully");
    }

    //댓글 수정하는 메서드
    @PutMapping("/edit/{cId}")
    public ResponseEntity<?> editComment(@PathVariable Long cId, Position cMypos,
                                         Boolean cMic, String cMemo){
        RequestCommentDto dto = new RequestCommentDto(cMypos, cMic, cMemo);
        commentService.editComment(cId, dto);
        return ResponseEntity.ok().build();
    }

    //댓글 삭제하는 메서드
    @DeleteMapping("/delete/{cId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long cId){
        commentService.deleteComment(cId);
        return ResponseEntity.ok().build();
    }

}
