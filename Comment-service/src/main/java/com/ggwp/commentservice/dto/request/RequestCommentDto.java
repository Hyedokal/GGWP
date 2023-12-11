package com.ggwp.commentservice.dto.request;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.enums.Position;
import com.ggwp.commentservice.enums.QType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RequestCommentDto {

    @NotNull(message = "해당 게시글이 존재하지 않습니다.")
    public Long sId;

    @NotNull(message = "반드시 하나의 포지션을 선택하셔야 합니다.")
    public Position myPos;

    @NotNull(message = "게시글에서 큐 타입을 받아오지 못했습니다.")
    public QType qType;

    @NotNull(message = "반드시 마이크 사용 여부를 체크하셔야 합니다.")
    public boolean useMic;

    @NotBlank(message = "닉네임을 반드시 입력해야 합니다.")
    public String summonerName;

    @NotBlank(message = "Riot 태그를 반드시 입력해야 합니다.")
    public String tagLine;

    @Length(max = 100, message = "댓글은 최대 100자까지 작성할 수 있습니다.")
    @NotBlank(message = "내용은 반드시 작성되어야 합니다.")
    public String memo;

    private String summonerRank;

    public Comment toEntity() {
        return Comment.create(this.sId, this.myPos, this.qType, this.useMic,
                this.summonerName, this.tagLine, this.memo, this.summonerRank);
    }
}
