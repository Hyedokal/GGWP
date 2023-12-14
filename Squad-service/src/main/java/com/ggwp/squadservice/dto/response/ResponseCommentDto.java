package com.ggwp.squadservice.dto.response;

import com.ggwp.squadservice.enums.Position;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ResponseCommentDto {
    private Position myPos;

    private Boolean useMic;

    private String summonerName;

    private String tagLine;

    private String memo;

    private String summonerRank;

}
