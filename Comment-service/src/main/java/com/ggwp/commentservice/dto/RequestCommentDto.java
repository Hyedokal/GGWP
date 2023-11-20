package com.ggwp.commentservice.dto;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.enums.Position;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RequestCommentDto {

    @NotBlank(message = "반드시 하나의 포지션을 선택하셔야 합니다.")
    public Position cMyPos;

    @NotBlank
    public Boolean cMic;

    @Length(max = 100, message = "댓글은 최대 100자까지 작성할 수 있습니다.")
    @NotBlank
    public String cMemo;

    public Comment toEntity(){
        return Comment.builder()
                .cMyPos(this.cMyPos)
                .cMemo(this.cMemo)
                .cMic(this.cMic)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
