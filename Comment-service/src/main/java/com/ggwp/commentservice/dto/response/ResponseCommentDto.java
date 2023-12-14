package com.ggwp.commentservice.dto.response;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.enums.Position;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseCommentDto {
    private Long cId;

    private Long cId;

    private Position myPos;

    private boolean useMic;

    private String summonerName;

    private String tagLine;

    private String memo;

    private String summonerRank;

    public static ResponseCommentDto fromEntity(Comment comment) {
        return new ResponseCommentDto()
                .setCId(comment.getCId())
                .setSId(comment.getSId())
                .setMyPos(comment.getMyPos())
                .setUseMic(comment.isUseMic())
                .setSummonerName(comment.getSummonerName())
                .setTagLine(comment.getTagLine())
                .setMemo(comment.getMemo())
                .setSummonerRank(comment.getSummonerRank());
    }
}
