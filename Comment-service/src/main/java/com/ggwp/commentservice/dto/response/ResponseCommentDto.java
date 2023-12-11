package com.ggwp.commentservice.dto.response;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.enums.Position;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseCommentDto {

    private Position myPos;

    private Boolean useMic;

    private String summonerName;

    private String tagLine;

    private String memo;

    private String summonerRank;

    public static ResponseCommentDto fromEntity(Comment comment) {
        return new ResponseCommentDto()
                .setMyPos(comment.getMyPos())
                .setUseMic(comment.isUseMic())
                .setSummonerName(comment.getSummonerName())
                .setTagLine(comment.getTagLine())
                .setMemo(comment.getMemo())
                .setSummonerRank(comment.getSummonerRank());
    }
}
