package com.ggwp.squadservice.dto.response;

import com.ggwp.squadservice.enums.Position;
import lombok.Data;

@Data
public class ResponseCommentDto {
    private Position cMyPos;

    private Boolean cMic;

    private String cMemo;
}
