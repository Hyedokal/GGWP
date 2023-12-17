package com.ggwp.squadservice.dto.memberfeign.request;

import lombok.Builder;
import lombok.Data;

@Data
public class RequestMatchDto {

    private String summonerName;
    private String tagLine;
    private Long sId;

}
