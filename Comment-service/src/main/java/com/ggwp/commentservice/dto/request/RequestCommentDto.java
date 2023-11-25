package com.ggwp.commentservice.dto.request;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.enums.Position;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
public class RequestCommentDto {

    @NotBlank(message = "해당 ID의 게시글이 존재하지 않습니다.")
    public Long sId;

    @NotBlank(message = "반드시 하나의 포지션을 선택하셔야 합니다.")
    public Position cMyPos;

    @NotBlank(message = "반드시 마이크 사용 여부를 체크하셔야 합니다.")
    public Boolean cMic;

    @Length(max = 100, message = "댓글은 최대 100자까지 작성할 수 있습니다.")
    @NotBlank(message = "내용은 반드시 작성되어야 합니다.")
    public String cMemo;

    public Comment toEntity() {
        return Comment.CREATE(this.sId, this.cMyPos, this.cMic, this.cMemo);
    }
}
