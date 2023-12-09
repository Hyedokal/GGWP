package com.ggwp.noticeservice.dto;

import com.ggwp.noticeservice.common.enums.Position;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseCommentDto {

    private String summonerName;

    private String tagLine;

}