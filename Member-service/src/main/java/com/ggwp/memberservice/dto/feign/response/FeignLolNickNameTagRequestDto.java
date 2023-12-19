package com.ggwp.memberservice.dto.feign.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeignLolNickNameTagRequestDto {

    @NotBlank
    String existLolNickName;
    @NotBlank
    String existTag;
    @NotBlank
    String lolNickName;
    @NotBlank
    String tag;

    public FeignLolNickNameTagRequestDto(String existLolNickName, String existTag, String lolNickName, String tag) {
        this.existLolNickName = existLolNickName;
        this.existTag = existTag;
        this.lolNickName = lolNickName;
        this.tag = tag;
    }
}
