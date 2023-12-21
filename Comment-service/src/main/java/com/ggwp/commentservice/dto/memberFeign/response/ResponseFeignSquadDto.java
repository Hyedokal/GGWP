package com.ggwp.commentservice.dto.memberFeign.response;


import lombok.Data;

import java.util.List;

@Data
public class ResponseFeignSquadDto {
    private Long sids;
    private String squadNickname;
    private String squadTag;
    private String myName;
    private String myTag;
}
