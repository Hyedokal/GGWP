package com.ggwp.squadservice.dto.memberfeign.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseMatchDto {

    private String lolNickname;
    private String tag;
    private List<Long> sIdList;

}