package com.ggwp.commentservice.dto.memberFeign.request;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestMatchDto {

    private List<Long> ids;


}
