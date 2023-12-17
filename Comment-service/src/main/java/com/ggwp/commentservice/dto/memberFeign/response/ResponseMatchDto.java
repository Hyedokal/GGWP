package com.ggwp.commentservice.dto.memberFeign.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseMatchDto {
    private String summonerName;
    private String tagLine;

    public ResponseMatchDto(String summonerName, String tagLine) {
        this.summonerName = summonerName;
        this.tagLine = tagLine;
    }
}



