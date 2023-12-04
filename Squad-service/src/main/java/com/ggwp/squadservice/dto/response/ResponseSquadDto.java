package com.ggwp.squadservice.dto.response;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class ResponseSquadDto {
    private Long sId;
    private Position myPos;
    private Position wantPos;
    private QType qType;
    private Boolean sMic;
    private String summonerName;
    private String rank;
    private String sMemo;
    private List<ResponseCommentDto> commentList;
    private LocalDateTime updatedAt;

    public static ResponseSquadDto fromEntity(Squad squad) {
        return new ResponseSquadDto()
                .setSId(squad.getSId())
                .setMyPos(squad.getMyPos())
                .setWantPos(squad.getWantPos())
                .setQType(squad.getQType())
                .setSMic(squad.getSMic())
                .setSummonerName(squad.getSummonerName())
                .setRank(squad.getSummonerRank())
                .setSMemo(squad.getSMemo())
                .setUpdatedAt(squad.getUpdatedAt());
    }
}
