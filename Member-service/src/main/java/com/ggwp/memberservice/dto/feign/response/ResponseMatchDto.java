package com.ggwp.memberservice.dto.feign.response;

import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Data;

import java.util.List;

@Data
public class ResponseMatchDto {
    private String lolNickname;
    private String tag;
    private List<Long> sIdList;



}
