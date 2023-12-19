package com.ggwp.memberservice.dto.feign.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeignLolNickNameTagResponseDto {
    private boolean success;
    private String message;
}
