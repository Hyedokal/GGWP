package com.ggwp.commentservice.dto.response;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.enums.Position;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseCommentDto {
    private Long sId;

    private Long sId;

    private Position myPos;

    private boolean useMic;

    private String summonerName;

    private String tag_line;

    private String memo;

    private String summonerRank;

    public static ResponseCommentDto fromEntity(Comment comment) {
        return new ResponseCommentDto()
                .setSId(comment.getSId())
                .setMyPos(comment.getMyPos())
                .setUseMic(comment.isUseMic())
                .setSummonerName(comment.getSummonerName())
                .setTag_line(comment.getTag_line())
                .setMemo(comment.getMemo())
                .setSummonerRank(comment.getSummonerRank());
    }
}
