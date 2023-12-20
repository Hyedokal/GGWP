package com.ggwp.squadservice.dto.memberfeign.request;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class RequestSidDto {

    private List<Long> sids;
}
