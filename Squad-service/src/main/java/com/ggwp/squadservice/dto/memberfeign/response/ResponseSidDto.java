package com.ggwp.squadservice.dto.memberfeign.response;


import lombok.Data;

import java.util.List;

@Data
public class ResponseSidDto {

    private boolean success;
    private String message;

    private Long sids;
    private String squadNickname;
    private String squadTag;


}
