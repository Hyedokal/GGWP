package com.ggwp.commentservice.dto.response;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.enums.Position;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseCommentDto {

    private Position cMyPos;

    private Boolean cMic;

    private String cMemo;

    public static ResponseCommentDto fromEntity(Comment comment) {
        return new ResponseCommentDto()
                .setCMyPos(comment.getCMyPos())
                .setCMic(comment.getCMic())
                .setCMemo(comment.getCMemo());
    }
}
