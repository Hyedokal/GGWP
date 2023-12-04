package com.ggwp.searchservice.account.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAccountDto {

    private String puuid;

    private String gameName;

    private String tagLine;

}
