package com.ggwp.squadservice.dto.response;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class ResponseSquadDto {
    private Long sId;
    private Position myPos;
    private Position wantPos;
    private QType qType;
    private Boolean useMic;
    private String summonerName;
    private String tag_line;
    private String rank;
    private String memo;
    private Page<ResponseCommentDto> commentList;
    private Timestamp updatedAt;

    public static ResponseSquadDto fromEntity(Squad squad) {
        return new ResponseSquadDto()
                .setSId(squad.getSId())
                .setMyPos(squad.getMyPos())
                .setWantPos(squad.getWantPos())
                .setQType(squad.getQType())
                .setUseMic(squad.isUseMic())
                .setSummonerName(squad.getSummonerName())
                .setTag_line(squad.getTag_line())
                .setRank(squad.getSummonerRank())
                .setMemo(squad.getMemo())
                .setUpdatedAt(squad.getUpdatedAt());
    }
}
