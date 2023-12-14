package com.ggwp.squadservice.dto.response;

import com.ggwp.squadservice.enums.Position;
import lombok.Data;

@Data
public class ResponseCommentDto {
    private Long cId;

    private Long sId;

    private Position myPos;

    private Boolean useMic;

    private String summonerName;

    private String tagLine;

    private String memo;

    private String summonerRank;
}
